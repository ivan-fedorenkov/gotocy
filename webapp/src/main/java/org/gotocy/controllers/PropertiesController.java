package org.gotocy.controllers;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private LocalizedPropertyRepository localizedPropertyRepository;


	@RequestMapping("/properties/add")
	@ResponseBody
	public LocalizedProperty addProperty(@RequestParam(required = false) Long id, Locale locale) {
		Property p;
		if (id == null) {
			p = new Property();
			propertyRepository.save(p);
		} else {
			p = propertyRepository.findOne(id);
		}

		LocalizedProperty lp = new LocalizedProperty();
		lp.setTitle("LP#" + p.getId());
		lp.setLocale(locale.getLanguage());
		lp.setProperty(p);
		localizedPropertyRepository.save(lp);

		return lp;
	}

	@RequestMapping("/properties")
	@ResponseBody
	public Iterable<LocalizedProperty> list() {
		return localizedPropertyRepository.findAll();
	}

	@RequestMapping("/property/{id}")
	public String get(Model model, @PathVariable("id") LocalizedProperty localizedProperty) {
		model.addAttribute(localizedProperty);
		return "property/show";
	}

}
