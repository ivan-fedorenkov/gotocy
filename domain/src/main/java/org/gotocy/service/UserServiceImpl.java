package org.gotocy.service;

import org.gotocy.domain.security.GtcUser;
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
	public GtcUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public GtcUser register(GtcUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(System.currentTimeMillis());
		user.setEnabled(true);
		return userRepository.save(user);
	}

}
