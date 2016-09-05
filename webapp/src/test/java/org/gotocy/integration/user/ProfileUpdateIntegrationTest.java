package org.gotocy.integration.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.integration.config.WithGtcUser;
import org.gotocy.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * @author ifedorenkov
 */
public class ProfileUpdateIntegrationTest extends IntegrationTestBase {

	@Autowired
	private UserService userService;

	@Test
	@WithGtcUser(username = "user")
	public void testChangeProfile() throws Exception {
		String updatedName = "updated name";
		String updatedEmail = "support@gotocy.com";
		String updatedPhone = "updated phone";
		String updatedSpokenLanguages = "en;ru";

		mvc.perform(put("/user/profile").with(csrf())
			.param("name", updatedName)
			.param("email", updatedEmail)
			.param("phone", updatedPhone)
			.param("spokenLanguages", updatedSpokenLanguages))
			.andExpect(redirectedUrl("/user/profile"));

		GtcUser updated = userService.findByUsername("user");
		Assert.assertEquals(updatedName, updated.getContacts().getName());
		Assert.assertEquals(updatedEmail, updated.getContacts().getEmail());
		Assert.assertEquals(updatedPhone, updated.getContacts().getPhone());
		Assert.assertEquals(updatedSpokenLanguages, updated.getContacts().getSpokenLanguages());
	}

}
