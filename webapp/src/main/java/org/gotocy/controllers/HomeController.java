package org.gotocy.controllers;

import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class HomeController {

	private static final int PROPERTIES_PER_CATEGORY = 4;

	private static final Comparator<Property> BY_COORDINATES_COMPARATOR = (o1, o2) -> {
		if (o1.getLatitude() == o2.getLatitude() && o1.getLongitude() == o2.getLongitude())
			return 0;

		if (o1.getLatitude() == o2.getLatitude()) {
			return Double.compare(o1.getLongitude(), o2.getLongitude());
		} else {
			return Double.compare(o1.getLatitude(), o2.getLatitude());
		}
	};

	private final PropertyService propertyService;

	@Autowired
	public HomeController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	/**
	 * TODO: dirty hack, still it works fine for now; later this logic would be substituted by property groups and offers
	 */
	/*@RequestMapping("/")
	public String home(Model model, Locale locale, @SortDefault(sort = "id", direction = Sort.Direction.DESC) Sort sort) {

		// Yes, fetch all the available publicly visible properties from the database
		Iterable<Property> properties = repository.findAll(publiclyVisible(), sort);

		// Properties by status
		Map<PropertyStatus, Set<Property>> propertiesByStatus = new EnumMap<>(PropertyStatus.class);
		for (PropertyStatus status : PropertyStatus.values())
			propertiesByStatus.put(status, new TreeSet<>(BY_COORDINATES_COMPARATOR));

		// Featured properties
		Set<Property> featured = new TreeSet<>(BY_COORDINATES_COMPARATOR);

		// Fill the sets of properties, each must contain no more then PROPERTIES_PER_CATEGORY elements
		for (Property property : properties) {
			if (featured.size() < PROPERTIES_PER_CATEGORY && property.isFeatured())
				featured.add(property);

			Set<Property> filtered = propertiesByStatus.get(property.getPropertyStatus());
			if (filtered.size() < PROPERTIES_PER_CATEGORY)
				filtered.add(property);
		}

		model.addAttribute("longTermProperties", propertiesByStatus.get(PropertyStatus.LONG_TERM));
		model.addAttribute("shortTermProperties", propertiesByStatus.get(PropertyStatus.SHORT_TERM));
		model.addAttribute("saleProperties", propertiesByStatus.get(PropertyStatus.SALE));
		model.addAttribute("featuredProperties", featured);

		model.addAttribute("propertiesSearchForm", new PropertiesSearchForm());

		return "home/index";
	}*/

	@RequestMapping("/")
	public String home(Model model, Locale locale,
		@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		model.addAttribute("featuredProperties", propertyService.getFeatured());
		model.addAttribute("longTermProperties", propertyService.findRecent(PropertyStatus.LONG_TERM, pageable));
		model.addAttribute("shortTermProperties", propertyService.findRecent(PropertyStatus.SHORT_TERM, pageable));
		model.addAttribute("saleProperties", propertyService.findRecent(PropertyStatus.SALE, pageable));
		model.addAttribute("propertiesSearchForm", new PropertiesSearchForm());

		PropertiesSearchForm shortTermPropertiesForm = new PropertiesSearchForm();
		shortTermPropertiesForm.setPropertyStatus(PropertyStatus.SHORT_TERM);
		model.addAttribute("shortTermPropertiesForm", shortTermPropertiesForm);

		PropertiesSearchForm longTermPropertiesForm = new PropertiesSearchForm();
		longTermPropertiesForm.setPropertyStatus(PropertyStatus.LONG_TERM);
		model.addAttribute("longTermPropertiesForm", longTermPropertiesForm);

		PropertiesSearchForm salePropertiesForm = new PropertiesSearchForm();
		salePropertiesForm.setPropertyStatus(PropertyStatus.SALE);
		model.addAttribute("salePropertiesForm", salePropertiesForm);

		return "home/index";
	}

}
