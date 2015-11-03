package org.gotocy;

import org.gotocy.config.Locales;
import org.gotocy.filters.LocaleFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

/**
 * @author ifedorenkov
 */
public class LocaleResolutionTest {

	private static final String TEST_URI = "/controller-under-test";
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ControllerUnderTest()).addFilters(new LocaleFilter()).build();
	}

	@Test
	public void testDefaultLocale() throws Exception {
		mockMvc.perform(get(TEST_URI))
			.andExpect(status().isOk())
			.andExpect(request().sessionAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, Locales.DEFAULT));
	}

	@Test
	public void testRussianLocale() throws Exception {
		mockMvc.perform(get("/ru" + TEST_URI))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(TEST_URI))
			.andExpect(request().sessionAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, Locales.RU));
	}

	@Test
	public void testLocaleSwitch() throws Exception {
		mockMvc.perform(get(TEST_URI)).andExpect(status().isOk());

		mockMvc.perform(get("/ru" + TEST_URI))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(TEST_URI))
			.andExpect(request().sessionAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, Locales.RU));

		mockMvc.perform(get(TEST_URI))
			.andExpect(status().isOk())
			.andExpect(request().sessionAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, Locales.DEFAULT));
	}


	@RestController
	public static class ControllerUnderTest {

		@SuppressWarnings("unused")
		@RequestMapping(TEST_URI)
		public String get() {
			return "nothing";
		}

	}

}
