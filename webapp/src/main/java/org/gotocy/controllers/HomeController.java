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
		// TODO: make them recent :)
		model.addAttribute("longTermProperties", repository.findByPropertyStatus(PropertyStatus.LONG_TERM, pageable));
		model.addAttribute("shortTermProperties", repository.findByPropertyStatus(PropertyStatus.SHORT_TERM, pageable));
		model.addAttribute("saleProperties", repository.findByPropertyStatus(PropertyStatus.SALE, pageable));
		return "home/index";
	}

}
