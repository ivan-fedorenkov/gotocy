package org.gotocy.controllers.user;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.controllers.exceptions.AccessDeniedException;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Property;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class UserPropertyImagesController {

	@RequestMapping(value = "/user/properties/{id}/images/edit", method = RequestMethod.GET)
	public String edit(Model model, @RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, Locale locale)
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		return "user/property/image/edit";
	}

}
