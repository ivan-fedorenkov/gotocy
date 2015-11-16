package org.gotocy.controllers;

import org.gotocy.domain.PropertyStatus;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import static org.gotocy.repository.PropertyPredicates.featured;
import static org.gotocy.repository.PropertyPredicates.publiclyVisible;
import static org.gotocy.repository.PropertyPredicates.inStatus;

/**
 * @author ifedorenkov
 */
@Controller
public class HomeController {

	private final PropertyRepository repository;

	@Autowired
	public HomeController(PropertyRepository repository) {
		this.repository = repository;
	}

	@RequestMapping("/")
	public String home(Model model, Locale locale,
		@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		model.addAttribute("featuredProperties", repository.findAll(publiclyVisible().and(featured())));

		// TODO: make them recent :)
		model.addAttribute("longTermProperties", repository.findAll(
			publiclyVisible().and(inStatus(PropertyStatus.LONG_TERM)), pageable));
		model.addAttribute("shortTermProperties", repository.findAll(
			publiclyVisible().and(inStatus(PropertyStatus.SHORT_TERM)), pageable));
		model.addAttribute("saleProperties", repository.findAll(
			publiclyVisible().and(inStatus(PropertyStatus.SALE)), pageable));
		return "home/index";
	}

}
