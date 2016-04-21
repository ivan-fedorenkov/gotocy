package org.gotocy.controllers.master;

import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.forms.master.PageForm;
import org.gotocy.helpers.Helper;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@Autowired
	public MasterPagesController(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
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
	public String create(@ModelAttribute PageForm pageForm, Locale locale) {
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
	public String update(@ModelAttribute PageForm pageForm, @PathVariable Long id, Locale locale) {
		Page page = pageRepository.findOne(id);
		if (page == null)
			throw new DomainObjectNotFoundException();
		page = pageForm.mergeWithPage(page, locale);
		pageRepository.save(page);
		return "redirect:" + Helper.path("/master/pages/" + page.getId());
	}

}
