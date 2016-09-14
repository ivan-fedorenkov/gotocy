package org.gotocy.service;

import org.gotocy.domain.Notification;
import org.gotocy.domain.i18n.LocalizedPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ifedorenkov
 */
public class LoggingNotificationService implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(LoggingNotificationService.class);

	private final TemplatesService templatesService;

	public LoggingNotificationService(TemplatesService templatesService) {
		this.templatesService = templatesService;
	}

	@Override
	public void sendNotification(Notification notification) {
		LocalizedPage notificationTemplate = templatesService.getProcessedTemplate(notification.getTemplatePageUrl(),
			notification.getModel(), notification.getLocale());
		logger.info("Sending notification to: {} Subject: {}\n{}",
			notification.getTo(), notificationTemplate.getTitle(), notificationTemplate.getHtml());
	}

}
