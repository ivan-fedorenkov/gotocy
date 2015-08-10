package org.gotocy.config;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
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
@Profile("heroku-cleardb")
public class HerokuClearDBConfig {

	@Bean
	public HerokuClearDBProperties herokuPostgreSQLProperties() throws URISyntaxException {
		return new HerokuClearDBProperties();
	}

	@Bean
	public DataSource dataSource(HerokuClearDBProperties properties) {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName(properties.getDriverClassName());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		dataSource.setMaxActive(10);
		dataSource.setInitialSize(5);
		dataSource.setMinIdle(2);
		dataSource.setMaxIdle(5);
		dataSource.setValidationQuery("SELECT 1");
		return dataSource;
	}

}
