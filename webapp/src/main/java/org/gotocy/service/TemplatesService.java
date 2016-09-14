package org.gotocy.service;

import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Locale;
import java.util.Map;

/**
 * @author ifedorenkov
 */
public interface TemplatesService {

	/**
	 * Tries to load and process a template.
	 *
	 * @param templatePageUrl of the template page
	 * @param model to be used during template processing
	 * @param locale of the resulting processed template page
	 * @return dummy template page with 'missing' title and html, processed template page in the given locale or
	 * 		   processed template in the {@link org.gotocy.config.Locales#DEFAULT} if translation is missing
	 */
	LocalizedPage getProcessedTemplate(String templatePageUrl, Map<String, Object> model, Locale locale);

}
