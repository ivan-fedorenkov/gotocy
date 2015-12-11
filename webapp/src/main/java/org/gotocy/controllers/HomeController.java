package org.gotocy.controllers;

import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import static org.gotocy.repository.PropertyPredicates.featured;
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
	public String home(Model model, Locale locale,
		@PageableDefault(size = 40, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Iterable<Property> properties = repository.findAll(publiclyVisible(), pageable);

		Set<Property> filteredLongTerms = new TreeSet<>(BY_COORDINATES_COMPARATOR);
		Set<Property> filteredShortTerms = new TreeSet<>(BY_COORDINATES_COMPARATOR);
		Set<Property> filteredSale = new TreeSet<>(BY_COORDINATES_COMPARATOR);

		for (Property property : properties) {
			Set<Property> filtered =
				property.getPropertyStatus() == PropertyStatus.LONG_TERM ? filteredLongTerms :
				property.getPropertyStatus() == PropertyStatus.SHORT_TERM ? filteredShortTerms : filteredSale;

			if (filtered.add(property)) {
				if (filteredLongTerms.size() == PROPERTIES_PER_CATEGORY &&
					filteredShortTerms.size() == PROPERTIES_PER_CATEGORY &&
					filteredSale.size() == PROPERTIES_PER_CATEGORY)
				{
					break;
				}
			}
		}

		model.addAttribute("longTermProperties", filteredLongTerms);
		model.addAttribute("shortTermProperties", filteredShortTerms);
		model.addAttribute("saleProperties", filteredSale);
		model.addAttribute("featuredProperties", repository.findAll(publiclyVisible().and(featured())));

		return "home/index";
	}

}
