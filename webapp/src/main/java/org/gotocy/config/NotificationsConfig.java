package org.gotocy.config;

import org.gotocy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.ExecutorService;

/**
 * @author ifedorenkov
 */
@Configuration
public class NotificationsConfig {

	@Configuration
	@Profile(Profiles.PROD)
	public static class ProdConfig {

		@Autowired
		private ApplicationProperties applicationProperties;

		@Autowired
		private JavaMailSender javaMailSender;

		@Autowired
		private ExecutorService executorService;

		@Autowired
		private TemplatesService templatesService;

		@Autowired
		private PageService pageService;

		@Bean
		public NotificationService mailNotificationService() throws Exception {
			return new MailNotificationService(applicationProperties,
				javaMailSender, pageService, templatesService, executorService);
		}

	}

	@Configuration
	@Profile({Profiles.DEV, Profiles.TEST})
	public static class DevConfig {

		@Bean
		public NotificationService loggingNotificationService() {
			return new LoggingNotificationService();
		}

	}

}
