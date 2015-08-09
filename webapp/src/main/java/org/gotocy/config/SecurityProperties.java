package org.gotocy.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ifedorenkov
 */
@Component
@ConfigurationProperties(prefix = "gotocy.webapp.security")
public class SecurityProperties {

	public static final String SESSION_KEY = "authorized";

	private String login;
	private String password;

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
