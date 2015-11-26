package org.gotocy.filters;


import org.gotocy.config.Locales;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;

import static org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

/**
 * @author ifedorenkov
 */
public class LocaleFilterTest {

	private static final String TEST_URI = "/controller-under-test";

	private static ServletContext testServletContext;
	private static LocaleFilter filter;

	private FilterChain filterChain;

	@BeforeClass
	public static void initTestEnvironment() {
		testServletContext = new MockServletContext();
		filter = new LocaleFilter();
	}

	@Before
	public void initFilterChain() {
		filterChain = new MockFilterChain();
	}

	@Test
	public void testDefaultLocale() throws Exception {
		MockHttpServletRequest request = MockMvcRequestBuilders.get(TEST_URI).buildRequest(testServletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();

		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(Locales.DEFAULT, request.getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME));
	}

	@Test
	public void testRussianLocale() throws Exception {
		MockHttpServletRequest request = MockMvcRequestBuilders.get("/ru" + TEST_URI).buildRequest(testServletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();

		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(Locales.RU, request.getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME));
		Assert.assertEquals(TEST_URI, response.getForwardedUrl());
	}

	@Test
	public void testLocaleSwitch() throws Exception {
		MockHttpServletRequest request = MockMvcRequestBuilders.get("/ru" + TEST_URI).buildRequest(testServletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();

		filter.doFilter(request, response, filterChain);
		Assert.assertEquals(Locales.RU, request.getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME));

		request = MockMvcRequestBuilders.get(TEST_URI).buildRequest(testServletContext);
		response = new MockHttpServletResponse();

		filter.doFilter(request, response, filterChain);
		Assert.assertEquals(Locales.DEFAULT, request.getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME));
	}

}
