package org.gotocy.controllers.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.forms.user.ContactsForm;
import org.gotocy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
public class UserProfileController extends AbstractUserController {

	@Autowired
	public UserProfileController(UserService userService) {
		super(userService);
	}

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String show(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		GtcUser user = resolveGtcUser(userDetails);
		model.addAttribute(new ContactsForm(user));
		return "user/profile/show";
	}

}
