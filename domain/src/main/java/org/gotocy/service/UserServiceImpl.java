package org.gotocy.service;

import org.gotocy.domain.Contact;
import org.gotocy.domain.security.GtcUser;
import org.gotocy.repository.ContactRepository;
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
	private final ContactRepository contactRepository;
	private final GtcUserRepository userRepository;

	@Autowired
	public UserServiceImpl(PasswordEncoder passwordEncoder, ContactRepository contactRepository,
		GtcUserRepository userRepository)
	{
		this.passwordEncoder = passwordEncoder;
		this.contactRepository = contactRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public GtcUser findByUsername(String email) {
		return userRepository.findByUsername(email);
	}

	@Override
	@Transactional
	public GtcUser register(GtcUser user, Contact contact) {
		// Save user contact
		contact = contactRepository.save(contact);
		// Save user
		user.setContact(contact);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(System.currentTimeMillis());
		return userRepository.save(user);
	}

}
