package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.*;
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
	private ContactRepository contactRepository;
	@Autowired
	private ComplexRepository complexRepository;
	@Autowired
	private DeveloperRepository developerRepository;
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
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());
		model.addAttribute(new PropertyForm());
		return "master/property/new";
	}

	@RequestMapping(value = "/property", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Property createByUser(PropertyForm propertyForm) {
		Property property = propertyForm.mergeWithProperty(new Property());
		property.setOfferStatus(OfferStatus.ACTIVE);
		return repository.saveAndFlush(property);
	}

	@RequestMapping(value = "/master/properties", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Property create(PropertyForm propertyForm) {
		Contact contact = getOrCreateContact(propertyForm.getContactId());
		contact = propertyForm.mergeWithContact(contact);

		Property property = propertyForm.mergeWithProperty(new Property());
		property.setPrimaryContact(contact);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		return repository.saveAndFlush(property);
	}

	@RequestMapping(value = "/master/property/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") Property property) {
		model.addAttribute(property);
		model.addAttribute(new PropertyForm(property));
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());

		return "master/property/edit";
	}


	@RequestMapping(value = "/master/property/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public Property update(@PathVariable("id") Property property, PropertyForm propertyForm) {
		Contact contact = getOrCreateContact(propertyForm.getContactId());
		contact = propertyForm.mergeWithContact(contact);

		property = propertyForm.mergeWithProperty(property);
		property.setPrimaryContact(contact);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		return repository.saveAndFlush(property);
	}

	private Complex getComplex(Long complexId) {
		return complexId == null ? null : complexRepository.findOne(complexId);
	}

	private Developer getDeveloper(Long developerId) {
		return developerId == null ? null : developerRepository.findOne(developerId);
	}

	private Contact getOrCreateContact(Long contactId) {
		return contactId != null && contactId > 0 ?
			contactRepository.findOne(contactId) : contactRepository.saveAndFlush(new Contact());
	}

}
