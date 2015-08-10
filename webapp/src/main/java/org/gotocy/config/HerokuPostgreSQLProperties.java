package org.gotocy.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
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
public class HerokuPostgreSQLProperties implements BeanClassLoaderAware {

	private ClassLoader classLoader;

	private final String url;
	private final String username;
	private final String password;


	public HerokuPostgreSQLProperties() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		username = dbUri.getUserInfo().split(":")[0];
		password = dbUri.getUserInfo().split(":")[1];
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
