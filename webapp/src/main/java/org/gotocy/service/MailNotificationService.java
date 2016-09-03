package org.gotocy.service;

import freemarker.template.Configuration;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author ifedorenkov
 */
public class MailNotificationService implements NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(MailNotificationService.class);

	private final ApplicationProperties applicationProperties;
	private final Configuration freemarkerConfig;
	private final JavaMailSender sender;
	private final ExecutorService executorService;

	public MailNotificationService(ApplicationProperties applicationProperties, Configuration freemarkerConfig,
		JavaMailSender sender, ExecutorService executorService)
	{
		this.applicationProperties = applicationProperties;
		this.freemarkerConfig = freemarkerConfig;
		this.sender = sender;
		this.executorService = executorService;
	}

	@Override
	public void sendNotification(Notification notification) throws ServiceMethodExecutionException {
		executorService.execute(() -> {
			try {
				MimeMessage mimeMessage = sender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
				mimeMessageHelper.setFrom(applicationProperties.getEmail());
				mimeMessageHelper.setTo(notification.getTo());
				mimeMessageHelper.setSubject(notification.getSubject());
				mimeMessageHelper.setText(getCompiledTemplate(notification.getViewName(), notification.getModel()));
				sender.send(mimeMessage);
			} catch (Exception e) {
				logger.error("Failed to send notification {}", notification, e);
			}
		});
	}

	private String getCompiledTemplate(String templateName, Map<String, Object> model) throws Exception {
		return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate(templateName), model);
	}

}
