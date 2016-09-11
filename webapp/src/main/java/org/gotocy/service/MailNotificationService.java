package org.gotocy.service;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.config.Locales;
import org.gotocy.domain.Notification;
import org.gotocy.domain.i18n.LocalizedPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * @author ifedorenkov
 */
public class MailNotificationService implements NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(MailNotificationService.class);

	private final ApplicationProperties applicationProperties;
	private final JavaMailSender sender;
	private final ExecutorService executorService;
	private final PageService pageService;
	private final TemplatesService templatesService;

	public MailNotificationService(ApplicationProperties applicationProperties, JavaMailSender sender,
		PageService pageService, TemplatesService templatesService, ExecutorService executorService)
	{
		this.applicationProperties = applicationProperties;
		this.sender = sender;
		this.executorService = executorService;
		this.pageService = pageService;
		this.templatesService = templatesService;
	}

	@Override
	public void sendNotification(Notification notification) {
		executorService.execute(() -> {
			try {
				LocalizedPage templatePage = getLocalizedPage(notification);
				MimeMessage mimeMessage = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setFrom(applicationProperties.getEmail());
				helper.setTo(notification.getTo());
				helper.setSubject(templatePage.getTitle());
				helper.setText(templatesService.processTemplateIntoString(templatePage, notification.getModel()));
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

}
