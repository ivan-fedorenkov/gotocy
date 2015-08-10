package org.gotocy.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.net.URISyntaxException;

/**
 * @author ifedorenkov
 */
@Configuration
@Profile("heroku-postgresql")
public class HerokuClearDBConfig {

	@Bean
	public HerokuClearDBProperties herokuPostgreSQLProperties() throws URISyntaxException {
		return new HerokuClearDBProperties();
	}

	@Bean
	public DataSource dataSource(HerokuClearDBProperties properties) {
		DataSourceBuilder factory = DataSourceBuilder
			.create(properties.getClassLoader())
			.driverClassName(properties.getDriverClassName())
			.url(properties.getUrl())
			.username(properties.getUsername())
			.password(properties.getPassword());
		return factory.build();
	}

}
