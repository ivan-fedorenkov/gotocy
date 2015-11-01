package org.gotocy.controllers;

import org.gotocy.beans.AssetsManager;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.forms.PropertyForm;
import org.gotocy.forms.UserPropertyForm;
import org.gotocy.forms.validation.UserPropertyFormValidator;
import org.gotocy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	private AssetsManager assetsManager;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && UserPropertyFormValidator.INSTANCE.supports(binder.getTarget().getClass()))
			binder.addValidators(UserPropertyFormValidator.INSTANCE);
	}

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Page<Property> properties = repository.findAll(form.toPredicate(), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		property.initLocalizedFields(locale);
		model.addAttribute(property);

		model.addAttribute("similarProperties",
			repository.findAll(PropertyPredicates.similarWith(property), new PageRequest(0, 4)));
		model.addAttribute("showRegistrationOffer", property.getOfferStatus() == OfferStatus.DEMO);

		return "property/show";
	}

	@RequestMapping(value = "/property/{id}/pano.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getPanoXml(@RequiredDomainObject @PathVariable("id") Property property) {
		return assetsManager.loadUnderlyingObject(property.getPanoXml()).decodeToXml();
	}

	@RequestMapping(value = "/property/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		Image imageKey = new Image();
		imageKey.setKey("property/" + id + "/360_images/" + image + ".jpg");
		return assetsManager.loadUnderlyingObject(imageKey).getBytes();
	}

	@RequestMapping(value = "/master/properties/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());
		model.addAttribute(new PropertyForm());
		return "master/property/new";
	}

	@RequestMapping(value = "/property/new", method = RequestMethod.GET)
	public String newByUser(@ModelAttribute UserPropertyForm userPropertyForm) {
		return "property/new";
	}

	@RequestMapping(value = "/property", method = RequestMethod.POST)
	@Transactional
	public String createByUser(@Valid @ModelAttribute UserPropertyForm form, BindingResult formErrors, Locale locale,
		@RequestParam(value = "files", required = false) MultipartFile[] files)
	{
		if (formErrors.hasErrors())
			return "property/new";

		Property property = form.mergeWithProperty(new Property());
		property.setOfferStatus(OfferStatus.DEMO);
		property.setDescription(property.getDescription(), locale);
		property = repository.saveAndFlush(property);

		if (files.length > 0) {
			List<Image> images = new ArrayList<>(files.length);
			try {
				// TODO: check extension (that a file is a valid image)
				for (MultipartFile file : files) {
					String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('/' + 1));
					Image image = new Image("property/" + property.getId() + "/" + fileName);
					image.setBytes(file.getBytes());
					assetsManager.saveUnderlyingObject(image);
					images.add(image);
				}

				property.setImages(images);
				property.setRepresentativeImage(images.get(0));
				property = repository.saveAndFlush(property);
			} catch (IOException | DataAccessException e) {
				// Clean up created objects
				try {
					repository.delete(property);
					for (Image image : images)
						assetsManager.deleteUnderlyingObject(image);
				} catch (DataAccessException | IOException ignored) {
					// TODO: log error
				}
				return "property/new";
			}
		}
		return "redirect:/property/" + property.getId();
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
	public String edit(@RequiredDomainObject @PathVariable("id") Property property, Model model) {
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
	public Property update(@RequiredDomainObject @PathVariable("id") Property property, PropertyForm propertyForm) {
		Contact contact = getOrCreateContact(propertyForm.getContactId());
		contact = propertyForm.mergeWithContact(contact);

		property = propertyForm.mergeWithProperty(property);
		property.setPrimaryContact(contact);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		return repository.saveAndFlush(property);
	}

	private Complex getComplex(long complexId) {
		return complexId > 0 ? complexRepository.findOne(complexId) : null;
	}

	private Developer getDeveloper(long developerId) {
		return developerId > 0 ? developerRepository.findOne(developerId) : null;
	}

	private Contact getOrCreateContact(long contactId) {
		return contactId > 0 ? contactRepository.findOne(contactId) : contactRepository.saveAndFlush(new Contact());
	}

}
