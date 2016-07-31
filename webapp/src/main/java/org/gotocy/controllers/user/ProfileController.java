package org.gotocy.controllers.user;

import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.GtcUser;
import org.gotocy.forms.user.ProfileForm;
import org.gotocy.helpers.Helper;
import org.gotocy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author ifedorenkov
 */
@Controller
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	private final UserService userService;

	@Autowired
	public ProfileController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String show(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		GtcUser user = resolveGtcUser(userDetails);
		model.addAttribute(new ProfileForm(user));
		return "user/profile/show";
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.PUT)
	public String update(Model model, @AuthenticationPrincipal UserDetails userDetails,
		@Valid @ModelAttribute ProfileForm profileForm, BindingResult formErrors)
	{
		if (formErrors.hasErrors())
			return "user/profile/show";
		GtcUser user = profileForm.mergeWithGtcUser(resolveGtcUser(userDetails));
		userService.update(user);
		return "redirect:" + Helper.path("/user/profile");
	}

	private GtcUser resolveGtcUser(UserDetails userDetails) {
		GtcUser user = userService.findByUsername(userDetails.getUsername());
		if (user == null) {
			logger.error("Failed to load user profile of user {}", userDetails.getUsername());
			throw new DomainObjectNotFoundException();
		}
		return user;
	}

}
