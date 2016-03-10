package org.gotocy.interceptors;

import org.gotocy.service.AssetsManager;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.helpers.Helper;
import org.gotocy.i18n.I18n;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Exposes the {@link org.gotocy.helpers.Helper} object into views under the "helper" key.
 * Exposes the {@link ApplicationProperties} properties into views under the "gotocy" key.
 * Exposes the {@link I18n} object into view under the "i18n" key.
 *
 * @author ifedorenkov
 */
public class HelpersInterceptor implements HandlerInterceptor {
	private final Helper helper;
	private final ApplicationProperties applicationProperties;
	private final I18n i18n;

	public HelpersInterceptor(ApplicationProperties applicationProperties, I18n i18n, Helper helper) {
		this.applicationProperties = applicationProperties;
		this.i18n = i18n;
		this.helper = helper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("helper", helper);
			modelAndView.addObject("i18n", i18n);
			modelAndView.addObject("gotocy", applicationProperties);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
