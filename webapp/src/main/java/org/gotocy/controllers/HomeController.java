package org.gotocy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ifedorenkov
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "home/index";
	}

}
