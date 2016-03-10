package org.gotocy.controllers;

import lombok.Getter;
import lombok.Setter;
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
import org.gotocy.helpers.property.PropertyHelper;
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

import static java.util.stream.Collectors.toList;
import static org.gotocy.repository.PropertyPredicates.*;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	private PropertyRepository repository;
	private AssetsManager assetsManager;
	private ApplicationProperties applicationProperties;
	private PropertyService propertyService;
	private PropertyHelper propertyHelper;

	@Autowired
	public PropertiesController(PropertyRepository repository, AssetsManager assetsManager,
		ApplicationProperties applicationProperties, PropertyService propertyService)
	{
		this.repository = repository;
		this.assetsManager = assetsManager;
		this.applicationProperties = applicationProperties;
		this.propertyService = propertyService;
		propertyHelper = new PropertyHelper(applicationProperties, assetsManager);
	}

	@InitBinder("userPropertyForm")
	public void initBinder(WebDataBinder binder) {
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

	@Getter
	@Setter
	public static class PropertyJson {
		private String title;
		private double latitude;
		private double longitude;
		private String shortAddress;
		private String typeIcon;
		private String price;
		private String propertyUrl;
		private String representativeImageUrl;
	}

	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<PropertyJson> indexJson(Locale locale) {
		return repository.findAll().stream()
			.map(property -> {
				PropertyJson asJson = new PropertyJson();
				asJson.setTitle(property.getTitle());
				asJson.setLatitude(property.getLatitude());
				asJson.setLongitude(property.getLongitude());
				asJson.setShortAddress(property.getShortAddress());
				asJson.setPrice(PropertyHelper.price(property));
				asJson.setTypeIcon(PropertyHelper.typeIcon(property.getPropertyType()));
				asJson.setPropertyUrl(Helper.path(property));
				asJson.setRepresentativeImageUrl(propertyHelper.representativeImageUrl(property));
				return asJson;
			})
			.collect(toList());
	}

	@RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		if (property.getOfferStatus() == OfferStatus.PROMO)
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute("featuredProperties", repository.findAll(publiclyVisible().and(featured()).and(ne(property))));

		return "property/show";
	}

	@RequestMapping(value = "/properties/{id}/pano.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String getPanoXml(@RequiredDomainObject @PathVariable("id") Property property) {
		return assetsManager.getAsset(PanoXml::new, property.getPanoXml().getKey())
			.orElseThrow(NotFoundException::new).decodeToXml();
	}

	@RequestMapping(value = "/properties/{id}/360_images/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable String id, @PathVariable String image) {
		return assetsManager.getAsset(Image::new, "property/" + id + "/360_images/" + image + ".jpg")
			.orElseThrow(NotFoundException::new).getBytes();
	}

	@RequestMapping(value = "/properties/new", method = RequestMethod.GET)
	public String newByUser(Model model) {
		UserPropertyForm userPropertyForm = new UserPropertyForm();
		userPropertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		userPropertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(userPropertyForm);

		return "property/new";
	}

	@RequestMapping(value = "/properties", method = RequestMethod.POST)
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
