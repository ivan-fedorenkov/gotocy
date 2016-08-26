package org.gotocy.controllers.user;

import org.gotocy.config.Paths;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.controllers.exceptions.AccessDeniedException;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Property;
import org.gotocy.forms.user.property.OffersForm;
import org.gotocy.helpers.Helper;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class UserPropertyOffersController {

	private final PropertyService propertyService;

	@Autowired
	public UserPropertyOffersController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@RequestMapping(value = "/user/properties/{id}/offers/edit", method = RequestMethod.GET)
	public String edit(Model model, @RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, Locale locale)
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute(new OffersForm(property));
		return "user/property/offer/edit";
	}

	@RequestMapping(value = "/user/properties/{id}/offers", method = RequestMethod.PUT)
	public String update(Model model, @Valid @ModelAttribute OffersForm form, BindingResult formErrors,
		@RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, Locale locale)
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		property.initLocalizedFields(locale);
		model.addAttribute(property);

		if(formErrors.hasErrors())
			return "user/property/offer/edit";

		propertyService.update(form.mergeWithProperty(property));
		return "redirect:" + Helper.path(Paths.USER, property);
	}

}
