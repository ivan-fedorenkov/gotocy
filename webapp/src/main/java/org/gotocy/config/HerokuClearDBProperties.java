package org.gotocy.config;

import org.springframework.beans.factory.BeanClassLoaderAware;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
public class HerokuClearDBProperties implements BeanClassLoaderAware {

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
