package org.gotocy.controllers;

import org.gotocy.forms.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
public class RegistrationController {

	@RequestMapping(name = "/registrations", method = RequestMethod.POST)
	public String createRegistration(@ModelAttribute RegistrationForm registrationForm) {
		throw new UnsupportedOperationException();
	}

}
