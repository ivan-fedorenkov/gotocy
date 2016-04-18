package org.gotocy.controllers;

import org.gotocy.controllers.exceptions.AccessDeniedException;
import org.gotocy.controllers.exceptions.DomainObjectNotFoundException;
import org.gotocy.domain.Page;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String show(Model model, @PathVariable String url) {
		Page page = pageRepository.findByUrl(url);

		if (page == null)
			throw new DomainObjectNotFoundException();
		if (!page.isVisible())
			throw new AccessDeniedException();

		model.addAttribute(page);
		return "page/show";
	}

}
