package org.gotocy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * @author ifedorenkov
 */
@ConfigurationProperties(prefix = "gotocy.db")
@Getter
@Setter
public class DatabaseProperties {

	@NotNull
	private String url;

	@NotNull
	private String driverClassName;

	@NotNull
	private String username;

	@NotNull
	private String password;

}
