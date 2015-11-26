package org.gotocy.controllers;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Complex;
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

	@RequestMapping(value = "/complexes/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Complex complex, Model model, Locale locale) {
		complex.initLocalizedFields(locale);
		model.addAttribute(complex);
		return "complex/show";
	}

}
