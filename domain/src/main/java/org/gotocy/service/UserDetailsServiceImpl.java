package org.gotocy.service;

import org.gotocy.domain.security.GtcUser;
import org.gotocy.repository.GtcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final GtcUserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(GtcUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GtcUser user = userRepository.findByUsername(username);
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
	}

}
