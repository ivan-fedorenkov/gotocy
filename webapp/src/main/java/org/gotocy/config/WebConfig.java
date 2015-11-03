package org.gotocy.config;

import org.gotocy.beans.AssetsManager;
import org.gotocy.controllers.aop.RequiredDomainObjectAspect;
import org.gotocy.domain.*;
import org.gotocy.filters.LocaleFilter;
import org.gotocy.filters.UrlRewriteFilter;
import org.gotocy.format.EnumsFormatter;
import org.gotocy.format.LocationFormatter;
import org.gotocy.interceptors.HelpersInterceptor;
import org.gotocy.interceptors.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpEncodingProperties;
import org.springframework.boot.context.web.OrderedCharacterEncodingFilter;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
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
public class WebConfig extends WebMvcConfigurerAdapter implements MessageSourceAware {

	private MessageSource messageSource;
	private ApplicationProperties applicationProperties;
	private AssetsManager assetsManager;
	private HttpEncodingProperties httpEncodingProperties;

	@Autowired
	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Autowired
	public void setAssetsManager(AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
	}

	@Autowired
	public void setHttpEncodingProperties(HttpEncodingProperties httpEncodingProperties) {
		this.httpEncodingProperties = httpEncodingProperties;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// Beans

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locales.DEFAULT);
		return resolver;
	}

	@Bean
	public Filter localeFilter() {
		return new LocaleFilter();
	}

	// TODO: remove after 1.3.0.RC1 and check the regression
	@Bean
	public OrderedCharacterEncodingFilter characterEncodingFilter() {
		OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.httpEncodingProperties.getCharset().name());
		filter.setForceEncoding(this.httpEncodingProperties.isForce());
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filter;
	}

	@Bean
	public RequiredDomainObjectAspect requiredDomainObjectAspect() {
		return new RequiredDomainObjectAspect();
	}

	// Production beans

	@Bean
	@Profile(Profiles.HEROKU_PROD)
	public Filter urlRewriteFilter() {
		return new UrlRewriteFilter();
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocationFormatter(messageSource));
		registry.addFormatter(new EnumsFormatter<PropertyType>(PropertyType.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<PropertyStatus>(PropertyStatus.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<OfferStatus>(OfferStatus.class, messageSource) {});
		registry.addFormatter(new EnumsFormatter<Furnishing>(Furnishing.class, messageSource) {});
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor(applicationProperties, messageSource, assetsManager));
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/master/**");
	}

}
