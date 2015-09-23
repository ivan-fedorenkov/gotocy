package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.Image;
import org.gotocy.domain.Owner;
import org.gotocy.domain.Property;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.OwnerRepository;
import org.gotocy.repository.PropertyPredicates;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private PropertyRepository repository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	AssetsProvider assetsProvider;

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Page<Property> properties = repository.findAll(form.toPredicate(), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable("id") Property property, Locale locale) {
		property.initLocalizedFields(locale);
		model.addAttribute(property);

		Page<Property> similar = repository.findAll(PropertyPredicates.similarWith(property), new PageRequest(0, 4));
		model.addAttribute("similarProperties", similar);

		return "property/show";
	}

	@RequestMapping(value = "/property/{id}/pano.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getPanoXml(@PathVariable("id") Property property) {
		return assetsProvider.loadUnderlyingObject(property.getPanoXml()).getObject();
	}

	@RequestMapping(value = "/property/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		Image imageKey = new Image();
		imageKey.setKey("property/" + id + "/360_images/" + image + ".jpg");
		return assetsProvider.loadUnderlyingObject(imageKey).getObject();
	}

	@RequestMapping(value = "/master/properties/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("owners", ownerRepository.findAll());
		model.addAttribute(new PropertyForm());
		return "master/property/new";
	}

	@RequestMapping(value = "/master/properties", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Property create(PropertyForm propertyForm) {
		Owner owner = getOrCreateOwner(propertyForm.getOwnerId());
		owner = propertyForm.mergeWithOwner(owner);

		Property property = propertyForm.mergeWithProperty(new Property());
		property.setOwner(owner);
		return repository.saveAndFlush(property);
	}

	@RequestMapping(value = "/master/property/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") Property property) {
		model.addAttribute(property);
		model.addAttribute(new PropertyForm(property));
		model.addAttribute("owners", ownerRepository.findAll());

		return "master/property/edit";
	}


	@RequestMapping(value = "/master/property/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public Property update(@PathVariable("id") Property property, PropertyForm propertyForm, Locale locale) {
		Owner owner = getOrCreateOwner(propertyForm.getOwnerId());
		owner = propertyForm.mergeWithOwner(owner);

		property.initLocalizedFields(locale);
		property = propertyForm.mergeWithProperty(property);
		property.setOwner(owner);
		return repository.saveAndFlush(property);
	}

	private Owner getOrCreateOwner(Long ownerId) {
		return ownerId != null && ownerId > 0 ?
			ownerRepository.findOne(ownerId) : ownerRepository.saveAndFlush(new Owner());
	}

}
