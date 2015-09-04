package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.gotocy.dto.PropertiesSearchForm;
import org.gotocy.dto.PropertyJson;
import org.gotocy.dto.PropertyForm;
import org.gotocy.helpers.Helper;
import org.gotocy.helpers.property.FieldFormat;
import org.gotocy.helpers.property.PropertyHelper;
import org.gotocy.repository.LocalizedPropertyPredicates;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.OwnerRepository;
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
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.gotocy.repository.LocalizedPropertyPredicates.forLocale;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private LocalizedPropertyRepository repository;
	@Autowired
	AssetsProvider assetsProvider;
	@Autowired
	PropertyHelper propertyHelper;

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @ModelAttribute PropertiesSearchForm form, Locale locale,
		@PageableDefault(size = 18, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		Page<LocalizedProperty> properties = repository.findAll(form.setLocale(locale).toPredicate(), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Iterable<PropertyJson> indexJson(Locale locale) {
		return StreamSupport.stream(repository.findAll(forLocale(locale)).spliterator(), false)
			.map(lp -> {
				PropertyJson p = new PropertyJson();
				p.setTitle(lp.getProperty().getTitle());
				p.setLatitude(lp.getProperty().getLatitude());
				p.setLongitude(lp.getProperty().getLongitude());
				p.setShortAddress(lp.getProperty().getShortAddress());
				p.setPrice(propertyHelper.price(lp.getProperty()));
				p.setTypeIcon(PropertyHelper.typeIcon(lp.getProperty().getPropertyType()));
				p.setPropertyUrl(Helper.path(lp));
				p.setRepresentativeImageUrl(assetsProvider.getImageUrl(lp.getProperty().getRepresentativeImage(),
					ImageSize.MEDIUM));
				return p;
			})
			.collect(toList());
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable Long id, Locale locale) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty lp = repository.findByPropertyIdAndLocale(id, locale.getLanguage());
		// TODO: replace with custom exception
		if (lp == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);
		model.addAttribute(lp);

		Page<LocalizedProperty> similar = repository.findAll(LocalizedPropertyPredicates.similarWith(lp),
			new PageRequest(0, 4));

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
	public Iterable<LocalizedProperty> create(PropertyForm propertyForm) {
		Owner owner = getOrCreateOwner(propertyForm.getOwnerId());
		owner = propertyForm.mergeWithOwner(owner);

		Property property = propertyForm.mergeWithProperty(new Property());
		property.setOwner(owner);
		property = propertyRepository.saveAndFlush(property);

		LocalizedProperty enLP = propertyForm.mergeWithEnLocalizedProperty(findOrCreateLP(property, "en"));
		LocalizedProperty ruLP = propertyForm.mergeWithRuLocalizedProperty(findOrCreateLP(property, "ru"));
		enLP = repository.saveAndFlush(enLP);
		ruLP = repository.saveAndFlush(ruLP);

		return Arrays.asList(enLP, ruLP);
	}

	@RequestMapping(value = "/master/property/{property}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("property") Property property) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty enLP = repository.findByPropertyIdAndLocale(property.getId(), "en");
		LocalizedProperty ruLP = repository.findByPropertyIdAndLocale(property.getId(), "ru");

		// TODO: replace with custom exception
		if (enLP == null || ruLP == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);

		model.addAttribute(new PropertyForm(property, enLP, ruLP));
		model.addAttribute("owners", ownerRepository.findAll());

		return "master/property/edit";
	}


	@RequestMapping(value = "/master/property/{property}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public List<LocalizedProperty> update(@PathVariable("property") Property property, PropertyForm propertyForm) {
		Owner owner = getOrCreateOwner(propertyForm.getOwnerId());
		owner = propertyForm.mergeWithOwner(owner);

		property = propertyForm.mergeWithProperty(property);
		property.setOwner(owner);
		property = propertyRepository.saveAndFlush(property);

		LocalizedProperty enLP = propertyForm.mergeWithEnLocalizedProperty(findOrCreateLP(property, "en"));
		LocalizedProperty ruLP = propertyForm.mergeWithRuLocalizedProperty(findOrCreateLP(property, "ru"));
		enLP = repository.saveAndFlush(enLP);
		ruLP = repository.saveAndFlush(ruLP);

		return Arrays.asList(enLP, ruLP);
	}

	private Owner getOrCreateOwner(Long ownerId) {
		return ownerId != null && ownerId > 0 ?
			ownerRepository.findOne(ownerId) : ownerRepository.saveAndFlush(new Owner());
	}

	private LocalizedProperty findOrCreateLP(Property property, String locale) {
		LocalizedProperty lp = repository.findByPropertyIdAndLocale(property.getId(), locale);
		if (lp == null) {
			lp = new LocalizedProperty();
			lp.setProperty(property);
			lp.setLocale(locale);
			lp = repository.saveAndFlush(lp);
		}
		return lp;
	}

}
