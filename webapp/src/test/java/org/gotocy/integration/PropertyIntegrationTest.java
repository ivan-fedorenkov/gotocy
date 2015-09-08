package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.SecurityProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
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
@WebIntegrationTest(randomPort = true, value = {
	"gotocy.s3.secretKey=test",
	"gotocy.s3.accessKey=test",
	"gotocy.s3.bucket=test",
	"gotocy.webapp.profile=test",
	"gotocy.webapp.security.login=test",
	"gotocy.webapp.security.password=test",
	"debug"
})
@Transactional
public class PropertyIntegrationTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void propertyCreation() throws Exception {
		mockMvc.perform(post("/master/properties")
				.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
				.param("title", "Test title")
				.param("propertyType", "LAND")
				.param("propertyStatus", "SALE")
				.param("offerStatus", "ACTIVE")
				.param("fullAddress", "Test address")
				.param("shortAddress", "Test address")
				.param("price", "123")
				.param("latitude", "1")
				.param("longitude", "1")
				.param("ownerName", "Any owner")
				.param("bedrooms", "0")
				.param("readyToMoveIn", "FALSE")
				.param("airConditioner", "FALSE")
				.param("heatingSystem", "FALSE")
				.param("vatIncluded", "FALSE"))
			.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}


}
