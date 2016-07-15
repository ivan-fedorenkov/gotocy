package org.gotocy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
public class SessionController {

	@RequestMapping(value = "/session/new", method = RequestMethod.GET)
	public String _new() {
		return "session/new";
	}

}
