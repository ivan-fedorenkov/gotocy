package org.gotocy.config;

import org.gotocy.beans.AssetsManager;
import org.gotocy.controllers.aop.RequiredDomainObjectAspect;
import org.gotocy.domain.*;
import org.gotocy.filters.LocaleFilter;
import org.gotocy.filters.UrlRewriteFilter;
import org.gotocy.format.EnumsFormatter;
import org.gotocy.format.LocationFormatter;
import org.gotocy.i18n.I18n;
import org.gotocy.interceptors.HelpersInterceptor;
import org.gotocy.interceptors.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
public class WebConfig extends WebMvcConfigurerAdapter {

	private I18n i18n;
	private ApplicationProperties applicationProperties;
	private AssetsManager assetsManager;

	@Autowired
	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Autowired
	public void setI18n(I18n i18n) {
		this.i18n = i18n;
	}

	@Autowired
	public void setAssetsManager(AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
	}

	// Beans

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locales.DEFAULT);
		return resolver;
	}

	@Bean
	public RequiredDomainObjectAspect requiredDomainObjectAspect() {
		return new RequiredDomainObjectAspect();
	}

	// Filters (order is important!)

	@Bean
	@Profile(Profiles.HEROKU_PROD)
	public Filter urlRewriteFilter() {
		return new UrlRewriteFilter();
	}

	@Bean
	public Filter localeFilter() {
		return new LocaleFilter();
	}

	// WebMvcConfigurerAdapter

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocationFormatter());
		registry.addFormatter(new EnumsFormatter<PropertyType>(PropertyType.class) {});
		registry.addFormatter(new EnumsFormatter<PropertyStatus>(PropertyStatus.class) {});
		registry.addFormatter(new EnumsFormatter<OfferStatus>(OfferStatus.class) {});
		registry.addFormatter(new EnumsFormatter<Furnishing>(Furnishing.class) {});
		registry.addFormatter(new EnumsFormatter<BusinessForm>(BusinessForm.class) {});
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor(applicationProperties, assetsManager, i18n));
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/master/**");
	}

}
