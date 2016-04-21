package org.gotocy.interceptors;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor that populates request with various page fragments that forms the final layout.
 *
 * Exposes the {@link Page} footer page fragment into view under the "footerPage" key.
 *
 * @author ifedorenkov
 */
public class CmsInterceptor implements HandlerInterceptor {

	public static final String FOOTER_PAGE_REQUEST_ATTR = "footerPage";

	private final ApplicationProperties applicationProperties;
	private final PageRepository pageRepository;

	public CmsInterceptor(ApplicationProperties applicationProperties, PageRepository pageRepository) {
		this.applicationProperties = applicationProperties;
		this.pageRepository = pageRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception
	{
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception
	{
		if (modelAndView != null) {
			Page footerPage = pageRepository.findByUrl(applicationProperties.getFooterPageUrl());
			if (footerPage != null) {
				LocalizedPage localizedFooterPage = footerPage.localize(LocaleContextHolder.getLocale());
				if (localizedFooterPage.isFullyTranslated()) {
					modelAndView.addObject(FOOTER_PAGE_REQUEST_ATTR, localizedFooterPage);
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception
	{

	}
}
