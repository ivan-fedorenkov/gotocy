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

	@Test
	public void testHerokuDomainRedirect() throws Exception {
		request.setScheme("http");
		request.setServerName("gotocy.herokuapp.com");
		request.setRequestURI("/anything");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals("http://www.gotocy.eu/anything", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void testPropertyRedirect() throws Exception {
		request.setRequestURI("/property");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/properties"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/property/1");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/properties/1"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/master/property/1/edit");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/master/properties/1/edit"), response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void testComplexRedirect() throws Exception {
		request.setRequestURI("/complex");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/complexes"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/complex/1");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/complexes/1"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/master/complex/1/edit");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/master/complexes/1/edit"), response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void testDeveloperRedirect() throws Exception {
		request.setRequestURI("/developer");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/developers"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/developer/1");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/developers/1"), response.getHeader(HttpHeaders.LOCATION));

		request.setRequestURI("/master/developer/1/edit");
		filter.doFilter(request, response, filterChain);

		Assert.assertEquals(HttpServletResponse.SC_MOVED_PERMANENTLY, response.getStatus());
		Assert.assertEquals(toUrl("/master/developers/1/edit"), response.getHeader(HttpHeaders.LOCATION));
	}

	private String toUrl(String path) {
		return request.getScheme() + "://" + request.getServerName() + path;
	}

}
