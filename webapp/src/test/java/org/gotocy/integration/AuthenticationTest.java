package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class AuthenticationTest extends IntegrationTestBase {

	private static final String SECURED_RESOURCE_URL = "/master/authentication-test";

	@Test
	public void deniedAccessToSecuredPath() throws Exception {
		mvc.perform(get(SECURED_RESOURCE_URL))
			.andExpect(redirectedUrlPattern("**/session/new"));
	}

	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void grantedAccessToSecuredPath() throws Exception {
		mvc.perform(get(SECURED_RESOURCE_URL))
			.andExpect(status().isOk());
	}

	@RestController
	public static class ControllerUnderTest {

		@RequestMapping(value = "/master/authentication-test", method = RequestMethod.GET)
		public String test() {
			return "sensitive information";
		}

	}

}
