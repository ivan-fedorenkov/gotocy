package org.gotocy.controllers;

import org.gotocy.beans.AssetsManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
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

import static org.gotocy.repository.PropertyPredicates.*;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping(value = "/properties")
public class PropertiesController {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);

	private PropertyRepository repository;
	private AssetsManager assetsManager;
	private ApplicationProperties applicationProperties;

	@Autowired
	public PropertiesController(PropertyRepository repository, AssetsManager assetsManager,
			ApplicationProperties applicationProperties)
	{
		this.repository = repository;
		this.assetsManager = assetsManager;
		this.applicationProperties = applicationProperties;
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
		return assetsManager.getFullyLoadedAsset(PanoXml::new, property.getPanoXml().getKey())
			.orElseThrow(NotFoundException::new).decodeToXml();
	}

	@RequestMapping(value = "/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		return assetsManager.getFullyLoadedAsset(Image::new, "property/" + id + "/360_images/" + image + ".jpg")
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
	@Transactional
	public String createByUser(@Valid @ModelAttribute UserPropertyForm userPropertyForm, BindingResult formErrors,
		Locale locale)
	{
		if (formErrors.hasErrors())
			return "property/new";

		Property property = userPropertyForm.mergeWithProperty(new Property());
		property.setOfferStatus(OfferStatus.PROMO);
		property.setDescription(property.getDescription(), locale);
		property = repository.save(property);

		List<MultipartFile> images = userPropertyForm.getImages();
		if (!images.isEmpty()) {
			List<Image> createdImages = new ArrayList<>(images.size());
			try {
				for (MultipartFile image : images) {
					String fileName = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('/') + 1);
					Image imageAsset = new Image("property/" + property.getId() + "/" + fileName);
					imageAsset.setBytes(image.getBytes());
					assetsManager.saveUnderlyingObject(imageAsset);
					createdImages.add(imageAsset);
				}

				property.setImages(createdImages);
				property.setRepresentativeImage(createdImages.get(0));
				property = repository.save(property);
			} catch (NullPointerException | IOException | DataAccessException e) {
				// Log error
				logger.error("Failed to upload property's assets.", e);

				// Clean up created objects
				try {
					repository.delete(property);
					for (Image image : createdImages)
						assetsManager.deleteUnderlyingObject(image);
				} catch (DataAccessException | IOException ee) {
					logger.error("Failed to clean up resources.", ee);
				}
				// TODO: show something to user
				return "property/new";
			}
		}
		return "redirect:" + Helper.path(property);
	}

}
