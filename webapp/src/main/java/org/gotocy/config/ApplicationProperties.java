package org.gotocy.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author ifedorenkov
 */
@Component
@ConfigurationProperties(prefix = "gotocy.webapp")
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Double getDefaultLatitude() {
		return defaultLatitude;
	}

	public void setDefaultLatitude(Double defaultLatitude) {
		this.defaultLatitude = defaultLatitude;
	}

	public Double getDefaultLongitude() {
		return defaultLongitude;
	}

	public void setDefaultLongitude(Double defaultLongitude) {
		this.defaultLongitude = defaultLongitude;
	}
}
