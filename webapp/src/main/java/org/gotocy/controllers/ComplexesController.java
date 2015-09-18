package org.gotocy.controllers;

import org.gotocy.domain.Complex;
import org.gotocy.repository.ComplexRepository;
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
public class ComplexesController {

	@Autowired
	private ComplexRepository complexRepository;

	@RequestMapping(value = "/complex/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable("id") Complex complex, Locale locale) {
		complex.initLocalizedFields(locale);
		model.addAttribute(complex);
		return "complex/show";
	}

}
