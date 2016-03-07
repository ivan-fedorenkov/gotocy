package org.gotocy.controllers;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.gotocy.domain.Image;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.PanoXml;
import org.gotocy.domain.Property;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.forms.UserPropertyForm;
import org.gotocy.forms.validation.UserPropertyFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.service.AssetsManager;
import org.gotocy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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

import static org.gotocy.repository.PropertyPredicates.*;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping(value = "/properties")
public class PropertiesController {

	private PropertyRepository repository;
	private AssetsManager assetsManager;
	private ApplicationProperties applicationProperties;
	private PropertyService propertyService;

	@Autowired
	public PropertiesController(PropertyRepository repository, AssetsManager assetsManager,
		ApplicationProperties applicationProperties, PropertyService propertyService)
	{
		this.repository = repository;
		this.assetsManager = assetsManager;
		this.applicationProperties = applicationProperties;
		this.propertyService = propertyService;
	}

	@InitBinder("userPropertyForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(UserPropertyFormValidator.INSTANCE);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Page<Property> properties = repository.findAll(publiclyVisible().and(form.toPredicate()), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		if (property.getOfferStatus() == OfferStatus.PROMO)
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute("featuredProperties", repository.findAll(publiclyVisible().and(featured()).and(ne(property))));

		return "property/show";
	}

	@RequestMapping(value = "/{id}/pano.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getPanoXml(@RequiredDomainObject @PathVariable("id") Property property) {
		return assetsManager.getAsset(PanoXml::new, property.getPanoXml().getKey())
			.orElseThrow(NotFoundException::new).decodeToXml();
	}

	@RequestMapping(value = "/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		return assetsManager.getAsset(Image::new, "property/" + id + "/360_images/" + image + ".jpg")
			.orElseThrow(NotFoundException::new).getBytes();
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newByUser(Model model) {
		UserPropertyForm userPropertyForm = new UserPropertyForm();
		userPropertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		userPropertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(userPropertyForm);

		return "property/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createByUser(@Valid @ModelAttribute UserPropertyForm userPropertyForm, BindingResult formErrors,
		Locale locale) throws IOException
	{
		if (formErrors.hasErrors())
			return "property/new";

		Property property = userPropertyForm.mergeWithProperty(new Property());
		property.setOfferStatus(OfferStatus.PROMO);
		property.setDescription(property.getDescription(), locale);

		List<Image> images = new ArrayList<>();
		if (!userPropertyForm.getImages().isEmpty()) {
			for (MultipartFile file : userPropertyForm.getImages()) {
				String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('/') + 1);
				Image image = new Image(fileName);
				image.setBytes(file.getBytes());
				images.add(image);
			}

			property.setImages(images);
			property.setRepresentativeImage(images.get(0));
		}

		propertyService.create(property);
		return "redirect:" + Helper.path(property);
	}

}
