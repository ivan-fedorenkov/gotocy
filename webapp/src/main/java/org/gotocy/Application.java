package org.gotocy;

import org.gotocy.beans.Migrator;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.LocalizedPropertySpecification;
import org.gotocy.domain.Property;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;


/**
 * @author ifedorenkov
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		Migrator migrator = ctx.getBean(Migrator.class);
		migrator.migrate();

	}

}
