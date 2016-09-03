package org.gotocy.service;

import org.gotocy.domain.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ifedorenkov
 */
public class LoggingNotificationService implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(LoggingNotificationService.class);

	@Override
	public void sendNotification(Notification notification) {
		logger.info("Sending notification to: {}, subject: {}, message: {}",
			notification.getTo(), notification.getSubject(), notification.getMessage());
	}

}
