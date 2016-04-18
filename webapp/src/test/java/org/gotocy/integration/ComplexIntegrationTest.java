package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Profiles;
import org.gotocy.config.SecurityProperties;
import org.gotocy.domain.Complex;
import org.gotocy.test.factory.ComplexFactory;
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
		Complex complex = ComplexFactory.INSTANCE.get();

		mockMvc.perform(post("/master/complexes")
			.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
			.param("title", complex.getTitle())
			.param("address", complex.getAddress())
			.param("coordinates", complex.getCoordinates()))
			.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
