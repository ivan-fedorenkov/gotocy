package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Contact;
import org.gotocy.domain.Image;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author ifedorenkov
 */
@Component
@ConfigurationProperties(prefix = "gotocy.webapp")
@Getter
@Setter
public class ApplicationProperties {

	@NotEmpty
	private String email;

	@NotEmpty
	private String phone;

	private double defaultLatitude;

	private double defaultLongitude;

	private Contact defaultContact = new Contact();

	@NotNull
	private Image defaultRepresentativeImage;

	// Expose the spring active profiles
	@Autowired
	private Environment environment;

	@NestedConfigurationProperty
	private final UserPropertyForm userPropertyForm = new UserPropertyForm();

	/**
	 * @return the first spring active profile or an empty string if there are no active profiles.
	 */
	public String getFirstActiveProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		return activeProfiles.length > 0 ? activeProfiles[0] : "";
	}

	@Getter
	@Setter
	public static class UserPropertyForm {

		/**
		 * Max file size in Kb.
		 */
		private int maxFileSize = 3072;

		/**
		 * Max file count.
		 */
		private int maxFileCount = 10;

		/**
		 * Returns max file size in Mb.
		 */
		public int getMaxFileSizeMb() {
			return maxFileSize / 1024;
		}

	}

}
