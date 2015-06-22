package org.gotocy;

import org.gotocy.persistance.LocalizedPropertyDao;
import org.gotocy.persistance.jdbc.LocalizedPropertyDaoImpl;
import org.gotocy.persistance.PropertyDao;
import org.gotocy.persistance.jdbc.PropertyDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
@Configuration
public class Config {

	@Bean
	public PropertyDao propertyDao(DataSource dataSource) {
		return new PropertyDaoImpl(dataSource);
	}

	@Bean
	public LocalizedPropertyDao localizedPropertyDao(DataSource dataSource) {
		return new LocalizedPropertyDaoImpl(dataSource);
	}

}
