package org.gotocy.controllers;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.*;
import org.gotocy.forms.RegistrationForm;
import org.gotocy.forms.validation.RegistrationFormValidator;
import org.gotocy.forms.validation.UserPropertyFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping("/promo")
public class PromoController {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private RegistrationRepository registrationRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && RegistrationFormValidator.INSTANCE.supports(binder.getTarget().getClass()))
			binder.addValidators(RegistrationFormValidator.INSTANCE);
	}

	/**
	 * TODO: unit test
	 */
	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String show(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		if (property.getOfferStatus() != OfferStatus.PROMO)
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute(new RegistrationForm());
		model.addAttribute("relatedRegistration", registrationRepository.findByRelatedProperty(property));

		return "promo/property";
	}

	/**
	 * TODO: integration test
	 */
	@Transactional
	@RequestMapping(value = "/property/{id}/registration", method = RequestMethod.POST)
	public String createRegistration(@RequiredDomainObject @PathVariable("id") Property property,
		@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult errors,
		Model model, Locale locale)
	{
		if (property.getOfferStatus() != OfferStatus.PROMO)
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);

		if (errors.hasErrors())
			return "promo/property";

		Registration registration = registrationForm.mergeWithRegistration(new Registration());
		registration.setRelatedProperty(property);
		registrationRepository.save(registration);

		return "redirect:" + Helper.path(property);
	}

	@RequestMapping(value = "/index-1", method = RequestMethod.GET)
	public String getIndex1(Model model, Locale locale,
		@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		model.addAttribute("longTermProperties", propertyRepository.findByPropertyStatus(PropertyStatus.LONG_TERM, pageable));
		model.addAttribute("shortTermProperties", propertyRepository.findByPropertyStatus(PropertyStatus.SHORT_TERM, pageable));
		model.addAttribute("saleProperties", propertyRepository.findByPropertyStatus(PropertyStatus.SALE, pageable));

		// List of featured properties (commercial)
		List<Property> featured = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			Property p = createPromo();
			p.setRepresentativeImage(p.getImages().get(i));
			featured.add(p);
		}
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

		Contact contact = new Contact();
		contact.setName("Denis");
		contact.setPhone("+357 96 740485");
		contact.setEmail("support@gotocy.eu");
		contact.setSpokenLanguages("Eng, Rus");
		p.setPrimaryContact(contact);

		return p;
	}

}
