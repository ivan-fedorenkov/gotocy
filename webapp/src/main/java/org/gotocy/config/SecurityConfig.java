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
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
		RedirectStrategy redirectStrategy = new LocaleAwareRedirectStrategy();

		SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler =
			new SavedRequestAwareAuthenticationSuccessHandler();
		authenticationSuccessHandler.setDefaultTargetUrl("/user/properties");
		authenticationSuccessHandler.setRedirectStrategy(redirectStrategy);

		SimpleUrlAuthenticationFailureHandler authenticationFailureHandler =
			new SimpleUrlAuthenticationFailureHandler("/login?error");
		authenticationFailureHandler.setRedirectStrategy(redirectStrategy);

		http
			.authorizeRequests()
			.antMatchers(anyLocale("/master/**")).hasRole(Roles.MASTER)
			.antMatchers(anyLocale("/user/**")).hasRole(Roles.USER)
			.antMatchers("/**").permitAll()
			.and().formLogin()
				.loginPage("/login")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
			.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));

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

	/**
	 * Spring Security messes up LocaleContextHolder for some reason so we have to extract the
	 * current locale manually.
	 */
	private static class LocaleAwareRedirectStrategy implements RedirectStrategy {
		private static final Logger logger = LoggerFactory.getLogger(LocaleAwareRedirectStrategy.class);

		@Override
		public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
			throws IOException
		{
			Locale currentLocale = Optional.ofNullable((Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)).orElse(Locales.DEFAULT);
			String redirectUrl = Helper.path(url, currentLocale);
			logger.debug("Redirecting to '{}'", redirectUrl);
			response.sendRedirect(redirectUrl);
		}
	}

}
