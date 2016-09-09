package org.gotocy.helpers;

import org.gotocy.config.Locales;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
public class PageHelper {

	private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

	private final PageRepository pageRepository;

	public PageHelper(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	/**
	 * Fetchers and returns html content of a page with the given {@param pageUrl} in the given {@param locale}.
	 * Returns an empty string if the page could not be found.
	 * Unit test: PageHelperTest#getTest
	 */
	public String get(String pageUrl, Locale locale) {
		Page page = pageRepository.findByUrl(pageUrl);
		if (page == null) {
			logger.warn("Requested non-existent page {}", pageUrl);
			return "";
		} else {
			return page.localize(locale).getHtml();
		}
	}

	/**
	 * Returns page path in the given locale or {@link Optional#EMPTY} if there is none.
	 * Unit test: PageHelperTest#pathTest
	 */
	public static Optional<String> path(Page page, Locale locale) {
		return path(page.localize(locale));
	}

	/**
	 * Returns page path in the given locale or {@link Optional#EMPTY} if there is none.
	 * Page instance is accessed through the localized instance of a page (convenient helper).
	 * Unit test: PageHelperTest#pathTest
	 */
	public static Optional<String> path(LocalizedPage localizedPage, Locale locale) {
		return localizedPage.getLocale().equals(locale) ?
			path(localizedPage) : path(localizedPage.getOriginal(), locale);
	}

	/**
	 * Returns page path or {@link Optional#EMPTY} if there is none.
	 * Unit test: PageHelperTest#pathTest
	 */
	public static Optional<String> path(LocalizedPage localizedPage) {
		Optional<String> url = Optional.empty();
		if (localizedPage.isFullyTranslated()) {
			url = Optional.of("/" + localizedPage.getUrl());
		} else {
			logger.error("Requested url of non-translated page #{} in language {}",
				localizedPage.getOriginal().getId(), localizedPage.getLocale());
		}
		return url;
	}

	/**
	 * Returns a list of supported locales for the given page.
	 * Unit test: PageHelperTest#supportedLocalesTest
	 */
	public static List<Locale> supportedLocales(Page page) {
		return Locales.SUPPORTED.stream()
			.filter(locale -> page.localize(locale).isFullyTranslated())
			.collect(toList());
	}

}
