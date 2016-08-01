package org.gotocy.service;

import org.gotocy.domain.GtcUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * GTC implementation of Spring's {@link UserDetailsService}.
 *
 * @author ifedorenkov
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

	private final MessageSource messageSource;
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(MessageSource messageSource, UserService userService) {
		this.messageSource = messageSource;
		this.userService = userService;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GtcUser user = userService.findByUsername(username);
		if (user == null) {
			logger.warn("An attempt was made to load user details of non existing user: {}", username);
			throw new UsernameNotFoundException(messageSource.getMessage(
				"org.gotocy.service.UserDetailsService.UserNotFound.message", new Object[]{username},
				"User {0} not found", LocaleContextHolder.getLocale()));
		}
		return user;
	}

}
