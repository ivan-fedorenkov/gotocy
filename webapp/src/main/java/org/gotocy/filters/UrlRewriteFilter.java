package org.gotocy.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs various redirects. Based on internal testing it works much faster then existing implementations.
 * Unit tests: UrlRewriteFilterTest
 *
 * @author ifedorenkov
 */
public class UrlRewriteFilter extends OncePerRequestFilter {

	private static final Pattern JSESSIONID_PATTERN =
		Pattern.compile("^(.*?)(?:\\;jsessionid=[^\\?#]*)(\\?[^#]*)?(#.*)?$");
	private static final Pattern HEROKU_DOMAIN_PATTERN =
		Pattern.compile("^(.*?)(?:gotocy\\.herokuapp\\.com)(.*)$");

	// TODO: remove these patterns after 01.02.2016 (3 months period for web crawlers)
	private static final Pattern PROPERTY_PATTERN = Pattern.compile("^(.*?)(?:/property)(/.*)?$");
	private static final Pattern COMPLEX_PATTERN = Pattern.compile("^(.*?)(?:/complex)(/.*)?$");
	private static final Pattern DEVELOPERS_PATTERN = Pattern.compile("^(.*?)(?:/developer)(/.*)?$");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException
	{
		Matcher jsessionidMatcher = JSESSIONID_PATTERN.matcher(request.getRequestURL());

		if (jsessionidMatcher.matches()) {
			// Invalidate session is someone has passed the jsessionid (just in case - security)
			HttpSession session = request.getSession(false);
			if (session != null)
				session.invalidate();
			applyRedirect(response, valOrEmpty(jsessionidMatcher.group(1)) +
				valOrEmpty(jsessionidMatcher.group(2)) + valOrEmpty(jsessionidMatcher.group(3)));
			return;
		}

		Matcher herokuMatcher = HEROKU_DOMAIN_PATTERN.matcher(request.getRequestURL());

		if (herokuMatcher.matches()) {
			applyRedirect(response, valOrEmpty(herokuMatcher.group(1)) + "www.gotocy.eu" +
				valOrEmpty(herokuMatcher.group(2)));
			return;
		}

		Matcher propertyMatcher = PROPERTY_PATTERN.matcher(request.getRequestURL());

		if (propertyMatcher.matches()) {
			applyRedirect(response, valOrEmpty(propertyMatcher.group(1)) + "/properties" +
				valOrEmpty(propertyMatcher.group(2)));
			return;
		}

		Matcher complexMatcher = COMPLEX_PATTERN.matcher(request.getRequestURL());

		if (complexMatcher.matches()) {
			applyRedirect(response, valOrEmpty(complexMatcher.group(1)) + "/complexes" +
				valOrEmpty(complexMatcher.group(2)));
			return;
		}

		Matcher developerMatcher = DEVELOPERS_PATTERN.matcher(request.getRequestURL());

		if (developerMatcher.matches()) {
			applyRedirect(response, valOrEmpty(developerMatcher.group(1)) + "/developers" +
				valOrEmpty(developerMatcher.group(2)));
			return;
		}

		filterChain.doFilter(request, response);
	}

	private static void applyRedirect(HttpServletResponse response, String location) {
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader(HttpHeaders.LOCATION, location);
	}

	private static String valOrEmpty(String s) {
		return s == null ? "" : s;
	}
}
