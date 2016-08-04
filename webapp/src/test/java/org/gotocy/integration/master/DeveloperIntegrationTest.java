package org.gotocy.integration.master;

import org.gotocy.config.Roles;
import org.gotocy.domain.Developer;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.integration.config.WithGtcUser;
import org.gotocy.test.factory.DeveloperFactory;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class DeveloperIntegrationTest extends IntegrationTestBase {

	@Test
	@WithGtcUser(roles = Roles.MASTER)
	public void complexCreation() throws Exception {
		Developer developer = DeveloperFactory.INSTANCE.get();

		mvc.perform(post("/master/developers").with(csrf())
			.param("name", developer.getName()))
			.andExpect(status().isOk());
	}

}
