package org.gotocy.controllers;

import org.gotocy.domain.LocalizedProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ifedorenkov
 */
@RestController
public class PropertiesController {

	@RequestMapping("/properties/add")
	public LocalizedProperty addProperty() {
		throw new UnsupportedOperationException();
	}

	@RequestMapping("/properties")
	public List<LocalizedProperty> list() {
		throw new UnsupportedOperationException();
	}

	@RequestMapping("/property/{id}")
	public LocalizedProperty get(@PathVariable("id") LocalizedProperty localizedProperty) {
		throw new UnsupportedOperationException();
	}

}
