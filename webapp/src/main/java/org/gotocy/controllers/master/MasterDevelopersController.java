package org.gotocy.controllers.master;

import org.gotocy.config.Paths;
import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Developer;
import org.gotocy.forms.master.DeveloperForm;
import org.gotocy.helpers.Helper;
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
@RequestMapping(value = "/master/developers")
public class MasterDevelopersController {

	private final DeveloperRepository developerRepository;

	@Autowired
	public MasterDevelopersController(DeveloperRepository developerRepository) {
		this.developerRepository = developerRepository;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute(new DeveloperForm());
		return "master/developer/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	public String create(DeveloperForm developerForm) {
		Developer developer = developerRepository.save(developerForm.mergeWithDeveloper(new Developer()));
		return "redirect:" + Helper.editPath(Paths.MASTER, developer);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@RequiredDomainObject @PathVariable("id") Developer developer, Model model) {
		model.addAttribute(developer);
		model.addAttribute(new DeveloperForm(developer));
		return "master/developer/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public String update(@RequiredDomainObject @PathVariable("id") Developer developer, DeveloperForm developerForm) {
		developer = developerRepository.save(developerForm.mergeWithDeveloper(developer));
		return "redirect:" + Helper.editPath(Paths.MASTER, developer);
	}

}
