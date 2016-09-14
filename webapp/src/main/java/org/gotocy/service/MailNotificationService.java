package org.gotocy.service;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Notification;
import org.gotocy.domain.i18n.LocalizedPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;

/**
 * @author ifedorenkov
 */
public class MailNotificationService implements NotificationService {
	private static final Logger logger = LoggerFactory.getLogger(MailNotificationService.class);

	private final ApplicationProperties applicationProperties;
	private final JavaMailSender sender;
	private final ExecutorService executorService;
	private final TemplatesService templatesService;

	public MailNotificationService(ApplicationProperties applicationProperties, JavaMailSender sender,
		TemplatesService templatesService, ExecutorService executorService)
	{
		this.applicationProperties = applicationProperties;
		this.sender = sender;
		this.executorService = executorService;
		this.templatesService = templatesService;
	}

	@Override
	public void sendNotification(Notification notification) {
		executorService.execute(() -> {
			try {
				LocalizedPage templatePage = templatesService.getProcessedTemplate(
					notification.getTemplatePageUrl(), notification.getModel(), notification.getLocale());
				MimeMessage mimeMessage = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setFrom(applicationProperties.getEmail());
				helper.setTo(notification.getTo());
				helper.setSubject(templatePage.getTitle());
				helper.setText(templatePage.getHtml());
				sender.send(mimeMessage);
			} catch (Exception e) {
				logger.error("Failed to send notification {}", notification, e);
			}
		});
	}

}
