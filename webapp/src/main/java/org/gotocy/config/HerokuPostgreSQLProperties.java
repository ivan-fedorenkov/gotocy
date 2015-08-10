package org.gotocy.config;

import org.springframework.beans.factory.BeanClassLoaderAware;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
public class HerokuPostgreSQLProperties implements BeanClassLoaderAware {

	private ClassLoader classLoader;

	private final String url;
	private final String driverClassName;
	private final String username;
	private final String password;


	public HerokuPostgreSQLProperties() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
		driverClassName = "org.postgresql.Driver";
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
