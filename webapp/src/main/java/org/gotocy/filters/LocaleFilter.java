package org.gotocy.filters;

import org.gotocy.config.Locales;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The filter sets the language session attribute value based on the request path.
 *
 * Examples and supported languages:
 * get http://xyz/resource - english language (default)
 * get http://xyz/ru/resource - russian language
 * get http://xyz/el/resource - greek language
 *
 * Determined the language, the filter forwards the request to uri substituting the language path variable.
 * get http://xyz/en/resource forwards to http://xyz/resource
 *
 * @author ifedorenkov
 */
public class LocaleFilter extends OncePerRequestFilter {

	private static final Map<String, Locale> PATH_TO_LOCALE = new HashMap<>();

	static {
		PATH_TO_LOCALE.put("/ru", Locales.RU);
		PATH_TO_LOCALE.put("/el", Locales.EL);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException
	{
		UrlPathHelper pathHelper = new UrlPathHelper();

		String uri = pathHelper.getRequestUri(request).substring(
				pathHelper.getContextPath(request).length());

		for (Map.Entry<String, Locale> locale : PATH_TO_LOCALE.entrySet()) {
			if (uri.startsWith(locale.getKey())) {
				// Language determined
				WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					locale.getValue());

				// Remove language code from the uri and forward the request
				RequestDispatcher dispatcher = request.getRequestDispatcher(uri.substring(locale.getKey().length()));
				dispatcher.forward(request, response);
				return;
			}
		}

		// Language code is not present in the uri, use the default locale
		WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locales.DEFAULT);
		filterChain.doFilter(request, response);
	}

}
