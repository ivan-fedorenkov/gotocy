package org.gotocy.config;

import org.gotocy.config.Profiles;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
@Configuration
@Profile(Profiles.HEROKU_PROD)
public class HerokuProdDatabaseConfig {

	@Bean
	@ConfigurationProperties(prefix = "heroku.cleardb")
	public DataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));

		String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath() + "?useUnicode=yes&characterEncoding=UTF-8";
		String driverClassName = "com.mysql.jdbc.Driver";
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];

		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

}
