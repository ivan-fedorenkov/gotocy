package org.gotocy.controllers;

import org.gotocy.beans.AssetsManager;
import org.gotocy.config.ApplicationProperties;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.forms.PropertyForm;
import org.gotocy.forms.UserPropertyForm;
import org.gotocy.forms.validation.UserPropertyFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.ComplexRepository;
import org.gotocy.repository.ContactRepository;
import org.gotocy.repository.DeveloperRepository;
import org.gotocy.repository.PropertyRepository;
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

import static org.gotocy.repository.PropertyPredicates.publiclyVisible;
import static org.gotocy.repository.PropertyPredicates.similarWith;

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
	@Autowired
	private ApplicationProperties applicationProperties;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && UserPropertyFormValidator.INSTANCE.supports(binder.getTarget().getClass()))
			binder.addValidators(UserPropertyFormValidator.INSTANCE);
	}

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Page<Property> properties = repository.findAll(publiclyVisible().and(form.toPredicate()), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		property.initLocalizedFields(locale);
		model.addAttribute(property);

		model.addAttribute("similarProperties",
			repository.findAll(publiclyVisible().and(similarWith(property)), new PageRequest(0, 4)));
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

		PropertyForm propertyForm = new PropertyForm();
		propertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		propertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(propertyForm);

		return "master/property/new";
	}

	@RequestMapping(value = "/property/new", method = RequestMethod.GET)
	public String newByUser(Model model) {
		UserPropertyForm userPropertyForm = new UserPropertyForm();
		userPropertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		userPropertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(userPropertyForm);

		return "property/new";
	}

	@RequestMapping(value = "/property", method = RequestMethod.POST)
	@Transactional
	public String createByUser(@Valid @ModelAttribute UserPropertyForm form, BindingResult formErrors, Locale locale) {
		if (formErrors.hasErrors())
			return "property/new";

		Property property = form.mergeWithProperty(new Property());
		property.setOfferStatus(OfferStatus.DEMO);
		property.setDescription(property.getDescription(), locale);
		property = repository.saveAndFlush(property);

		MultipartFile[] images = form.getImages();
		if (images != null && images.length > 0) {
			List<Image> createdImages = new ArrayList<>(images.length);
			try {
				for (MultipartFile image : images) {
					// Skip empty files
					if (image.isEmpty())
						continue;

					String fileName = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('/') + 1);
					Image imageAsset = new Image("property/" + property.getId() + "/" + fileName);
					imageAsset.setBytes(image.getBytes());
					assetsManager.saveUnderlyingObject(imageAsset);
					createdImages.add(imageAsset);
				}

				if (!createdImages.isEmpty()) {
					property.setImages(createdImages);
					property.setRepresentativeImage(createdImages.get(0));
					property = repository.saveAndFlush(property);
				}
			} catch (NullPointerException | IOException | DataAccessException e) {
				// Clean up created objects
				try {
					repository.delete(property);
					for (Image image : createdImages)
						assetsManager.deleteUnderlyingObject(image);
				} catch (DataAccessException | IOException ignored) {
					// TODO: log error
				}
				// TODO: show something to user
				return "property/new";
			}
		}
		return "redirect:" + Helper.path("/property/" + property.getId(), locale.getLanguage());
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
