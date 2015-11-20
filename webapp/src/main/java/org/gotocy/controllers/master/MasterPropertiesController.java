package org.gotocy.controllers.master;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Contact;
import org.gotocy.domain.Developer;
import org.gotocy.domain.Property;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.ComplexRepository;
import org.gotocy.repository.ContactRepository;
import org.gotocy.repository.DeveloperRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping(value = "/master/properties")
public class MasterPropertiesController {

	private final ApplicationProperties applicationProperties;

	private final PropertyRepository propertyRepository;
	private final DeveloperRepository developerRepository;
	private final ComplexRepository complexRepository;
	private final ContactRepository contactRepository;

	@Autowired
	public MasterPropertiesController(ApplicationProperties applicationProperties,
		PropertyRepository propertyRepository, DeveloperRepository developerRepository,
		ComplexRepository complexRepository, ContactRepository contactRepository)
	{
		this.applicationProperties = applicationProperties;
		this.propertyRepository = propertyRepository;
		this.developerRepository = developerRepository;
		this.complexRepository = complexRepository;
		this.contactRepository = contactRepository;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());

		PropertyForm propertyForm = new PropertyForm();
		propertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		propertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(propertyForm);

		return "master/property/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Property create(PropertyForm propertyForm) {
		Contact contact = getOrCreateContact(propertyForm.getContactId());
		contact = propertyForm.mergeWithContact(contact);

		Property property = propertyForm.mergeWithProperty(new Property());
		property.setPrimaryContact(contact);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		return propertyRepository.save(property);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@RequiredDomainObject @PathVariable("id") Property property, Model model) {
		model.addAttribute(property);
		model.addAttribute(new PropertyForm(property));
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());

		return "master/property/edit";
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public Property update(@RequiredDomainObject @PathVariable("id") Property property, PropertyForm propertyForm) {
		Contact contact = getOrCreateContact(propertyForm.getContactId());
		contact = propertyForm.mergeWithContact(contact);

		property = propertyForm.mergeWithProperty(property);
		property.setPrimaryContact(contact);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		return propertyRepository.save(property);
	}

	private Complex getComplex(long complexId) {
		return complexId > 0 ? complexRepository.findOne(complexId) : null;
	}

	private Developer getDeveloper(long developerId) {
		return developerId > 0 ? developerRepository.findOne(developerId) : null;
	}

	private Contact getOrCreateContact(long contactId) {
		return contactId > 0 ? contactRepository.findOne(contactId) : contactRepository.save(new Contact());
	}
	
}
