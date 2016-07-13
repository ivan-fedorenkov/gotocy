package org.gotocy.controllers;

import org.gotocy.config.Locales;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.gotocy.helpers.Helper;
import org.gotocy.i18n.I18n;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class CommercialController {

	@RequestMapping(value = "/gmdeco4u-ad-block", method = RequestMethod.GET, produces = "text/html")
	@ResponseBody
	public String adBlock() {
		return "<div class=\"property small\">" +
			"<a th:href=\"" + Helper.path("/gmdeco4u") + "\">" +
			"<div class=\"property-image\">" +
			"<img alt=\"G.M. Deco\" src=\"http://assets.gotocy.com/commercial/gm_deco/logo_side.jpg\">" +
			"</div>" +
			"</a>" +
			"<div class=\"info\">" +
			"<a th:href=\"" + Helper.path("/gmdeco4u") + "\"><h4>G.M. Deco For You</h4></a>" +
			"<figure>" + I18n.t("templates.property.show.gm-deco-side-description") + "</figure>" +
			"</div>" +
			"</div>";
	}

	@RequestMapping(value = "/gmdeco4u", method = RequestMethod.GET)
	public String get(Locale locale) {
		if (!Locales.SUPPORTED.contains(locale))
			throw new NotFoundException();

		return "commercial/gm_deco_" + locale.getLanguage();
	}

}
