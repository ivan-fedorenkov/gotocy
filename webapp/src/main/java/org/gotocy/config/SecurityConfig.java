package org.gotocy.config;

import org.gotocy.service.UserDetailsServiceImpl;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ifedorenkov
 */
@Configuration
public class SecurityConfig  {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure the production and the test security paths.
	 */
	private static void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/master/**").hasRole(Roles.MASTER)
			.antMatchers("/**").permitAll()
			.and().formLogin()
			.loginPage("/session/new")
			.loginProcessingUrl("/session");
	}

	@Profile(Profiles.PROD)
	@Configuration
	@EnableWebSecurity
	public static class ProductionSecurityConfig extends WebSecurityConfigurerAdapter implements MessageSourceAware {

		private UserService userService;
		private PasswordEncoder passwordEncoder;
		private MessageSource messageSource;

		@Autowired
		public void setUserService(UserService userService) {
			this.userService = userService;
		}

		@Override
		public void setMessageSource(MessageSource messageSource) {
			this.messageSource = messageSource;
		}

		@Autowired
		public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
			this.passwordEncoder = passwordEncoder;
		}

		@Bean
		public UserDetailsService userDetailsService() {
			return new UserDetailsServiceImpl(messageSource, userService);
		}


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			SecurityConfig.configure(http);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
		}
	}

	@Profile(Profiles.TEST)
	@Configuration
	@EnableWebSecurity
	public static class TestSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			SecurityConfig.configure(http);
		}
	}

	@Profile({Profiles.LOCAL_DEV, Profiles.HEROKU_DEV})
	@Configuration
	@EnableWebSecurity
	public static class DevelopmentSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
				.anyRequest().permitAll();
		}
	}

}
