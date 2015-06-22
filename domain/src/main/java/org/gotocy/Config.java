package org.gotocy;

import org.gotocy.persistance.LocalizedPropertyDao;
import org.gotocy.persistance.jdbc.LocalizedPropertyDaoImpl;
import org.gotocy.persistance.PropertyDao;
import org.gotocy.persistance.jdbc.PropertyDaoImpl;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ifedorenkov
 */
@Configuration
public class Config {

	@Bean
	public PropertyDao propertyDao(DataSourceProperties dsp, DataSource dataSource) {
		return new PropertyDaoImpl(dsp, dataSource);
	}

	@Bean
	public LocalizedPropertyDao localizedPropertyDao(DataSourceProperties dsp, DataSource dataSource) {
		return new LocalizedPropertyDaoImpl(dsp, dataSource);
	}

}
