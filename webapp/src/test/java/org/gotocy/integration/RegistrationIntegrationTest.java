package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.Contact;
import org.gotocy.domain.ContactType;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.GtcUserRole;
import org.gotocy.repository.GtcUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class RegistrationIntegrationTest extends IntegrationTestBase {
	@Autowired
	private GtcUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void registrationThroughForm() throws Exception {
		String name = "any name";
		String email = "anymail@example.com";
		String password = "password";

		mockMvc.perform(post("/user").with(csrf())
			.param("name", name)
			.param("email", email)
			.param("password", password)
			.param("confirmPassword", password))
			.andExpect(status().is3xxRedirection());

		GtcUser user = userRepository.findByUsername(email);

		Assert.assertNotNull(user);
		Assert.assertEquals(email, user.getUsername());
		Assert.assertTrue(passwordEncoder.matches(password, user.getPassword()));

		Assert.assertThat(user.getContacts(), hasSize(2));
		Assert.assertThat(user.getContacts(), containsInAnyOrder(
			new Contact(ContactType.NAME, name),
			new Contact(ContactType.EMAIL, email)
		));

		Assert.assertThat(user.getRoles(), hasSize(1));
		Assert.assertThat(user.getRoles(), contains(new GtcUserRole(Roles.USER)));
	}

}
