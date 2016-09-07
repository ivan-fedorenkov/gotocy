package org.gotocy.controllers.user;

import org.gotocy.config.Paths;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PropertyPredicates;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static org.gotocy.repository.PropertyPredicates.ofUser;
import static org.gotocy.repository.PropertyPredicates.withOfferInStatus;

/**
 * @author ifedorenkov
 */
@Controller
public class UserWelcomeController {

	private final PropertyService propertyService;

	@Autowired
	public UserWelcomeController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@RequestMapping(value = "/user/welcome", method = RequestMethod.GET)
	public String show() {
		return "user/welcome/show";
	}

	@RequestMapping(value = "user/welcome/redirect-to-first-inactive-offer", method = RequestMethod.GET)
	public String editFirstInactiveOffer(@AuthenticationPrincipal GtcUser user) {
		// Try to find any property with an offer in the Inactive state and redirect to its offers edit form
		Property property = propertyService.findOne(ofUser(user).and(withOfferInStatus(OfferStatus.INACTIVE)));
		if (property != null)
			return "redirect:" + Helper.editPath(Paths.USER, property, Paths.OFFERS);

		// Try to obtain last created publicly visible property and redirect to its offers edit form
		Page<Property> properties = propertyService.findPubliclyVisible(ofUser(user),
			new PageRequest(0, 1, Sort.Direction.DESC, "creationDate"));
		Optional<Property> lastCreatedProperty = StreamSupport.stream(properties.spliterator(), false)
			.max(comparingLong(p -> p.getCreationDate().toEpochDay()));
		if (lastCreatedProperty.isPresent())
			return "redirect:" + Helper.editPath(Paths.USER, lastCreatedProperty.get(), Paths.OFFERS);

		// There is no single property that belongs to current user so redirect to property creation form
		return "redirect:" + Helper.newPath(Paths.USER, Paths.PROPERTIES);
	}

}
