package org.gotocy;

import org.gotocy.filters.LocaleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;


/**
 * @author ifedorenkov
 */
@SpringBootApplication
public class Application {

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(LocaleFilter.DEFAULT_LOCALE);
		return resolver;
	}

	@Bean
	public Filter localeFilter() {
		return new LocaleFilter();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
