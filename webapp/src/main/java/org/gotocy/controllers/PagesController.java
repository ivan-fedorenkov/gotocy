package org.gotocy.controllers;

import org.gotocy.controllers.exceptions.AccessDeniedException;
import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Controller
public class PagesController {

	private final PageRepository pageRepository;

	@Autowired
	public PagesController(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@RequestMapping(value = "/{url:[\\d\\w_-]+}")
	public String show(Model model, @PathVariable String url, Locale locale) {
		Page page = pageRepository.findByUrl(url);

		if (page == null)
			throw new DomainObjectNotFoundException();
		if (!page.isVisible())
			throw new AccessDeniedException();

		LocalizedPage localizedPage = page.localize(locale);

		if (!localizedPage.isFullyTranslated())
			throw new DomainObjectNotFoundException();

		// Requested url was found but in different locale, redirect to the appropriate url
		if (!localizedPage.getUrl().equals(url))
			return "redirect:/" + localizedPage.getUrl();

		model.addAttribute("page", localizedPage);
		return "page/show";
	}

}
