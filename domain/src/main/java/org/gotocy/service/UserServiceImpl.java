package org.gotocy.service;

import org.gotocy.domain.GtcUser;
import org.gotocy.repository.GtcUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ifedorenkov
 */
@Component
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final GtcUserRepository userRepository;

	@Autowired
	public UserServiceImpl(PasswordEncoder passwordEncoder, GtcUserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public GtcUser findByUsername(String email) {
		return userRepository.findByUsername(email);
	}

	@Override
	@Transactional
	public GtcUser register(GtcUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(System.currentTimeMillis());
		return userRepository.save(user);
	}

}
