package org.gotocy.controllers;

import org.gotocy.domain.PropertyStatus;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class HomeController {

	private final LocalizedPropertyRepository localizedPropertyRepository;

	@Autowired
	public HomeController(LocalizedPropertyRepository localizedPropertyRepository) {
		this.localizedPropertyRepository = localizedPropertyRepository;
	}

	@RequestMapping("/")
	public String home(Model model, Locale locale) {
		// TODO: make them recent :)
		model.addAttribute("shortTermProperties", localizedPropertyRepository.findByPropertyPropertyStatusAndLocale(
			PropertyStatus.SHORT_TERM, locale.getLanguage()));
		model.addAttribute("saleProperties", localizedPropertyRepository.findByPropertyPropertyStatusAndLocale(
			PropertyStatus.SALE, locale.getLanguage()));
		return "home/index";
	}

}
