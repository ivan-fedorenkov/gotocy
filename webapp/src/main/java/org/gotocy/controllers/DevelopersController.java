package org.gotocy.controllers;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Developer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class DevelopersController {

	@RequestMapping(value = "/developers/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Developer developer, Model model, Locale locale) {
		developer.initLocalizedFields(locale);
		model.addAttribute(developer);
		return "developer/show";
	}

}
