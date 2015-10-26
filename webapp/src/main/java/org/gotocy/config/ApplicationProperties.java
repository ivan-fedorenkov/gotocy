package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Contact;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

	@NotEmpty
	private String profile;

	@NotNull
	private Double defaultLatitude;

	@NotNull
	private Double defaultLongitude;

	private Contact defaultContact = new Contact();

}
