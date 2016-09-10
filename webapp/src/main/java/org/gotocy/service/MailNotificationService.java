package org.gotocy.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.config.Locales;
import org.gotocy.domain.Notification;
import org.gotocy.domain.i18n.LocalizedPage;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.StringReader;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

/**
 * @author ifedorenkov
 */
public class MailNotificationService implements NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(MailNotificationService.class);

	private final ApplicationProperties applicationProperties;
	private final Configuration freemarkerConfig;
	private final JavaMailSender sender;
	private final ExecutorService executorService;
	private final PageService pageService;

	public MailNotificationService(ApplicationProperties applicationProperties, Configuration freemarkerConfig,
		JavaMailSender sender, PageService pageService, ExecutorService executorService)
	{
		this.applicationProperties = applicationProperties;
		this.freemarkerConfig = freemarkerConfig;
		this.sender = sender;
		this.pageService = pageService;
		this.executorService = executorService;
	}

	@Override
	public void sendNotification(Notification notification) {
		executorService.execute(() -> {
			try {
				LocalizedPage emailPageTemplate = getLocalizedPage(notification);
				MimeMessage mimeMessage = sender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
				mimeMessageHelper.setFrom(applicationProperties.getEmail());
				mimeMessageHelper.setTo(notification.getTo());
				mimeMessageHelper.setSubject(emailPageTemplate.getTitle());
				mimeMessageHelper.setText(compileTemplate(emailPageTemplate, notification.getModel()));
				sender.send(mimeMessage);
			} catch (Exception e) {
				logger.error("Failed to send notification {}", notification, e);
			}
		});
	}

	private LocalizedPage getLocalizedPage(Notification notification) {
		Optional<LocalizedPage> localizedPage = pageService.findByUrl(notification.getTemplatePageUrl(),
			notification.getLocale());

		if (!localizedPage.isPresent()) {
			logger.info("Failed to load email template page '{}' in the required locale '{}'. " +
				"Will try to load in the default locale", notification.getTemplatePageUrl(), notification.getLocale());
			localizedPage = pageService.findByUrl(notification.getTemplatePageUrl(), Locales.DEFAULT);
		}

		if (!localizedPage.isPresent()) {
			logger.error("Failed to load email template page '{}'", notification.getTemplatePageUrl());
			throw new ServiceMethodExecutionException("Email template not found.");
		}

		return localizedPage.get();
	}

	/**
	 * TODO: cache templates
	 */
	private String compileTemplate(LocalizedPage localizedPage, Map<String, Object> model) throws Exception {
		return FreeMarkerTemplateUtils.processTemplateIntoString(
			new Template(localizedPage.getTitle(), new StringReader(sanitizeHtml(localizedPage.getHtml())),
				freemarkerConfig), model);
	}

	private static final Pattern DOG_PATTERN = Pattern.compile("&#64;", Pattern.LITERAL);
	private static final Pattern DOUBLE_QUOTE_PATTERN = Pattern.compile("&#34;", Pattern.LITERAL);
	private static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("&#39;", Pattern.LITERAL);

	private static String sanitizeHtml(String html) {
		PolicyFactory sanitizer = new HtmlPolicyBuilder()
			.withPreprocessor(AppendLinkUrl::new)
			.toFactory();
		String sanitized = sanitizer.sanitize(html);
		// Unescape
		sanitized = DOG_PATTERN.matcher(sanitized).replaceAll("@");
		sanitized = DOUBLE_QUOTE_PATTERN.matcher(sanitized).replaceAll("\"");
		sanitized = SINGLE_QUOTE_PATTERN.matcher(sanitized).replaceAll("\'");
		return sanitized;
	}

}
