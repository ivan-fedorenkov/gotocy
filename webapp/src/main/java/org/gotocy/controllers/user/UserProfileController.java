package org.gotocy.controllers.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.forms.user.ContactsForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
public class UserProfileController {

	@RequestMapping(value = "/user/profile", method = RequestMethod.GET)
	public String show(Model model, @AuthenticationPrincipal GtcUser user) {
		model.addAttribute(new ContactsForm(user));
		return "user/profile/show";
	}

}
