package org.gotocy.interceptors;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.helpers.Helper;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Exposes the {@link org.gotocy.helpers.Helper} object into views under the "helper" key.
 * Exposes the {@link ApplicationProperties} properties into views under the "gotocy" key.
 *
 * @author ifedorenkov
 */
public class HelpersInterceptor implements HandlerInterceptor {
	private final Helper helper;
	private final ApplicationProperties applicationProperties;

	public HelpersInterceptor(ApplicationProperties applicationProperties, Helper helper) {
		this.helper = helper;
		this.applicationProperties = applicationProperties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("helper", helper);
			modelAndView.addObject("gotocy", applicationProperties);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
