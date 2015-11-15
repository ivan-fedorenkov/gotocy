package org.gotocy.controllers;

import org.gotocy.config.Locales;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class CommercialController {

	@RequestMapping(value = "/gmdeco4u", method = RequestMethod.GET)
	public String get(Locale locale) {
		if (!Locales.SUPPORTED.contains(locale))
			throw new NotFoundException();

		return "commercial/gm_deco_" + locale.getLanguage();
	}

}
