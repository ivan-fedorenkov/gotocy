package org.gotocy.controllers.user;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.config.Paths;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.controllers.exceptions.AccessDeniedException;
import org.gotocy.domain.GtcUser;
import org.gotocy.domain.Image;
import org.gotocy.domain.Property;
import org.gotocy.forms.user.ImagesEditorForm;
import org.gotocy.forms.user.property.ImagesSubmissionForm;
import org.gotocy.forms.validation.user.property.ImagesSubmissionFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class UserPropertyImagesController {

	private final ApplicationProperties applicationProperties;
	private final PropertyService propertyService;

	@Autowired
	public UserPropertyImagesController(ApplicationProperties applicationProperties, PropertyService propertyService) {
		this.applicationProperties = applicationProperties;
		this.propertyService = propertyService;
	}

	@RequestMapping(value = "/user/properties/{id}/images/edit", method = RequestMethod.GET)
	public String edit(Model model, @RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, Locale locale)
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		model.addAttribute(property);
		model.addAttribute(new ImagesSubmissionForm());
		model.addAttribute(new ImagesEditorForm(property.getImages()));
		return "user/property/image/edit";
	}

	@RequestMapping(value = "/user/properties/{id}/images", method = RequestMethod.POST)
	public String create(Model model, @RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, @Valid @ModelAttribute ImagesSubmissionForm form, BindingResult formErrors)
		throws IOException
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		ImagesSubmissionFormValidator validator =
			new ImagesSubmissionFormValidator(user, property, applicationProperties);
		validator.validate(form, formErrors);
		if (formErrors.hasErrors()) {
			model.addAttribute(property);
			model.addAttribute(new ImagesEditorForm(property.getImages()));
			return "user/property/image/edit";
		}

		Collection<Image> attached = form.mapFilesToImages();
		propertyService.attachImages(property, attached);
		return "redirect:" + Helper.path(Paths.USER, property);
	}

	@RequestMapping(value = "/user/properties/{id}/images", method = RequestMethod.PUT)
	public String update(Model model, @RequiredDomainObject @PathVariable("id") Property property,
		@AuthenticationPrincipal GtcUser user, @Valid @ModelAttribute ImagesEditorForm form, BindingResult formErrors)
	{
		if (!property.isEditableBy(user))
			throw new AccessDeniedException();

		Collection<Image> removed = form.getRemoved(property.getImages());
		propertyService.detachImages(property, removed);
		return "redirect:" + Helper.path(Paths.USER, property);
	}

}
