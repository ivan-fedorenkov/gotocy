package org.gotocy.service;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.SecretKey;
import org.gotocy.repository.GtcUserRepository;
import org.gotocy.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Component
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final PasswordEncoder passwordEncoder;
	private final GtcUserRepository userRepository;
	private final PropertyRepository propertyRepository;

	@Autowired
	public UserServiceImpl(PasswordEncoder passwordEncoder, GtcUserRepository userRepository,
		PropertyRepository propertyRepository)
	{
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public GtcUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public GtcUser register(GtcUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(LocalDate.now());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public GtcUser register(GtcUser user, Long relPropertyId, String relPropertySecret) {
		GtcUser registeredUser = register(user);

		// If there is a related property then the user should become the owner
		if (relPropertyId != null && relPropertySecret != null) {
			Property relProperty = propertyRepository.findOne(relPropertyId);
			if (relProperty != null) {
				SecretKey registrationKey = relProperty.getRegistrationKey();
				if (registrationKey != null && !registrationKey.isExpired()) {
					if (Objects.equals(registrationKey.getKey(), relPropertySecret)) {
						relProperty.setRegisteredOwner(user);
						// If a property has PROMO offer status then it should be changed to INACTIVE
						if (relProperty.getOfferStatus() == OfferStatus.PROMO)
							relProperty.setOfferStatus(OfferStatus.INACTIVE);
						propertyRepository.save(relProperty);
					} else {
						logger.warn("Failed to set user {} as the owner. Registration key mismatch.",
							user.getUsername());
					}
				} else {
					logger.warn("Failed to set user {} as the owner of a property #{}. Registration key has expired.",
						user.getUsername(), relPropertyId);
				}
			} else {
				logger.warn("Failed to set user {} as the owner. Property #{} not found.", user.getUsername(),
					relPropertyId);
			}
		}

		return registeredUser;
	}

	@Override
	@Transactional
	public GtcUser update(GtcUser user) {
		GtcUser existing = userRepository.findOne(user.getId());
		if (existing == null) {
			logger.error("An attempt was made to update the non-existent user #{} {}", user.getId(), user.getUsername());
			throw new ServiceMethodExecutionException();
		}
		existing.setRoles(user.getRoles());
		existing.setContacts(user.getContacts());
		return userRepository.save(existing);
	}

}
