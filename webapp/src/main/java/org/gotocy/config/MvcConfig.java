package org.gotocy.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import org.gotocy.beans.S3Configuration;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.filters.LocaleFilter;
import org.gotocy.format.EnumsFormatter;
import org.gotocy.interceptors.HelpersInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({S3Configuration.class})
public class MvcConfig extends WebMvcConfigurerAdapter implements MessageSourceAware {

	private MessageSource messageSource;

	private S3Configuration s3Configuration;
	private AmazonS3Client s3Client;

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

	@Bean
	public S3Configuration s3Configuration() {
		return s3Configuration = new S3Configuration();
	}

	@Bean
	AmazonS3Client amazonS3Client() {
		return s3Client = new AmazonS3Client(new EnvironmentVariableCredentialsProvider());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new EnumsFormatter<>(Location.class, messageSource));
		registry.addFormatter(new EnumsFormatter<>(PropertyType.class, messageSource));
		registry.addFormatter(new EnumsFormatter<>(PropertyStatus.class, messageSource));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HelpersInterceptor(s3Client, s3Configuration));
	}

}
