package org.gotocy.controllers;

import com.mysema.query.SearchResults;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.persistance.LocalizedPropertyDao;
import org.gotocy.persistance.PropertyDao;
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
	private PropertyDao propertyDao;

	@Autowired
	private LocalizedPropertyDao localizedPropertyDao;


	@RequestMapping("/properties/add")
	public LocalizedProperty addProperty(@RequestParam(required = false) Long id, Locale locale) {
		Property p;
		if (id == null) {
			p = new Property();
			propertyDao.save(p);
		} else {
			p = propertyDao.findOne(id);
		}

		LocalizedProperty lp = new LocalizedProperty();
		lp.setTitle("LP#" + p.getId());
		lp.setLocale(locale.getLanguage());
		lp.setProperty(p);
		localizedPropertyDao.save(lp);

		return lp;
	}

	@RequestMapping("/properties")
	public SearchResults<LocalizedProperty> list() {
		return localizedPropertyDao.findAll();
	}

	@RequestMapping("/property/{id}")
	public LocalizedProperty get(@PathVariable("id") Long id) {
		return localizedPropertyDao.findOne(id);
	}

}
