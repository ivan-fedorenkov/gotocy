package org.gotocy.integration.master;

import org.gotocy.config.Roles;
import org.gotocy.domain.Complex;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.integration.config.WithGtcUser;
import org.gotocy.test.factory.ComplexFactory;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */

public class ComplexIntegrationTest extends IntegrationTestBase {

	@Test
	@WithGtcUser(roles = Roles.MASTER)
	public void complexCreation() throws Exception {
		Complex complex = ComplexFactory.INSTANCE.get();

		mvc.perform(post("/master/complexes").with(csrf())
			.param("title", complex.getTitle())
			.param("address", complex.getAddress())
			.param("coordinates", complex.getCoordinates()))
			.andExpect(status().is3xxRedirection());
	}

}
