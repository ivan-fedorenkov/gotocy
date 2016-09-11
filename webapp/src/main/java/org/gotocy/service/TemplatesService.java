package org.gotocy.service;

import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Map;

/**
 * @author ifedorenkov
 */
public interface TemplatesService {

	/**
	 * Builds a template from the given page.
	 */
	String processTemplateIntoString(LocalizedPage templatePage, Map<String, Object> model);

}
