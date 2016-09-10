package org.gotocy.service;

import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
@Service
public class PageServiceImpl implements PageService {

	private final PageRepository pageRepository;

	@Autowired
	public PageServiceImpl(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<LocalizedPage> findByUrl(String url, Locale locale) {
		Page page = pageRepository.findByUrl(url);
		return Optional.ofNullable(page)
			.map(p -> p.localize(locale))
			.filter(LocalizedPage::isFullyTranslated);
	}

}
