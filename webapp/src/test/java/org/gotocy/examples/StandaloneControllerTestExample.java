package org.gotocy.examples;

import org.gotocy.controllers.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author ifedorenkov
 */
public class StandaloneControllerTestExample {

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new ControllerUnderTest()).build();
	}

	@Test
	public void testIt() throws Exception {
		mockMvc.perform(get("/")).andExpect(content().string("Hello World!"));
	}

	@RestController
	public static class ControllerUnderTest {
		@RequestMapping("/")
		public String get() {
			return "Hello world";
		}
	}

}