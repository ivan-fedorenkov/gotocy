package org.gotocy.config;

import org.gotocy.filters.LocaleFilter;
import org.gotocy.format.LocationFormatter;
import org.gotocy.format.PropertyTypeFormatter;
import org.gotocy.interceptors.HelpersInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;

/**
 * @author ifedorenkov
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter implements MessageSourceAware {

	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

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

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocationFormatter(messageSource));
		registry.addFormatter(new PropertyTypeFormatter(messageSource));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor());
	}

}
