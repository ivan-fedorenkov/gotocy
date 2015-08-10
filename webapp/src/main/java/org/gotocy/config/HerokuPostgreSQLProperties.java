package org.gotocy.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
@Component
@Profile("heroku-postgresql")
@ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
public class HerokuPostgreSQLProperties extends DataSourceProperties {

	public HerokuPostgreSQLProperties() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		setUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
		setUsername(dbUri.getUserInfo().split(":")[0]);
		setPassword(dbUri.getUserInfo().split(":")[1]);
	}

	@Override
	public void setUrl(String url) {
		// do nothing
	}

	@Override
	public void setUsername(String username) {
		// do nothing
	}

	@Override
	public void setPassword(String password) {
		// do nothing
	}

}
