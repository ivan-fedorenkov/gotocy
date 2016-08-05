package org.gotocy.controllers;

import org.gotocy.domain.OfferType;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import static org.gotocy.repository.PropertyPredicates.withOfferType;

/**
 * @author ifedorenkov
 */
@Controller
public class HomeController {

	private final PropertyService propertyService;

	@Autowired
	public HomeController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@RequestMapping("/")
	public String home(Model model, Locale locale,
		@PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		model.addAttribute("featuredProperties", propertyService.getFeatured());
		model.addAttribute("longTermProperties", propertyService.findPubliclyVisible(
			withOfferType(OfferType.LONG_TERM), pageable));
		model.addAttribute("shortTermProperties", propertyService.findPubliclyVisible(
			withOfferType(OfferType.SHORT_TERM), pageable));
		model.addAttribute("saleProperties", propertyService.findPubliclyVisible(
			withOfferType(OfferType.SALE), pageable));
		model.addAttribute("propertiesSearchForm", new PropertiesSearchForm());

		model.addAttribute("shortTermPropertiesForm", PropertiesSearchForm.SHORT_TERM_FORM);
		model.addAttribute("longTermPropertiesForm", PropertiesSearchForm.LONG_TERM_FORM);
		model.addAttribute("salePropertiesForm", PropertiesSearchForm.SALE_FORM);

		return "home/index";
	}

}
