package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.Image;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private LocalizedPropertyRepository repository;
	@Autowired
	AssetsProvider assetsProvider;

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(required = false) PropertyStatus propertyStatus, Locale locale,
		@PageableDefault(size = 40, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		List<LocalizedProperty> properties = propertyStatus == null ?
			repository.findByLocale(locale.getLanguage(), pageable) :
			repository.findByPropertyPropertyStatusAndLocale(propertyStatus, locale.getLanguage(), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable Long id, Locale locale) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty lp = repository.findProperty(id, locale.getLanguage());
		// TODO: replace with custom exception
		if (lp == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);
		model.addAttribute(lp);

		List<LocalizedProperty> similar = repository.findSimilar(lp.getProperty().getPropertyStatus(),
			lp.getProperty().getLocation(), locale.getLanguage(), new PageRequest(0, 4));

		// Remove self or last (if there was no self
		if (!similar.remove(lp))
			similar.remove(3);
		model.addAttribute("similarProperties", similar);

		return "property/show";
	}

	@RequestMapping(value = "/property/{id}/pano", method = RequestMethod.GET)
	public String getPano(@PathVariable("id") Property property) {
		return "property/pano";
	}

	@RequestMapping(value = "/property/{id}/pano.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getPanoXml(@PathVariable("id") Property property) {
		return assetsProvider.loadUnderlyingObject(property.getPanoXml()).getObject();
	}

	@RequestMapping(value ="/property/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		Image imageKey = new Image();
		imageKey.setKey("property/" + id + "/360_images/" + image + ".jpg");
		return assetsProvider.loadUnderlyingObject(imageKey).getObject();
	}

}
