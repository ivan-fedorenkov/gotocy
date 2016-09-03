package org.gotocy.service;

import org.gotocy.domain.Notification;

/**
 * A service for sending notifications.
 *
 * @author ifedorenkov
 */
public interface NotificationService {

	/**
	 * Sends the given notification.
	 *
	 * @param notification that should be sent
	 * @throws ServiceMethodExecutionException if anything goes wrong
	 */
	void sendNotification(Notification notification) throws ServiceMethodExecutionException;

}
