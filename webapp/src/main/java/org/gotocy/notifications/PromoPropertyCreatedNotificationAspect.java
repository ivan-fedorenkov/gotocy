package org.gotocy.notifications;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.Notification;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Notification that should be sent to the support address when a new promo property has been created.
 *
 * @author ifedorenkov
 */
@Aspect
@Component
public class PromoPropertyCreatedNotificationAspect {

	private final ApplicationProperties applicationProperties;
	private final NotificationService notificationService;

	@Autowired
	public PromoPropertyCreatedNotificationAspect(ApplicationProperties applicationProperties,
		NotificationService notificationService)
	{
		this.applicationProperties = applicationProperties;
		this.notificationService = notificationService;
	}

	@AfterReturning(
		value = "execution(org.gotocy.domain.Property org.gotocy.service.PropertyService+.createWithAttachments(..))",
		returning = "createdProperty"
	)
	public void sendNotification(Property createdProperty) {
		if (createdProperty.getOfferStatus() == OfferStatus.PROMO) {
			notificationService.sendNotification(new Notification(applicationProperties.getEmail(),
				"New promo property has been created.", "Property id: " + createdProperty.getId(), null, null));
		}
	}

}
