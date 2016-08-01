package org.gotocy.service;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.SecretKey;
import org.gotocy.repository.GtcUserRepository;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.ContactsFactory;
import org.gotocy.test.factory.GtcUserFactory;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ifedorenkov
 */
public class UserServiceTest {

	private PropertyRepository propertyRepository;
	private GtcUserRepository userRepository;
	private UserService userService;

	@Before
	public void setUp() {
		PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
		userRepository = mock(GtcUserRepository.class);
		when(userRepository.save(any(GtcUser.class))).then(returnsFirstArg());
		propertyRepository = mock(PropertyRepository.class);
		userService = new UserServiceImpl(passwordEncoder, userRepository, propertyRepository);
	}

	@Test
	public void testRegistration() {
		GtcUser registeringUser = createRegisteringUser();
		GtcUser registeredUser = userService.register(registeringUser);
		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
	}

	@Test
	public void testRegistrationWithRelatedProperty() {
		Property relProperty = mockExistingProperty();
		GtcUser registeringUser = createRegisteringUser();

		OfferStatus originalStatus = relProperty.getOfferStatus();
		GtcUser registeredUser = userService.register(registeringUser, relProperty.getId(),
			relProperty.getRegistrationKey().getKey());

		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeredUser, relProperty.getOwner());
		Assert.assertNull(relProperty.getRegistrationKey());
		Assert.assertEquals(originalStatus, relProperty.getOfferStatus());
	}

	@Test
	public void testRegistrationWithRelatedPromoProperty() {
		Property relProperty = mockExistingProperty();
		relProperty.setOfferStatus(OfferStatus.PROMO);
		GtcUser registeringUser = createRegisteringUser();

		GtcUser registeredUser = userService.register(registeringUser, relProperty.getId(),
			relProperty.getRegistrationKey().getKey());

		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeredUser, relProperty.getOwner());
		Assert.assertNull(relProperty.getRegistrationKey());
		Assert.assertEquals(OfferStatus.INACTIVE, relProperty.getOfferStatus());
	}

	@Test
	public void testRegistrationWithExpiredPropertyKey() {
		Property relProperty = mockExistingProperty();
		// Expire the registration key
		relProperty.getRegistrationKey().setEol(LocalDate.now().minusDays(1));
		GtcUser registeringUser = createRegisteringUser();

		OfferStatus originalStatus = relProperty.getOfferStatus();
		GtcUser registeredUser = userService.register(registeringUser, relProperty.getId(),
			relProperty.getRegistrationKey().getKey());

		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertNull(relProperty.getOwner());
		Assert.assertEquals(originalStatus, relProperty.getOfferStatus());
	}

	/**
	 * {@link UserService#update(GtcUser)} most not touch credentials fields.
	 */
	@Test
	public void testAttemptToUpdateCredentialsData() {
		// Pretend there is some user
		GtcUser existing = GtcUserFactory.INSTANCE.get(user -> {
			user.setId(1);
			user.setRegistrationDate(LocalDate.now().minusMonths(1));
			user.setContacts(ContactsFactory.INSTANCE.get());
		});
		Mockito.when(userRepository.findOne(existing.getId())).thenReturn(existing);
		String existingUsername = existing.getUsername();
		String existingPassword = existing.getPassword();
		LocalDate existingRegistrationDate = existing.getRegistrationDate();

		// Try to update username and password
		GtcUser updating = new GtcUser();
		updating.setId(existing.getId());
		updating.setUsername("something else");
		updating.setPassword(null);
		updating.setContacts(existing.getContacts());
		updating.setRoles(existing.getRoles());
		updating.setRegistrationDate(LocalDate.now());
		GtcUser updated = userService.update(updating);

		// Should silently discard changes
		Assert.assertEquals(existingUsername, updated.getUsername());
		Assert.assertEquals(existingPassword, updated.getPassword());
		Assert.assertEquals(existingRegistrationDate, updated.getRegistrationDate());
	}

	private Property mockExistingProperty() {
		String registrationKey = "any key";
		Long propertyId = 1L;
		SecretKey relPropertyRegistrationKey = new SecretKey();
		relPropertyRegistrationKey.setKey(registrationKey);
		relPropertyRegistrationKey.setEol(LocalDate.MAX);

		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setId(propertyId);
			p.setRegistrationKey(relPropertyRegistrationKey);
		});
		when(propertyRepository.findOne(propertyId)).thenReturn(property);
		return property;
	}

	private GtcUser createRegisteringUser() {
		return GtcUserFactory.INSTANCE.get(user -> user.setContacts(ContactsFactory.INSTANCE.get()));
	}

}
