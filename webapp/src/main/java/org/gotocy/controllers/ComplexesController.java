package org.gotocy.controllers;

import org.gotocy.domain.Complex;
import org.gotocy.forms.ComplexForm;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.ComplexRepository;
import org.gotocy.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class ComplexesController {

	@Autowired
	private ComplexRepository complexRepository;
	@Autowired
	private OwnerRepository ownerRepository;

	@RequestMapping(value = "/complex/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable("id") Complex complex, Locale locale) {
		complex.initLocalizedFields(locale);
		model.addAttribute(complex);
		return "complex/show";
	}

	@RequestMapping(value = "/master/complex/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("contacts", ownerRepository.findAll());
		model.addAttribute(new ComplexForm());
		return "master/complex/new";
	}

	@RequestMapping(value = "/master/complex/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") Complex complex) {
		model.addAttribute(complex);
		model.addAttribute("contacts", ownerRepository.findAll());
		model.addAttribute(new ComplexForm(complex));
		return "master/complex/edit";
	}

}
