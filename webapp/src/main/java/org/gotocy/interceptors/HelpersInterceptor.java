package org.gotocy.interceptors;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.helpers.Helper;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Exposes the {@link org.gotocy.helpers.Helper} object into views.
 *
 * @author ifedorenkov
 */
public class HelpersInterceptor implements HandlerInterceptor {
	private final Helper helper;

	public HelpersInterceptor(MessageSource messageSource, AssetsProvider assetsProvider) {
		helper = new Helper(messageSource, assetsProvider);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null)
			modelAndView.addObject("helper", helper);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
