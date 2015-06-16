package org.gotocy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author ifedorenkov
 */
@SpringBootApplication
public class Application {

	// TODO: move this code to a configuration class
	/*@Bean
	public Flyway flyway(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
		return flyway;
	}*/


	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
