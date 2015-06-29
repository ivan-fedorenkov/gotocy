package org.gotocy.interceptors;

import com.amazonaws.services.s3.AmazonS3Client;
import org.gotocy.beans.S3Configuration;
import org.gotocy.helpers.Helper;
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

	public HelpersInterceptor(AmazonS3Client s3client, S3Configuration s3config) {
		helper = new Helper(s3client, s3config);
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
