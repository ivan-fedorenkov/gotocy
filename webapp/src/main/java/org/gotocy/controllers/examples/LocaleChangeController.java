package org.gotocy.controllers.examples;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * An example controller that allows a user to change the locale (language).
 * TODO: remove
 *
 * @author ifedorenkov
 */
@RestController
public class LocaleChangeController {

	@RequestMapping("/say-hello-localized")
	public String home(Locale locale) {
		switch (locale.getLanguage()) {
		case "ru":
			return "Привет!";
		default:
			return "Hello World!";
		}
	}

}
