package org.gotocy.controllers;

import org.gotocy.domain.Developer;
import org.gotocy.forms.DeveloperForm;
import org.gotocy.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(value = "/master/developer/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute(new DeveloperForm());
		return "master/developer/new";
	}

	@RequestMapping(value = "/master/developer", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Developer create(DeveloperForm developerForm) {
		return developerRepository.saveAndFlush(developerForm.mergeWithDeveloper(new Developer()));
	}

	@RequestMapping(value = "/master/developer/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") Developer developer) {
		model.addAttribute(developer);
		model.addAttribute(new DeveloperForm(developer));
		return "master/developer/edit";
	}

	@RequestMapping(value = "/master/developer/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public Developer update(@PathVariable("id") Developer developer, DeveloperForm developerForm) {
		return developerRepository.saveAndFlush(developerForm.mergeWithDeveloper(developer));
	}

}
