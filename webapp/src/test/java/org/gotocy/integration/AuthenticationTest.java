package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Profiles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true, value = {
	"gotocy.webapp.security.login=master",
	"gotocy.webapp.security.password=master",
	"debug"
})
@ActiveProfiles(Profiles.TEST)
@Transactional
public class AuthenticationTest {

	private static final String SECURED_RESOURCE_URL = "/master/authentication-test";

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	public void deniedAccessToSecuredPath() throws Exception {
		mockMvc.perform(get(SECURED_RESOURCE_URL))
			.andExpect(redirectedUrlPattern("**/session/new"));
	}

	@Test
	public void grantedAccessToSecuredPath() throws Exception {
		mockMvc.perform(get(SECURED_RESOURCE_URL).with(user("master").roles("MASTER")))
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
