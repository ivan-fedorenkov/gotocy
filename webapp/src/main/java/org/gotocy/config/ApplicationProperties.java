package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Contact;
import org.gotocy.domain.Image;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

	@NotNull
	private Double defaultLatitude;

	@NotNull
	private Double defaultLongitude;

	private Contact defaultContact = new Contact();

	@NotNull
	private Image defaultRepresentativeImage;

	// Expose the spring active profiles
	@Autowired
	private Environment environment;

	/**
	 * @return the first spring active profile or an empty string if there are no active profiles.
	 */
	public String getFirstActiveProfile() {
		String[] activeProfiles = environment.getActiveProfiles();
		return activeProfiles.length > 0 ? activeProfiles[0] : "";
	}

}
