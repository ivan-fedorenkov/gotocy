package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Profiles;
import org.gotocy.config.SecurityProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles(Profiles.TEST)
@Transactional
public class ComplexIntegrationTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void complexCreation() throws Exception {
		mockMvc.perform(post("/master/complex")
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", "Test title")
				.param("address", "Test address")
				.param("coordinates", "anything")
				.param("contactName", "Any contact"))
			.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
