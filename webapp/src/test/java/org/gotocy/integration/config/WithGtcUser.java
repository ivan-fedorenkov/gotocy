package org.gotocy.integration.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

/**
 * @author ifedorenkov
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithGtcUserSecurityContextFactory.class)
public @interface WithGtcUser {

	/**
	 * Username of a user (synonym).
	 */
	String value() default "user";

	/**
	 * Username of a user.
	 */
	String username() default "";

	/**
	 * Roles of a user.
	 */
	String[] roles() default { "USER" };

	/**
	 * Password of a user.
	 */
	String password() default "password";

}
