package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.security.GtcUser;
import org.gotocy.domain.security.GtcUserRole;
import org.gotocy.repository.GtcUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
	public void testRegistrationProcedure() throws Exception {
		String email = "anymail@example.com";
		String password = "password";

		mockMvc.perform(post("/user").with(csrf())
			.param("email", email)
			.param("password", password)
			.param("repeatPassword", password))
			.andDo(print())
			.andExpect(status().is3xxRedirection());

		GtcUser user = userRepository.findByEmail(email);
		Assert.assertNotNull(user);
		Assert.assertEquals(email, user.getEmail());
		Assert.assertTrue(passwordEncoder.matches(password, user.getPassword()));
		Assert.assertThat(user.getRoles(), hasSize(1));
		Assert.assertThat(user.getRoles(), contains(new GtcUserRole(Roles.USER)));
		Assert.assertTrue(user.isEnabled());
	}

}
