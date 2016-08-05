package org.gotocy.controllers.master;

import org.gotocy.config.Paths;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Developer;
import org.gotocy.forms.master.ComplexForm;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.ComplexRepository;
import org.gotocy.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping(value = "/master/complexes")
public class MasterComplexesController {

	private final ComplexRepository complexRepository;
	private final DeveloperRepository developerRepository;

	@Autowired
	public MasterComplexesController(ComplexRepository complexRepository, DeveloperRepository developerRepository) {
		this.complexRepository = complexRepository;
		this.developerRepository = developerRepository;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute(new ComplexForm());
		return "master/complex/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public String create(ComplexForm complexForm) {
		Complex complex = complexForm.mergeWithComplex(new Complex());
		complex.setDeveloper(getDeveloper(complexForm.getDeveloperId()));
		complex = complexRepository.save(complex);
		return "redirect:" + Helper.editPath(Paths.MASTER, complex);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@RequiredDomainObject @PathVariable("id") Complex complex, Model model) {
		model.addAttribute(complex);
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute(new ComplexForm(complex));
		return "master/complex/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public String update(@RequiredDomainObject @PathVariable("id") Complex complex, ComplexForm complexForm) {
		complex = complexForm.mergeWithComplex(complex);
		complex.setDeveloper(getDeveloper(complexForm.getDeveloperId()));
		complex = complexRepository.save(complex);
		return "redirect:" + Helper.editPath(Paths.MASTER, complex);
	}

	private Developer getDeveloper(Long developerId) {
		return developerId != null ? developerRepository.findOne(developerId) : null;
	}

}
