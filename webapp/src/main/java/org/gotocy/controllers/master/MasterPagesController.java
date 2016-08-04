package org.gotocy.controllers.master;

import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.forms.master.PageForm;
import org.gotocy.forms.validation.master.PageFormValidator;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * @author ifedorenkov
 */
@Controller
@RequestMapping("/master/pages")
public class MasterPagesController {

	private final PageRepository pageRepository;
	private final PageFormValidator pageFormValidator;

	@Autowired
	public MasterPagesController(PageRepository pageRepository, PageFormValidator pageFormValidator) {
		this.pageRepository = pageRepository;
		this.pageFormValidator = pageFormValidator;
	}

	@InitBinder("pageForm")
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && pageFormValidator.supports(binder.getTarget().getClass()))
			binder.addValidators(pageFormValidator);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model, Locale locale) {
		List<Page> pages = pageRepository.findAll();
		List<LocalizedPage> localizedPages = pages.stream()
			.map(p -> p.localize(locale))
			.filter(LocalizedPage::isFullyTranslated)
			.collect(toList());
		model.addAttribute("pages", localizedPages);
		return "master/page/index";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(Model model, @PathVariable Long id, Locale locale) {
		Page page = pageRepository.findOne(id);
		if (page == null)
			throw new DomainObjectNotFoundException();
		model.addAttribute("page", page.localize(locale));
		return "master/page/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute(new PageForm());
		return "master/page/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute PageForm pageForm, BindingResult pageFormErrors, Locale locale) {
		if (pageFormErrors.hasErrors())
			return "master/page/new";
		Page page = pageForm.mergeWithPage(new Page(), locale);
		pageRepository.save(page);
		return "redirect:" + Helper.path("/master/pages/" + page.getId());
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable Long id, Locale locale) {
		Page page = pageRepository.findOne(id);
		if (page == null)
			throw new DomainObjectNotFoundException();
		model.addAttribute(new PageForm(page, locale));
		model.addAttribute("page", page.localize(locale));
		return "master/page/edit";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(Model model, @Validated @ModelAttribute PageForm pageForm, BindingResult pageFormErrors,
		@PathVariable Long id, Locale locale)
	{
		Page page = pageRepository.findOne(id);
		if (page == null)
			throw new DomainObjectNotFoundException();

		if (pageFormErrors.hasErrors()) {
			model.addAttribute("page", page.localize(locale));
			return "master/page/edit";
		}

		page = pageForm.mergeWithPage(page, locale);
		pageRepository.save(page);
		return "redirect:" + Helper.path("/master/pages/" + page.getId());
	}

}
