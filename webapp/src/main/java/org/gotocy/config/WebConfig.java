package org.gotocy.config;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.Furnishing;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.filters.LocaleFilter;
import org.gotocy.filters.UrlRewriteFilter;
import org.gotocy.format.EnumsFormatter;
import org.gotocy.interceptors.HelpersInterceptor;
import org.gotocy.interceptors.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.annotation.Priority;
import javax.servlet.Filter;

/**
 * @author ifedorenkov
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements MessageSourceAware {

	private MessageSource messageSource;
	private AssetsProvider assetsProvider;
	private ApplicationProperties applicationProperties;

	@Autowired
	public void setAssetsProvider(AssetsProvider assetsProvider) {
		this.assetsProvider = assetsProvider;
	}

	@Autowired
	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

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

	@Profile(value = "production")
	@Bean
	public Filter urlRewriteFilter() {
		return new UrlRewriteFilter();
	}

	@Bean
	public Filter localeFilter() {
		return new LocaleFilter();
	}


	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new EnumsFormatter<Location>(Location.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<PropertyType>(PropertyType.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<PropertyStatus>(PropertyStatus.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<Furnishing>(Furnishing.class, messageSource) {});
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor(applicationProperties, messageSource, assetsProvider));
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/master/**");
	}

}
