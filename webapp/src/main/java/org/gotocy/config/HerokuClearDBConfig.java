package org.gotocy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
@Configuration
@Profile("heroku-cleardb")
public class HerokuClearDBConfig {

	@Bean
	public HerokuClearDBProperties herokuClearDBProperties() throws URISyntaxException {
		return new HerokuClearDBProperties();
	}

	@ConfigurationProperties(prefix = HerokuClearDBProperties.PREFIX)
	@Bean
	public DataSource dataSource(HerokuClearDBProperties properties) {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(properties.getDriverClassName());
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		return dataSource;
	}

}
