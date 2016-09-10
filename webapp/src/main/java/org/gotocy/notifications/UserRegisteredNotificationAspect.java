package org.gotocy.notifications;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Notification;
import org.gotocy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
	private static final String ADMINS_NOTIFICATION_TEMPLATE_URL = "user-registered-for-admins";
	private static final String USER_NOTIFICATION_TEMPLATE_URL = "user-registered-for-user";

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
		model.put("user", registeredUser);

		notificationService.sendNotification(new Notification(applicationProperties.getEmail(),
			ADMINS_NOTIFICATION_TEMPLATE_URL, LocaleContextHolder.getLocale(), model));
		notificationService.sendNotification(new Notification(registeredUser.getContacts().getEmail(),
			USER_NOTIFICATION_TEMPLATE_URL, LocaleContextHolder.getLocale(), model));
	}

}
