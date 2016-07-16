package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.Complex;
import org.gotocy.test.factory.ComplexFactory;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */

public class ComplexIntegrationTest extends IntegrationTestBase {

	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void complexCreation() throws Exception {
		Complex complex = ComplexFactory.INSTANCE.get();

		mockMvc.perform(post("/master/complexes").with(csrf())
			.param("title", complex.getTitle())
			.param("address", complex.getAddress())
			.param("coordinates", complex.getCoordinates()))
			.andExpect(status().isOk());
	}

}
