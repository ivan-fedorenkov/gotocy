package org.gotocy.controllers;

import org.gotocy.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping("/promo")
public class PromoController {

	@RequestMapping(value = "/property-1", method = RequestMethod.GET)
	public String getProperty1(Model model) {

		// The Promo property
		model.addAttribute(createPromo());

		// List of featured properties (commercial)
		List<LocalizedProperty> featured = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			LocalizedProperty lp = createPromo();
			lp.getProperty().setRepresentativeImage(lp.getProperty().getImages().get(i));
			featured.add(lp);
		}
		model.addAttribute("featuredProperties", featured);

		return "promo/property_1";
	}

	@RequestMapping(value = "/property-2", method = RequestMethod.GET)
	public String getProperty2(Model model) {
		model.addAttribute(createPromo());
		return "promo/property_2";
	}

	private static LocalizedProperty createPromo() {
		Property p = new Property();
		p.setTitle("The Promo property");
		p.setAddress("Demetri Koumandari Str. No.1, 7103, Aradippou, Larnaca");
		p.setShortAddress("Demetri Koumandari Str.1, Larnaca");
		p.setLatitude(34.940275);
		p.setLongitude(33.590204);
		p.setPropertyStatus(PropertyStatus.LONG_TERM);
		p.setPropertyType(PropertyType.HOUSE);
		p.setOfferStatus(OfferStatus.RENTED);
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

		ImageSet imageSet = p.getImageSet();
		Image img;
		for (int i = 1; i < 5; i++) {
			img = new Image();
			img.setKey("property/promo/" + i + ".jpg");
			imageSet.getImages().add(img);
		}

		Owner owner = new Owner();
		owner.setName("Denis");
		owner.setPhone("+357 96 740485");
		owner.setEmail("support@gotocy.eu");
		owner.setSpokenLanguages("Eng, Rus");
		p.setOwner(owner);

		LocalizedProperty lp = new LocalizedProperty();
		lp.setLocale("en");
		lp.setProperty(p);

		return lp;
	}

}
