package org.gotocy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ifedorenkov
 */
@RestController
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

}
