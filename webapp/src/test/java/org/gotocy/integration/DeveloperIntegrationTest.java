package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.Developer;
import org.gotocy.test.factory.DeveloperFactory;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class DeveloperIntegrationTest extends IntegrationTestBase {

	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void complexCreation() throws Exception {
		Developer developer = DeveloperFactory.INSTANCE.get();

		mockMvc.perform(post("/master/developers").with(csrf())
			.param("name", developer.getName()))
			.andExpect(status().isOk());
	}

}
