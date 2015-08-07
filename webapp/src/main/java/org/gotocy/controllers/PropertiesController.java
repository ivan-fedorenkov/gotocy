package org.gotocy.controllers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.beans.PropertyFormFactory;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.*;
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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author ifedorenkov
 */
@Controller
public class PropertiesController {

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private PanoXmlRepository panoXmlRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private LocalizedPropertyRepository repository;
	@Autowired
	private PropertyFormFactory propertyFormFactory;
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
	public String edit(Model model) {
		model.addAttribute("owners", ownerRepository.findAll());
		model.addAttribute(propertyFormFactory.create());
		return "master/property/new";
	}

	@RequestMapping(value = "/master/properties", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Iterable<LocalizedProperty> create(PropertyForm propertyForm) {
		Owner existingOwner = getOrCreateOwner(propertyForm);
		Owner owner = mergeOwner(existingOwner, propertyForm.getOwner());

		List<Image> images = propertyForm.getImageSet().getImages();
		if (!images.isEmpty())
			images = imageRepository.save(images);

		Image ri = propertyForm.getImageSet().getRepresentativeImage();
		if (ri != null && ri.getKey() != null && !ri.getKey().isEmpty()) {
			ri = imageRepository.saveAndFlush(ri);
		} else {
			ri = null;
		}


		PanoXml panoXml = propertyForm.getPanoXml();
		if (panoXml != null && panoXml.getKey() != null && !panoXml.getKey().isEmpty()) {
			panoXml = panoXmlRepository.saveAndFlush(panoXml);
		} else {
			panoXml = null;
		}

		Property property = propertyForm.getPropertyDelegate();
		property.setOwner(owner);
		property.getImageSet().setImages(images);
		property.getImageSet().setRepresentativeImage(ri);
		property.setPanoXml(panoXml);
		property = propertyRepository.saveAndFlush(property);

		propertyForm.getEnLocalizedProperty().setProperty(property);
		propertyForm.getRuLocalizedProperty().setProperty(property);
		LocalizedProperty enLP = repository.saveAndFlush(propertyForm.getEnLocalizedProperty());
		LocalizedProperty ruLP = repository.saveAndFlush(propertyForm.getRuLocalizedProperty());

		return Arrays.asList(enLP, ruLP);
	}

	@RequestMapping(value = "/master/property/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable("id") Long id) throws NoSuchRequestHandlingMethodException {
		LocalizedProperty enLP = repository.findProperty(id, "en");
		LocalizedProperty ruLP = repository.findProperty(id, "ru");

		// TODO: replace with custom exception
		if (enLP == null || ruLP == null)
			throw new NoSuchRequestHandlingMethodException("get", PropertiesController.class);

		model.addAttribute(propertyFormFactory.create(enLP, ruLP));
		model.addAttribute("owners", ownerRepository.findAll());

		return "master/property/edit";
	}


	@RequestMapping(value = "/master/property/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public List<LocalizedProperty> update(@PathVariable("id") Property property, PropertyForm propertyForm) {
		Owner existingOwner = getOrCreateOwner(propertyForm);
		Owner owner = mergeOwner(existingOwner, propertyForm.getOwner());

		List<Image> existingImages = property.getImageSet().getImages();
		List<Image> images = propertyForm.getImageSet().getImages();
		images = !existingImages.equals(images) ? imageRepository.save(images) : existingImages;

		Image existingRi = property.getImageSet().getRepresentativeImage();
		Image ri = propertyForm.getImageSet().getRepresentativeImage();
		ri = !Objects.equals(existingRi, ri) ? imageRepository.saveAndFlush(ri) : existingRi;

		PanoXml existingPanoXml = property.getPanoXml();
		PanoXml panoXml = propertyForm.getPanoXml();
		panoXml = !Objects.equals(existingPanoXml, panoXml) ? panoXmlRepository.saveAndFlush(panoXml) : existingPanoXml;

		property.setOwner(owner);
		property.getImageSet().setImages(images);
		property.getImageSet().setRepresentativeImage(ri);
		property.setPanoXml(panoXml);
		property.setLocation(propertyForm.getLocation());
		property.setTitle(propertyForm.getTitle());
		property.setAddress(propertyForm.getFullAddress());
		property.setShortAddress(propertyForm.getShortAddress());
		property.setLatitude(propertyForm.getLatitude());
		property.setLongitude(propertyForm.getLongitude());
		property.setPropertyType(propertyForm.getPropertyType());
		property.setPropertyStatus(propertyForm.getPropertyStatus());
		property.setOfferStatus(propertyForm.getOfferStatus());
		property.setPrice(propertyForm.getPrice());
		property.setCoveredArea(propertyForm.getCoveredArea());
		property.setPlotSize(propertyForm.getPlotSize());
		property.setBedrooms(propertyForm.getBedrooms());
		property.setGuests(propertyForm.getGuests());
		property.setDistanceToSea(propertyForm.getDistanceToSea());
		property.setAirConditioner(propertyForm.getAirConditioner());
		property.setReadyToMoveIn(propertyForm.getReadyToMoveIn());
		property.setHeatingSystem(propertyForm.getHeatingSystem());
		property.setFurnishing(propertyForm.getFurnishing());
		property = propertyRepository.saveAndFlush(property);

		LocalizedProperty enLP = repository.findProperty(property.getId(), "en");
		if (enLP == null)
			enLP = propertyForm.getEnLocalizedProperty();
		enLP.setProperty(property);
		enLP.setDescription(propertyForm.getEnDescription());
		enLP.setSpecifications(propertyForm.getEnLocalizedProperty().getSpecifications());

		LocalizedProperty ruLP = repository.findProperty(property.getId(), "ru");
		if (ruLP == null)
			ruLP = propertyForm.getRuLocalizedProperty();
		ruLP.setProperty(property);
		ruLP.setDescription(propertyForm.getRuDescription());
		ruLP.setSpecifications(propertyForm.getRuLocalizedProperty().getSpecifications());

		enLP = repository.saveAndFlush(enLP);
		ruLP = repository.saveAndFlush(ruLP);

		return Arrays.asList(enLP, ruLP);
	}

	private Owner getOrCreateOwner(PropertyForm form) {
		Owner formOwner = form.getOwner();
		return formOwner.getId() != null && formOwner.getId() != 0 ?
			ownerRepository.findOne(formOwner.getId()) : ownerRepository.saveAndFlush(new Owner());
	}

	private Owner mergeOwner(Owner existing, Owner current) {
		if (!existing.equals(current)) {
			existing.setName(current.getName());
			existing.setPhone(current.getPhone());
			existing.setEmail(current.getEmail());
			existing.setSpokenLanguages(current.getSpokenLanguages());
		}
		return existing;
	}

}
