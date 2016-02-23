package org.gotocy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
@Configuration
@Profile(Profiles.PROD)
public class ProdDatabaseConfig {

	@Bean
	public DatabaseProperties databaseProperties() {
		return new DatabaseProperties();
	}

	@Bean
	@ConfigurationProperties(prefix = "gotocy.db.pool")
	public DataSource dataSource() {
		DatabaseProperties databaseProperties = databaseProperties();
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(databaseProperties.getDriverClassName());
		dataSource.setUrl(databaseProperties.getUrl());
		dataSource.setUsername(databaseProperties.getUsername());
		dataSource.setPassword(databaseProperties.getPassword());
		return dataSource;
	}

}
