package org.gotocy.interceptors;

import org.gotocy.config.SecurityProperties;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * TODO: integration test
 * TODO: set error code and redirect to 404 page
 *
 * @author ifedorenkov
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Boolean accessGranted = false;
		HttpSession session = request.getSession(false);

		if(null != session) {
			accessGranted = (Boolean) session.getAttribute(SecurityProperties.SESSION_KEY);

			if (accessGranted != Boolean.TRUE)
				response.sendRedirect("/");
		}



		return accessGranted != null && accessGranted;
	}

}
