package org.gotocy.controllers.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Property;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ifedorenkov
 */
@Controller
public class UserPropertiesController {

	private final PropertyService propertyService;

	@Autowired
	public UserPropertiesController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@RequestMapping("/user/properties")
	public String index(Model model, @AuthenticationPrincipal GtcUser user) {
		Iterable<Property> properties = propertyService.findByUser(user);
		model.addAttribute("properties", properties);
		return "user/property/index";
	}

}
