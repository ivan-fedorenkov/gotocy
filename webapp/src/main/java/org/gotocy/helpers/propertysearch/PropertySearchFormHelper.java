package org.gotocy.helpers.propertysearch;

import org.gotocy.format.seo.SeoPropertySearchFormUriFormatter;
import org.gotocy.forms.PropertiesSearchForm;
import org.springframework.web.util.UriComponentsBuilder;

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
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/" + FORMATTER.print(form, locale));
		if (form.isPriceChanged())
			uriBuilder.queryParam("price", form.getPriceFrom() + ";" + form.getPriceTo());
		return uriBuilder.toUriString();
	}

}
