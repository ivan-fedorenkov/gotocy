package org.gotocy.notifications;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Notification;
import org.gotocy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Notification that should be sent when a new user has been registered.
 *
 * @author ifedorenkov
 */
@Aspect
@Component
public class UserRegisteredNotificationAspect {
	private static final String VIEW_NAME = "userRegisteredNotification.txt";

	private final ApplicationProperties applicationProperties;
	private final NotificationService notificationService;

	@Autowired
	public UserRegisteredNotificationAspect(ApplicationProperties applicationProperties,
		NotificationService notificationService)
	{
		this.applicationProperties = applicationProperties;
		this.notificationService = notificationService;
	}

	@AfterReturning(
		value = "execution(org.gotocy.domain.GtcUser org.gotocy.service.UserService+.register(..))",
		returning = "registeredUser"
	)
	public void sendNotification(GtcUser registeredUser) {
		Map<String, Object> model = new HashMap<>();
		model.put("name", registeredUser.getContacts().getName());
		model.put("email", registeredUser.getContacts().getEmail());

		notificationService.sendNotification(new Notification(applicationProperties.getEmail(),
			"New user has been registered", "User: " + registeredUser.getUsername(), VIEW_NAME, model));
	}

}
