package org.gotocy.controllers;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.controllers.exceptions.NotFoundException;
import org.gotocy.domain.Image;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.PanoXml;
import org.gotocy.domain.Property;
import org.gotocy.dto.PropertyDto;
import org.gotocy.dto.PropertyDtoFactory;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;
import static org.gotocy.repository.PropertyPredicates.publiclyVisible;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	private final PropertyRepository repository;
	private final AssetsManager assetsManager;
	private final ApplicationProperties applicationProperties;
	private final PropertyService propertyService;
	private final PropertyDtoFactory propertyDtoFactory;
	private final UserPropertyFormValidator userPropertyFormValidator;

	@Autowired
	public PropertiesController(PropertyRepository repository, AssetsManager assetsManager,
		ApplicationProperties applicationProperties, PropertyService propertyService,
		PropertyDtoFactory propertyDtoFactory)
	{
		this.repository = repository;
		this.assetsManager = assetsManager;
		this.applicationProperties = applicationProperties;
		this.propertyService = propertyService;
		this.propertyDtoFactory = propertyDtoFactory;
		userPropertyFormValidator = new UserPropertyFormValidator(applicationProperties);
	}

	@InitBinder("userPropertyForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userPropertyFormValidator);
	}

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(@ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(Helper.path(form, locale));
		if (pageable.getPageNumber() > 0)
			uriBuilder.queryParam("page", pageable.getPageNumber());
		return "redirect:" + uriBuilder.toUriString();
	}

	@RequestMapping(
		value = "/{formUri:(?:properties-|houses-|apartments-|land-|prodazha-|otdih-na-kipre-|arenda-|nedvizhimost-|apartamenti-|doma-|zemlya-)[\\w-]+}",
		method = RequestMethod.GET
	)
	public String indexSeo(Model model, @PathVariable("formUri") PropertiesSearchForm form,
		@RequestParam(required = false) String price,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		// Price is not being automatically set into the form in this case (resolved through @PathVariable)
		// TODO: investigate this later
		form.setPrice(price);

		Page<Property> properties = repository.findAll(publiclyVisible().and(form.toPredicate()), pageable);
		model.addAttribute("properties", properties);
		model.addAttribute(form);
		return "property/index";
	}

	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<PropertyDto> indexJson() {
		return repository.findAll().stream().map(propertyDtoFactory::create).collect(toList());
	}

	@RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Property property, Model model, Locale locale) {
		if (property.isPromo())
			return "redirect:" + Helper.path(property);

		property.initLocalizedFields(locale);
		model.addAttribute(property);
		model.addAttribute("featuredProperties", propertyService.getFeatured());

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
