package org.gotocy.controllers.master;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.config.Paths;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Developer;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.dto.PropertyDto;
import org.gotocy.dto.PropertyDtoFactory;
import org.gotocy.forms.master.PropertyForm;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.ComplexRepository;
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

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
@Controller
public class MasterPropertiesController {

	private final ApplicationProperties applicationProperties;

	private final PropertyRepository propertyRepository;
	private final DeveloperRepository developerRepository;
	private final ComplexRepository complexRepository;
	private final PropertyDtoFactory propertyDtoFactory;

	@Autowired
	public MasterPropertiesController(ApplicationProperties applicationProperties,
		PropertyRepository propertyRepository, DeveloperRepository developerRepository,
		ComplexRepository complexRepository, PropertyDtoFactory propertyDtoFactory)
	{
		this.applicationProperties = applicationProperties;
		this.propertyRepository = propertyRepository;
		this.developerRepository = developerRepository;
		this.complexRepository = complexRepository;
		this.propertyDtoFactory = propertyDtoFactory;
	}

	@RequestMapping(value = "/master/properties.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<PropertyDto> indexJson() {
		return propertyRepository.findAll().stream()
			.filter(p -> p.getOfferStatus() == OfferStatus.ACTIVE)
			.filter(p -> p.getPrice() > 0)
			.map(propertyDtoFactory::create)
			.collect(toList());
	}

	@RequestMapping(value = "/master/properties/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());

		PropertyForm propertyForm = new PropertyForm();
		propertyForm.setLatitude(applicationProperties.getDefaultLatitude());
		propertyForm.setLongitude(applicationProperties.getDefaultLongitude());
		model.addAttribute(propertyForm);

		return "master/property/new";
	}

	@RequestMapping(value = "/master/properties", method = RequestMethod.POST)
	@Transactional
	public String create(PropertyForm propertyForm) {
		Property property = propertyForm.mergeWithProperty(new Property());
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		property = propertyRepository.save(property);
		return "redirect:" + Helper.editPath(Paths.MASTER, property);
	}

	@RequestMapping(value = "/master/properties/{id}/edit", method = RequestMethod.GET)
	public String edit(@RequiredDomainObject @PathVariable("id") Property property, Model model) {
		model.addAttribute(property);
		model.addAttribute(new PropertyForm(property));
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("complexes", complexRepository.findAll());
		return "master/property/edit";
	}


	@RequestMapping(value = "/master/properties/{id}", method = RequestMethod.PUT)
	@Transactional
	public String update(@RequiredDomainObject @PathVariable("id") Property property, PropertyForm propertyForm) {
		property = propertyForm.mergeWithProperty(property);
		property.setComplex(getComplex(propertyForm.getComplexId()));
		property.setDeveloper(getDeveloper(propertyForm.getDeveloperId()));
		property = propertyRepository.save(property);
		return "redirect:" + Helper.editPath(Paths.MASTER, property);
	}

	private Complex getComplex(long complexId) {
		return complexId > 0 ? complexRepository.findOne(complexId) : null;
	}

	private Developer getDeveloper(long developerId) {
		return developerId > 0 ? developerRepository.findOne(developerId) : null;
	}

}
