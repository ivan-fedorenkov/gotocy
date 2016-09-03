package org.gotocy.config;

import org.gotocy.controllers.aop.RequiredDomainObjectAspect;
import org.gotocy.domain.*;
import org.gotocy.dto.PropertyDtoFactory;
import org.gotocy.filters.LocaleFilter;
import org.gotocy.filters.UrlRewriteFilter;
import org.gotocy.format.EnumsFormatter;
import org.gotocy.format.LocationFormatter;
import org.gotocy.format.seo.SeoPropertySearchFormUriFormatter;
import org.gotocy.helpers.Helper;
import org.gotocy.i18n.I18n;
import org.gotocy.interceptors.HelpersInterceptor;
import org.gotocy.repository.PageRepository;
import org.gotocy.service.AssetsService;
import org.gotocy.service.MailNotificationService;
import org.gotocy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ifedorenkov
 */
@Configuration
@EnableWebSecurity
public class WebConfig extends WebMvcConfigurerAdapter {

	private I18n i18n;
	private ApplicationProperties applicationProperties;
	private AssetsService assetsService;
	private PageRepository pageRepository;
	private JavaMailSender javaMailSender;

	@Autowired
	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Autowired
	public void setI18n(I18n i18n) {
		this.i18n = i18n;
	}

	@Autowired
	public void setAssetsService(AssetsService assetsService) {
		this.assetsService = assetsService;
	}

	@Autowired
	public void setPageRepository(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	// Beans

	@Bean
	public ExecutorService executorService() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	public Helper helper() {
		return new Helper(applicationProperties, assetsService, pageRepository);
	}

	@Bean
	public PropertyDtoFactory propertyDtoFactory() {
		return new PropertyDtoFactory(helper().getProperty());
	}

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

	@Bean
	public NotificationService notificationService() throws Exception {
		return new MailNotificationService(applicationProperties, getFreemarkerConfiguration(), javaMailSender,
			executorService());
	}

	@Bean
	public freemarker.template.Configuration getFreemarkerConfiguration() throws Exception {
		FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
		factory.setTemplateLoaderPath("/mail/");
		return factory.createConfiguration();
	}

	// Filters (order is important!)

	@Bean
	@Profile(Profiles.PROD)
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
		registry.addFormatter(new EnumsFormatter<OfferType>(OfferType.class) {});
		registry.addFormatter(new EnumsFormatter<OfferStatus>(OfferStatus.class) {});
		registry.addFormatter(new EnumsFormatter<Furnishing>(Furnishing.class) {});
		registry.addFormatter(new EnumsFormatter<BusinessForm>(BusinessForm.class) {});
		registry.addFormatter(new SeoPropertySearchFormUriFormatter());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor(applicationProperties, i18n, helper()));
	}

}
