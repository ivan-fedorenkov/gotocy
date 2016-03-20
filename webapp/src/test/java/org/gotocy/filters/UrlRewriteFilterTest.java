package org.gotocy.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ifedorenkov
 */
public class UrlRewriteFilterTest {

	private UrlRewriteFilter filter;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockFilterChain filterChain;

	@Before
	public void setUp() {
		filter = new UrlRewriteFilter();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		filterChain = new MockFilterChain();
	}

	@Test
	public void testJsessionidRemoval() throws Exception {
		request.setRequestURI("/anything;jsessionid=123456?one=1&two=2#anchor");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/anything?one=1&two=2#anchor"), response.getHeader(HttpHeaders.LOCATION));
	}

	private String toUrl(String path) {
		return request.getScheme() + "://" + request.getServerName() + path;
	}

}
