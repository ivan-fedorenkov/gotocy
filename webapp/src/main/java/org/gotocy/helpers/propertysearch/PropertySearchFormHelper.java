package org.gotocy.helpers.propertysearch;

import org.gotocy.format.seo.SeoPropertySearchFormUriFormatter;
import org.gotocy.forms.PropertiesSearchForm;

import java.util.Locale;

/**
 * A number of property search form related helper methods.
 *
 * @author ifedorenkov
 */
public class PropertySearchFormHelper {

	private static final SeoPropertySearchFormUriFormatter FORMATTER = new SeoPropertySearchFormUriFormatter();

	/**
	 * Returns the appropriate path for the given {@link PropertiesSearchForm} in the given {@link Locale}.
	 * TODO: unit test
	 */
	public static String path(PropertiesSearchForm form, Locale locale) {
		return "/" + FORMATTER.print(form, locale);
	}

}
