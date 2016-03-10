package org.gotocy.controllers;

import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import static org.gotocy.repository.PropertyPredicates.publiclyVisible;

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

	private final PropertyRepository repository;

	@Autowired
	public HomeController(PropertyRepository repository) {
		this.repository = repository;
	}

	/**
	 * TODO: dirty hack, still it works fine for now; later this logic would be substituted by property groups and offers
	 */
	@RequestMapping("/")
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
	}

}
