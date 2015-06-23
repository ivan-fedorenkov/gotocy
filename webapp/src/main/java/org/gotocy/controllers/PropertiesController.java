package org.gotocy.controllers;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@RestController
public class PropertiesController {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private LocalizedPropertyRepository localizedPropertyRepository;


	@RequestMapping("/properties/add")
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
	public Iterable<LocalizedProperty> list() {
		return localizedPropertyRepository.findAll();
	}

	@RequestMapping("/property/{id}")
	public LocalizedProperty get(@PathVariable("id") LocalizedProperty localizedProperty) {
		return localizedProperty;
	}

}
