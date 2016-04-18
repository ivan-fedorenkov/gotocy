package org.gotocy.controllers.master;

import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Page;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;

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
		pages.forEach(page -> page.initLocalizedFields(locale));
		model.addAttribute("pages", pages);
		return "master/page/index";
	}

	@RequestMapping(value = "/{url:[\\w\\d_-]+}", method = RequestMethod.GET)
	public String show(Model model, @PathVariable String url, Locale locale) {
		Page page = pageRepository.findByUrl(url);
		if (page == null)
			throw new DomainObjectNotFoundException();
		page.initLocalizedFields(locale);
		model.addAttribute(page);
		return "page/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(Model model) {
		model.addAttribute(new Page());
		return "master/page/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute Page page, Locale locale) {
		page.initLocalizedFieldsFromTransients(locale);
		pageRepository.save(page);
		return "redirect:" + UriComponentsBuilder.fromPath("/master/pages/{url}").buildAndExpand(page.getUrl());
	}

	@RequestMapping(value = "/{url:[\\w\\d_-]+}/edit", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable String url, Locale locale) {
		Page page = pageRepository.findByUrl(url);
		if (page == null)
			throw new DomainObjectNotFoundException();
		page.initLocalizedFields(locale);
		model.addAttribute(page);
		return "master/page/edit";
	}

	@RequestMapping(value = "/{url:[\\w\\d_-]+}", method = RequestMethod.PUT)
	public String update(@ModelAttribute Page page, @PathVariable String url, Locale locale) {
		Page originalPage = pageRepository.findByUrl(url);
		if (originalPage == null)
			throw new DomainObjectNotFoundException();
		originalPage.setTitle(page.getTitle());
		originalPage.setHtml(page.getHtml());
		originalPage.setUrl(page.getUrl());
		originalPage.setVisible(page.isVisible());
		originalPage.initLocalizedFieldsFromTransients(locale);
		pageRepository.save(originalPage);
		return "redirect:" + UriComponentsBuilder.fromPath("/master/pages/{url}").buildAndExpand(originalPage.getUrl());
	}

}
