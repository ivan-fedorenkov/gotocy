package org.gotocy.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
@Component
@ConfigurationProperties(prefix = "heroku.cleardb")
public class HerokuClearDBProperties implements BeanClassLoaderAware {

	public static final String PREFIX = "heroku.cleardb";

	private ClassLoader classLoader;

	private final String url;
	private final String driverClassName;
	private final String username;
	private final String password;


	public HerokuClearDBProperties() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));

		url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
		driverClassName = "com.mysql.jdbc.Driver";
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

	public String getDriverClassName() {
		return driverClassName;
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
