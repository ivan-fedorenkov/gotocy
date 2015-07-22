package org.gotocy.controllers;

import org.gotocy.domain.PropertyStatus;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	public String home(Model model, Locale locale,
		@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		// TODO: make them recent :)
		model.addAttribute("longTermProperties", localizedPropertyRepository.findByPropertyPropertyStatusAndLocale(
			PropertyStatus.LONG_TERM, locale.getLanguage(), pageable));
		model.addAttribute("shortTermProperties", localizedPropertyRepository.findByPropertyPropertyStatusAndLocale(
			PropertyStatus.SHORT_TERM, locale.getLanguage(), pageable));
		model.addAttribute("saleProperties", localizedPropertyRepository.findByPropertyPropertyStatusAndLocale(
			PropertyStatus.SALE, locale.getLanguage(), pageable));
		return "home/index";
	}

}
