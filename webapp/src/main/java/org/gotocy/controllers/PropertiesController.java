package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.LocalizedPropertyRepository;
import org.gotocy.repository.OwnerRepository;
import org.gotocy.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

	@RequestMapping(value = "/properties", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(required = false) PropertyStatus propertyStatus, Locale locale,
		@PageableDefault(size = 40, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
	{
		List<LocalizedProperty> properties = propertyStatus == null ?
			repository.findByLocale(locale.getLanguage(), pageable) :
			repository.findByPropertyPropertyStatusAndLocale(propertyStatus, locale.getLanguage(), pageable);
		model.addAttribute("properties", properties);
		return "property/index";
	}

	@RequestMapping(value = "/property/{id}", method = RequestMethod.GET)
	public String get(Model model, @PathVariable Long id, Locale locale) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty lp = repository.findProperty(id, locale.getLanguage());
		// TODO: replace with custom exception
		if (lp == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);
		model.addAttribute(lp);

		List<LocalizedProperty> similar = repository.findSimilar(lp.getProperty().getPropertyStatus(),
			lp.getProperty().getLocation(), locale.getLanguage(), new PageRequest(0, 4));

		// Remove self or last (if there was no self
		if (!similar.remove(lp))
			similar.remove(3);
		model.addAttribute("similarProperties", similar);

		return "property/show";
	}

	@RequestMapping(value = "/property/promo", method = RequestMethod.GET)
	public String getPromo(Model model) {

		// The Promo property
		model.addAttribute(createPromo());

		// List of featured properties (commercial)
		List<LocalizedProperty> featured = new ArrayList<>(3);
		for (int i = 0; i < 3; i++) {
			LocalizedProperty lp = createPromo();
			lp.getProperty().setRepresentativeImage(lp.getProperty().getImages().get(i));
			featured.add(lp);
		}
		model.addAttribute("featuredProperties", featured);

		return "property/promo";
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
		LocalizedProperty enLP = repository.findProperty(property.getId(), "en");
		LocalizedProperty ruLP = repository.findProperty(property.getId(), "ru");

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
		LocalizedProperty lp = repository.findProperty(property.getId(), locale);
		if (lp == null) {
			lp = new LocalizedProperty();
			lp.setProperty(property);
			lp.setLocale(locale);
			lp = repository.saveAndFlush(lp);
		}
		return lp;
	}

	private static LocalizedProperty createPromo() {
		Property p = new Property();
		p.setTitle("The Promo property");
		p.setAddress("Demetri Koumandari Str. No.1, 7103, Aradippou, Larnaca");
		p.setShortAddress("Demetri Koumandari Str.1, Larnaca");
		p.setLatitude(34.940275);
		p.setLongitude(33.590204);
		p.setPropertyStatus(PropertyStatus.LONG_TERM);
		p.setPropertyType(PropertyType.HOUSE);
		p.setOfferStatus(OfferStatus.RENTED);
		p.setReadyToMoveIn(Boolean.TRUE);
		p.setPrice(850);
		p.setGuests(10);
		p.setBedrooms(4);
		p.setDistanceToSea(4700);
		p.setFurnishing(Furnishing.FULL);
		p.setAirConditioner(Boolean.TRUE);
		p.setHeatingSystem(Boolean.TRUE);
		p.setReadyToMoveIn(Boolean.TRUE);

		Image representative = new Image();
		representative.setKey("property/promo/representative.jpg");
		p.setRepresentativeImage(representative);

		ImageSet imageSet = p.getImageSet();
		Image img;
		for (int i = 1; i < 5; i++) {
			img = new Image();
			img.setKey("property/promo/" + i + ".jpg");
			imageSet.getImages().add(img);
		}

		Owner owner = new Owner();
		owner.setName("Denis");
		owner.setPhone("+357 96 740485");
		owner.setEmail("support@gotocy.eu");
		owner.setSpokenLanguages("Eng, Rus");
		p.setOwner(owner);

		LocalizedProperty lp = new LocalizedProperty();
		lp.setLocale("en");
		lp.setProperty(p);

		return lp;
	}
}
