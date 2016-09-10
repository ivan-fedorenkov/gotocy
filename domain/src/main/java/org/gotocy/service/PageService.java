package org.gotocy.service;

import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Locale;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public interface PageService {

	/**
	 * Tries to load a page with the given url key in the given locale.
	 *
	 * @param url of a page
	 * @param locale to be used for page localization
	 * @return loaded page in the given locale
	 */
	Optional<LocalizedPage> findByUrl(String url, Locale locale);

}
