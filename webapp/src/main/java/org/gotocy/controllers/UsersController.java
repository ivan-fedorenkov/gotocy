package org.gotocy.controllers;

import org.gotocy.config.Roles;
import org.gotocy.domain.GtcUser;
import org.gotocy.forms.UserRegistrationForm;
import org.gotocy.forms.validation.UserRegistrationFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collections;

/**
 * @author ifedorenkov
 */
@Controller
public class UsersController {

	private final UserService userService;
	private final UserRegistrationFormValidator formValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && formValidator.supports(binder.getTarget().getClass()))
			binder.addValidators(formValidator);
	}

	@Autowired
	public UsersController(UserService userService, UserRegistrationFormValidator formValidator) {
		this.userService = userService;
		this.formValidator = formValidator;
	}

	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute(new UserRegistrationForm());
		return "user/new";
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public String create(Model model, @ModelAttribute @Valid UserRegistrationForm form, BindingResult errors) {
		if (errors.hasErrors()) {
			// Clean up passwords
			form.setPassword("");
			form.setConfirmPassword("");
			return "user/new";
		}
		// Enforce the Roles.USER role
		form.setRoles(Collections.singleton(Roles.USER));
		GtcUser registeredUser = userService.register(form.toUser());
		model.addAttribute(registeredUser);
		return "redirect:" + Helper.path("/");
	}

}
