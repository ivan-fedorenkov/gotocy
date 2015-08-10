package org.gotocy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
@Configuration
@Profile("heroku-postgresql")
@EnableConfigurationProperties(HerokuPostgreSQLProperties.class)
public class HerokuPostgreSQLConfig {

	@Autowired
	private HerokuPostgreSQLProperties properties;

	@Bean
	public DataSource dataSource() {
		DataSourceBuilder factory = DataSourceBuilder
			.create(properties.getClassLoader())
			.driverClassName(properties.getDriverClassName())
			.url(properties.getUrl())
			.username(properties.getUsername())
			.password(properties.getPassword());
		return factory.build();
	}

}
