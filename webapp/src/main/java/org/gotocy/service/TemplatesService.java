package org.gotocy.service;

import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.Localized;
import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public interface TemplatesService {

	/**
	 * TODO: javadoc, unit test
	 */
	LocalizedPage getProcessedTemplate(String templatePageUrl, Map<String, Object> model, Locale locale);

}
