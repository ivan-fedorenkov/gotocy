package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.*;
import org.gotocy.repository.GtcUserRepository;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.ContactsFactory;
import org.gotocy.test.factory.GtcUserFactory;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * @author ifedorenkov
 */
public class UserRegistrationIntegrationTest extends IntegrationTestBase {
	@Autowired
	private GtcUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PropertyRepository propertyRepository;

	private GtcUser registeringUser;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		registeringUser = GtcUserFactory.INSTANCE.get(user -> {
			user.setContacts(ContactsFactory.INSTANCE.get(contacts -> {
				contacts.setPhone(null);
				contacts.setSpokenLanguages(null);
			}));
			user.setRoles(Collections.singleton(new GtcUserRole(Roles.ROLE_USER)));
		});
	}

	@Test
	public void registrationThroughForm() throws Exception {
		mvc.perform(post("/users").with(csrf())
			.param("name", registeringUser.getContacts().getName())
			.param("email", registeringUser.getContacts().getEmail())
			.param("password", registeringUser.getPassword())
			.param("confirmPassword", registeringUser.getPassword()))
			.andExpect(redirectedUrl("/user/welcome"));

		GtcUser registeredUser = userRepository.findByUsername(registeringUser.getUsername());

		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeringUser.getUsername(), registeredUser.getUsername());
		Assert.assertTrue(passwordEncoder.matches(registeringUser.getPassword(), registeredUser.getPassword()));
		Assert.assertEquals(registeringUser.getContacts(), registeredUser.getContacts());
		Assert.assertEquals(registeringUser.getRoles(), registeredUser.getRoles());
	}

	@Test
	public void registrationThroughPromo() throws Exception {
		SecretKey registrationKey = new SecretKey();
		registrationKey.setKey("any-secret-key");
		registrationKey.setEol(LocalDate.now().plusDays(1));
		Property promo = PropertyFactory.INSTANCE.get(p -> {
			p.setOfferStatus(OfferStatus.PROMO);
			p.setRegistrationKey(registrationKey);
		});
		promo = propertyRepository.save(promo);

		mvc.perform(post("/users").with(csrf())
			.param("name", registeringUser.getContacts().getName())
			.param("email", registeringUser.getContacts().getEmail())
			.param("password", registeringUser.getPassword())
			.param("confirmPassword", registeringUser.getPassword())
			.param("relPropertyId", Long.toString(promo.getId()))
			.param("relPropertySecret", registrationKey.getKey()))
			.andExpect(redirectedUrl("/user/welcome"));

		GtcUser registeredUser = userRepository.findByUsername(registeringUser.getUsername());
		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeringUser.getUsername(), registeredUser.getUsername());
		Assert.assertTrue(passwordEncoder.matches(registeringUser.getPassword(), registeredUser.getPassword()));
		Assert.assertEquals(registeringUser.getContacts(), registeredUser.getContacts());
		Assert.assertEquals(registeringUser.getRoles(), registeredUser.getRoles());

		Property exPromo = propertyRepository.findOne(promo.getId());
		Assert.assertEquals(OfferStatus.INACTIVE, exPromo.getOfferStatus());
		Assert.assertEquals(exPromo.getOwner(), registeredUser);
	}

}
