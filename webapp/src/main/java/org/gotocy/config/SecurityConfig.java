package org.gotocy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author ifedorenkov
 */
@Configuration
public class SecurityConfig  {

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
	public static class ProductionSecurityConfig extends WebSecurityConfigurerAdapter {

		@Bean
		SecurityProperties securityProperties() {
			return new SecurityProperties();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			SecurityConfig.configure(http);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
				.inMemoryAuthentication()
				.withUser(securityProperties().getLogin())
				.password(securityProperties().getPassword())
				.roles(Roles.MASTER);
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
