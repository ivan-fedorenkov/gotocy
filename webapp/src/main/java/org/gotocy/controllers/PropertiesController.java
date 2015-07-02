package org.gotocy.controllers;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private LocalizedPropertyRepository repository;

	@RequestMapping("/properties")
	@ResponseBody
	public Iterable<LocalizedProperty> list() {
		return repository.findAll();
	}

	@RequestMapping("/property/{id}")
	public String get(Model model, @PathVariable("id") Long id, Locale locale) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty lp = repository.findProperty(id, locale.getLanguage());
		// TODO: replace with custom exception
		if (lp == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);
		model.addAttribute(lp);

		List<LocalizedProperty> similar = repository.findSimilar(lp.getProperty().getPropertyStatus(),
			lp.getProperty().getLocation(), locale.getLanguage(), new PageRequest(0, 4));

		// Remove self or last (if there was no self
		if (!similar.remove(lp))
			similar.remove(3);
		model.addAttribute("similarLocalizedProperties", similar);

		return "property/show";
	}

}
