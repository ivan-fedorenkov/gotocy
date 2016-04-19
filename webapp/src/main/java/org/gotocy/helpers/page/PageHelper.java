package org.gotocy.helpers.page;

import org.gotocy.config.Locales;
import org.gotocy.domain.Page;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class PageHelper {

	/**
	 * Returns page path in the given locale.
	 * Unit test: PageHelperTest#pathTest
	 */
	public static String path(Page page, Locale locale) {
		return "/" + page.getUrl(locale).orElse(page.getUrl(Locales.DEFAULT).orElse(""));
	}

}
