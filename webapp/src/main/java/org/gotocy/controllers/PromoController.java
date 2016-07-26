package org.gotocy.controllers;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.*;
import org.gotocy.forms.UserRegistrationForm;
import org.gotocy.forms.validation.RegistrationFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.repository.RegistrationRepository;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

import static org.gotocy.repository.PropertyPredicates.publiclyVisible;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping("/promo")
public class PromoController {

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
	private final PropertyRepository propertyRepository;
	private final RegistrationRepository registrationRepository;

	@Autowired
	public PromoController(PropertyService propertyService, PropertyRepository propertyRepository,
		RegistrationRepository registrationRepository)
	{
		this.propertyService = propertyService;
		this.propertyRepository = propertyRepository;
		this.registrationRepository = registrationRepository;
	}

	@InitBinder("registrationForm")
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && RegistrationFormValidator.INSTANCE.supports(binder.getTarget().getClass()))
			binder.addValidators(RegistrationFormValidator.INSTANCE);
	}

	/**
	 * TODO: unit test
	 */
	@RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
	public String show(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		if (!property.isPromo())
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute(new UserRegistrationForm());

		return "promo/property";
	}

	@RequestMapping(value = "/index-1", method = RequestMethod.GET)
	public String getIndex1(Model model, Locale locale,
		@SortDefault(sort = "id", direction = Sort.Direction.DESC) Sort sort)
	{
		// Yes, fetch all the available publicly visible properties from the database
		Iterable<Property> properties = propertyRepository.findAll(publiclyVisible(), sort);

		// Properties by status
		Map<PropertyStatus, Set<Property>> propertiesByStatus = new EnumMap<>(PropertyStatus.class);
		for (PropertyStatus status : PropertyStatus.values())
			propertiesByStatus.put(status, new TreeSet<>(BY_COORDINATES_COMPARATOR));

		// Fill the sets of properties, each must contain no more then PROPERTIES_PER_CATEGORY elements
		for (Property property : properties) {
			Set<Property> filtered = propertiesByStatus.get(property.getPropertyStatus());
			if (filtered.size() < PROPERTIES_PER_CATEGORY)
				filtered.add(property);
		}

		// List of featured properties (commercial)
		List<Property> featured = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			Property p = createPromo();
			p.setRepresentativeImage(p.getImages().get(i));
			featured.add(p);
		}

		model.addAttribute("longTermProperties", propertiesByStatus.get(PropertyStatus.LONG_TERM));
		model.addAttribute("shortTermProperties", propertiesByStatus.get(PropertyStatus.SHORT_TERM));
		model.addAttribute("saleProperties", propertiesByStatus.get(PropertyStatus.SALE));
		model.addAttribute("featuredProperties", featured);

		return "promo/index_1";
	}

	@RequestMapping(value = "/property-1", method = RequestMethod.GET)
	public String getProperty1(Model model) {

		// The Promo property
		model.addAttribute(createPromo());

		// List of featured properties (commercial)
		List<Property> featured = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			Property p = createPromo();
			p.setRepresentativeImage(p.getImages().get(i));
			featured.add(p);
		}
		model.addAttribute("featuredProperties", featured);

		return "promo/property_1";
	}

	@RequestMapping(value = "/property-2", method = RequestMethod.GET)
	public String getProperty2(Model model) {
		model.addAttribute(createPromo());
		return "promo/property_2";
	}

	private static Property createPromo() {
		Property p = new Property();
		p.setTitle("The Promo property");
		p.setAddress("Demetri Koumandari Str. No.1, 7103, Aradippou, Larnaca");
		p.setShortAddress("Demetri Koumandari Str.1, Larnaca");
		p.setLocation(Location.LARNACA);
		p.setLatitude(34.940275);
		p.setLongitude(33.590204);
		p.setPropertyStatus(PropertyStatus.LONG_TERM);
		p.setPropertyType(PropertyType.HOUSE);
		p.setOfferStatus(OfferStatus.PROMO);
		p.setReadyToMoveIn(Boolean.TRUE);
		p.setPrice(850);
		p.setGuests(10);
		p.setBedrooms(4);
		p.setDistanceToSea(4700);
		p.setFurnishing(Furnishing.FULL);
		p.setAirConditioner(Boolean.TRUE);
		p.setHeatingSystem(Boolean.TRUE);
		p.setReadyToMoveIn(Boolean.TRUE);

		Image representative = new Image();
		representative.setKey("property/promo/representative.jpg");
		p.setRepresentativeImage(representative);

		Image img;
		for (int i = 1; i < 5; i++) {
			img = new Image();
			img.setKey("property/promo/" + i + ".jpg");
			p.getImages().add(img);
		}

		Contacts propertyContacts = new Contacts();
		propertyContacts.setName("Denis");
		propertyContacts.setEmail("support@gotocy.com");
		propertyContacts.setSpokenLanguages("Eng, Rus");
		p.setContactsDisplayOption(PropertyContactsDisplayOption.OVERRIDDEN);
		p.setOverriddenContacts(propertyContacts);

		return p;
	}

}
