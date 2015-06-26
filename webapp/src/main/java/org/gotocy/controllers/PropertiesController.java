package org.gotocy.controllers;

import org.gotocy.domain.*;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.LocalizedPropertySpecificationRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.ArrayList;
import java.util.List;
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

	@Autowired
	private LocalizedPropertySpecificationRepository localizedPropertySpecificationRepository;

	@RequestMapping("/properties/add")
	@ResponseBody
	public LocalizedProperty addProperty(@RequestParam(required = false) Long id,
		@RequestParam(required = false, defaultValue = "LARNACA") Location location,
		@RequestParam(required = false, defaultValue = "34.7071301") Double latitude,
		@RequestParam(required = false, defaultValue = "33.022617399999945") Double longitude,
		@RequestParam(required = false, defaultValue = "100") Integer price,
		Locale locale)
	{
		Property p;
		if (id == null) {
			p = new Property();
			p.setPrice(price);
			p.setLocation(location);
			p.setLatitude(latitude);
			p.setLongitude(longitude);
			p.setPropertyType(PropertyType.DETACHED_HOUSE);
			p.setPropertyStatus(PropertyStatus.LONG_TERM);
			propertyRepository.save(p);
		} else {
			p = propertyRepository.findOne(id);
		}

		LocalizedProperty lp = new LocalizedProperty();
		lp.setLocale(locale.getLanguage());
		lp.setTitle("LP#" + p.getId());
		lp.setAddress("Property address");
		lp.setDescription("Some description");
		lp.setProperty(p);

		List<LocalizedPropertySpecification> specifications = new ArrayList<>();
		LocalizedPropertySpecification lps = new LocalizedPropertySpecification();
		lps.setSpecification("spec #1");
		specifications.add(lps);
		lp.setSpecifications(specifications);

		localizedPropertyRepository.save(lp);

		return lp;
	}

	@RequestMapping("/properties")
	@ResponseBody
	public Iterable<LocalizedProperty> list() {
		return localizedPropertyRepository.findAll();
	}

	@RequestMapping("/property/{id}")
	public String get(Model model, @PathVariable("id") Long id, Locale locale) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty lp = localizedPropertyRepository.findByPropertyIdAndLocale(id, locale.getLanguage());
		// TODO: replace with custom exception
		if (lp == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);
		lp.setSpecifications(localizedPropertySpecificationRepository.findByLocalizedProperty(lp));
		model.addAttribute(lp);
		return "property/show";
	}

}
