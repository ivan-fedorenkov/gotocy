package org.gotocy.filters;

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
 * @author ifedorenkov
 */
public class UrlRewriteFilter extends OncePerRequestFilter {

	private static final Pattern JSESSIONID_PATTERN =
		Pattern.compile("^(.*?)(?:\\;jsessionid=[^\\?#]*)(\\?[^#]*)?(#.*)?$");
	private static final Pattern HEROKU_DOMAIN_PATTERN =
		Pattern.compile("^(.*?)(?:gotocy\\.herokuapp\\.com)(.*)$");

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
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", valOrEmpty(jsessionidMatcher.group(1)) +
				valOrEmpty(jsessionidMatcher.group(2)) + valOrEmpty(jsessionidMatcher.group(3)));
			return;
		}

		Matcher herokuMatcher = HEROKU_DOMAIN_PATTERN.matcher(request.getRequestURL());

		if (herokuMatcher.matches()) {
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", valOrEmpty(herokuMatcher.group(1)) + "www.gotocy.eu" +
				valOrEmpty(herokuMatcher.group(2)));
			return;
		}

		filterChain.doFilter(request, response);
	}

	private static String valOrEmpty(String s) {
		return s == null ? "" : s;
	}
}
