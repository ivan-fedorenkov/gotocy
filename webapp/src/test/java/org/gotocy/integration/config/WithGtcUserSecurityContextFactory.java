package org.gotocy.integration.config;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.GtcUserRole;
import org.gotocy.service.UserService;
import org.gotocy.test.factory.ContactsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

import java.util.Arrays;

import static java.util.stream.Collectors.toSet;

/**
 * @author ifedorenkov
 */
public class WithGtcUserSecurityContextFactory implements WithSecurityContextFactory<WithGtcUser> {

	private final UserService userService;

	@Autowired
	public WithGtcUserSecurityContextFactory(UserService userService) {
		this.userService = userService;
	}

	@Override
	public SecurityContext createSecurityContext(WithGtcUser withGtcUser) {
		String username = withGtcUser.value() == null ? withGtcUser.username() : withGtcUser.value();
		Assert.hasLength(username, "value() must be non empty String");
		GtcUser principal = userService.findByUsername(withGtcUser.username());
		if (principal == null) {
			GtcUser registering = new GtcUser();
			registering.setUsername(username);
			registering.setPassword(withGtcUser.password());
			registering.setRoles(Arrays.stream(withGtcUser.roles())
				.map(role -> "ROLE_" + role)
				.map(GtcUserRole::new)
				.collect(toSet()));
			registering.setContacts(ContactsFactory.INSTANCE.get());
			principal = userService.register(registering);
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(
			principal, principal.getPassword(), principal.getAuthorities());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}

}
