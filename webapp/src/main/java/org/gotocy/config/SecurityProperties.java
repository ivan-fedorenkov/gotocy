package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ifedorenkov
 */
@ConfigurationProperties(prefix = "gotocy.webapp.security")
@Getter
@Setter
public class SecurityProperties {

	private String login;
	private String password;

}
