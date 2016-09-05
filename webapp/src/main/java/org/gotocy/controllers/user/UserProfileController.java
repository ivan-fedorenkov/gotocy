package org.gotocy.controllers.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.forms.user.profile.ProfileForm;
import org.gotocy.forms.validation.user.profile.ProfileFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author ifedorenkov
 */
@Controller
public class UserProfileController {

	private final UserService userService;
	private final ProfileFormValidator formValidator;

	@Autowired
	public UserProfileController(UserService userService, ProfileFormValidator formValidator) {
		this.userService = userService;
		this.formValidator = formValidator;
	}

	@InitBinder("profileForm")
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && formValidator.supports(binder.getTarget().getClass()))
			binder.addValidators(formValidator);
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String show(Model model, @AuthenticationPrincipal GtcUser user) {
		model.addAttribute(new ProfileForm(user));
		return "user/profile/show";
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.PUT)
	public String update(Model model, @AuthenticationPrincipal GtcUser user,
		@Valid @ModelAttribute ProfileForm profileForm, BindingResult formErrors)
	{
		if (formErrors.hasErrors())
			return "user/profile/show";
		profileForm.mergeWithGtcUser(user);
		userService.update(user);
		return "redirect:" + Helper.path("/user/profile");
	}

}
