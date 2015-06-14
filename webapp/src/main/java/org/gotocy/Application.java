package org.gotocy;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;


/**
 * @author ifedorenkov
 */

@Controller
@EnableAutoConfiguration
public class Application {

	@Bean
	public Flyway flyway(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
		return flyway;
	}

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
