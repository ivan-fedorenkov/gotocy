package org.gotocy.config;

import org.gotocy.helpers.Helper;
import org.gotocy.service.UserDetailsServiceImpl;
import org.gotocy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements MessageSourceAware {

	private MessageSource messageSource;
	private UserService userService;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(messageSource, userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(anyLocale("/master/**")).hasRole(Roles.MASTER)
			.antMatchers(anyLocale("/user/**")).hasRole(Roles.USER)
			.antMatchers("/**").permitAll()
			.and().formLogin().failureHandler(new FailureHandler())
			.loginPage("/session/new")
			.loginProcessingUrl("/session");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	private static String[] anyLocale(String path) {
		return Locales.SUPPORTED.stream()
			.map(locale -> Helper.path(path, locale))
			.collect(toList())
			.toArray(new String[Locales.SUPPORTED.size()]);
	}

	public static class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
		public FailureHandler() {
			super("/session/new");

			setRedirectStrategy((request, response, url) -> {
				// Somehow Spring messes up LocaleContextHolder value so we have to extract the
				// current locale manually
				Locale locale = Optional.ofNullable((Locale) request.getSession().getAttribute(
					SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)).orElse(Locales.DEFAULT);
				String redirectUrl = Helper.path(url, locale);

				if (logger.isDebugEnabled()) {
					logger.debug("Redirecting to '" + redirectUrl + "'");
				}

				response.sendRedirect(redirectUrl);
			});
		}


	}

}
