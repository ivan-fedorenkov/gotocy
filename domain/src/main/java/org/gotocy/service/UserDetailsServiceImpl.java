package org.gotocy.service;

import org.gotocy.domain.security.GtcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * GTC implementation of Spring's {@link UserDetailsService}.
 *
 * @author ifedorenkov
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MessageSource messageSource;
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(MessageSource messageSource, UserService userService) {
		this.messageSource = messageSource;
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GtcUser user = userService.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(messageSource.getMessage(
				"org.gotocy.service.UserDetailsService.userNotFound", new Object[]{username},
				"User {0} not found", LocaleContextHolder.getLocale()));
		}
		return new User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
	}

}
