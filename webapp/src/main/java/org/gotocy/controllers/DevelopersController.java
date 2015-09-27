package org.gotocy.controllers;

import org.gotocy.domain.Developer;
import org.gotocy.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private DeveloperRepository developerRepository;

	@RequestMapping(value = "/developer/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable("id") Developer developer, Locale locale) {
		developer.initLocalizedFields(locale);
		model.addAttribute(developer);
		return "developer/show";
	}

}
