package org.gotocy.controllers;

import org.gotocy.controllers.aop.RequiredDomainObject;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Contact;
import org.gotocy.domain.Developer;
import org.gotocy.forms.ComplexForm;
import org.gotocy.repository.ComplexRepository;
import org.gotocy.repository.ContactRepository;
import org.gotocy.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class ComplexesController {

	@Autowired
	private ComplexRepository complexRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private DeveloperRepository developerRepository;

	@RequestMapping(value = "/complex/{id}", method = RequestMethod.GET)
	public String get(@RequiredDomainObject @PathVariable("id") Complex complex, Model model, Locale locale) {
		complex.initLocalizedFields(locale);
		model.addAttribute(complex);
		return "complex/show";
	}

	@RequestMapping(value = "/master/complex/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());
		model.addAttribute(new ComplexForm());
		return "master/complex/new";
	}

	@RequestMapping(value = "/master/complex", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Complex create(ComplexForm complexForm) {
		Contact contact = getOrCreateContact(complexForm.getContactId());
		contact = complexForm.mergeWithContact(contact);

		Complex complex = complexForm.mergeWithComplex(new Complex());
		complex.setPrimaryContact(contact);
		complex.setDeveloper(getDeveloper(complexForm.getDeveloperId()));
		return complexRepository.saveAndFlush(complex);
	}

	@RequestMapping(value = "/master/complex/{id}/edit", method = RequestMethod.GET)
	public String edit(@RequiredDomainObject @PathVariable("id") Complex complex, Model model) {
		model.addAttribute(complex);
		model.addAttribute("developers", developerRepository.findAll());
		model.addAttribute("contacts", contactRepository.findAll());
		model.addAttribute(new ComplexForm(complex));
		return "master/complex/edit";
	}

	@RequestMapping(value = "/master/complex/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public Complex update(@RequiredDomainObject @PathVariable("id") Complex complex, ComplexForm complexForm) {
		Contact contact = getOrCreateContact(complexForm.getContactId());
		contact = complexForm.mergeWithContact(contact);

		complex = complexForm.mergeWithComplex(complex);
		complex.setPrimaryContact(contact);
		complex.setDeveloper(getDeveloper(complexForm.getDeveloperId()));
		return complexRepository.saveAndFlush(complex);
	}

	private Developer getDeveloper(long developerId) {
		return developerId > 0 ? developerRepository.findOne(developerId) : null;
	}

	private Contact getOrCreateContact(long contactId) {
		return contactId > 0 ? contactRepository.findOne(contactId) : contactRepository.saveAndFlush(new Contact());
	}

}
