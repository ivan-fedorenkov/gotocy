package org.gotocy.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
public class UserWelcomeController {

	@RequestMapping(value = "/user/welcome", method = RequestMethod.GET)
	public String show() {
		return "user/welcome/show";
	}

}
