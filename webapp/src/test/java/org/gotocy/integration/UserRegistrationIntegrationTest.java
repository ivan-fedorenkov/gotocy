package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.*;
import org.gotocy.repository.GtcUserRepository;
import org.gotocy.service.PropertyService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class UserRegistrationIntegrationTest extends IntegrationTestBase {
	@Autowired
	private GtcUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PropertyService propertyService;

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
		mockMvc.perform(post("/users").with(csrf())
			.param("name", registeringUser.getContacts().getName())
			.param("email", registeringUser.getContacts().getEmail())
			.param("password", registeringUser.getPassword())
			.param("confirmPassword", registeringUser.getPassword()))
			.andExpect(status().is3xxRedirection());

		GtcUser registeredUser = userRepository.findByUsername(registeringUser.getUsername());

		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeringUser.getUsername(), registeredUser.getUsername());
		Assert.assertTrue(passwordEncoder.matches(registeringUser.getPassword(), registeredUser.getPassword()));
		Assert.assertEquals(registeringUser.getContacts(), registeredUser.getContacts());
		Assert.assertEquals(registeringUser.getRoles(), registeredUser.getRoles());
	}

	@Test
	public void registrationThroughPromo() throws Exception {
		SecretKey registrationKey = propertyService.generateRegistrationSecret();
		Property promo = PropertyFactory.INSTANCE.get(p -> {
			p.setOfferStatus(OfferStatus.PROMO);
			p.setRegistrationKey(registrationKey);
		});
		promo = propertyService.create(promo);

		mockMvc.perform(post("/users").with(csrf())
			.param("name", registeringUser.getContacts().getName())
			.param("email", registeringUser.getContacts().getEmail())
			.param("password", registeringUser.getPassword())
			.param("confirmPassword", registeringUser.getPassword())
			.param("relPropertyId", Long.toString(promo.getId()))
			.param("relPropertySecret", registrationKey.getKey()))
			.andExpect(status().is3xxRedirection());

		GtcUser registeredUser = userRepository.findByUsername(registeringUser.getUsername());
		Assert.assertEquals(LocalDate.now(), registeredUser.getRegistrationDate());
		Assert.assertEquals(registeringUser.getUsername(), registeredUser.getUsername());
		Assert.assertTrue(passwordEncoder.matches(registeringUser.getPassword(), registeredUser.getPassword()));
		Assert.assertEquals(registeringUser.getContacts(), registeredUser.getContacts());
		Assert.assertEquals(registeringUser.getRoles(), registeredUser.getRoles());

		Property exPromo = propertyService.findOne(promo.getId());
		Assert.assertEquals(OfferStatus.INACTIVE, exPromo.getOfferStatus());
		Assert.assertEquals(exPromo.getOwner(), registeredUser);
	}

}
