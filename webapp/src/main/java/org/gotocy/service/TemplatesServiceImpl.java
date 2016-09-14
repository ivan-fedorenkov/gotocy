package org.gotocy.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.gotocy.config.Locales;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

/**
 * @author ifedorenkov
 */
@Service
public class TemplatesServiceImpl implements TemplatesService {
	private static final Logger logger = LoggerFactory.getLogger(TemplatesServiceImpl.class);

	private final PageRepository pageRepository;
	private final Configuration freemarkerConfig;

	@Autowired
	public TemplatesServiceImpl(PageRepository pageRepository, Configuration freemarkerConfig) {
		this.pageRepository = pageRepository;
		this.freemarkerConfig = freemarkerConfig;
	}

	@Override
	public LocalizedPage getProcessedTemplate(String templatePageUrl, Map<String, Object> model,
		Locale locale)
	{
		Page templatePage = pageRepository.findByUrl(templatePageUrl);
		if (templatePage == null)
			return missingTemplate(templatePageUrl);

		LocalizedPage localizedTemplatePage = templatePage.localize(locale);
		if (!localizedTemplatePage.isFullyTranslated()) {
			logger.info("Failed to load template '{}' in the required locale '{}'. " +
				"Will try to load in the default locale", templatePageUrl, locale);
			localizedTemplatePage = templatePage.localize(Locales.DEFAULT);
		}

		if (!localizedTemplatePage.isFullyTranslated()) {
			logger.warn("Failed to load template '{}' even in the default locale.", templatePageUrl);
			return missingTemplate(templatePageUrl);
		}

		return processTemplatePage(localizedTemplatePage, model);
	}

	private LocalizedPage processTemplatePage(LocalizedPage templatePage, Map<String, Object> model) {
		try (StringReader templateReader = new StringReader(templatePage.getHtml())) {
			templatePage.setHtml(FreeMarkerTemplateUtils.processTemplateIntoString(
				new Template(templatePage.getTitle(), templateReader, freemarkerConfig), model));
			return templatePage;
		} catch (IOException | TemplateException ioe) {
			logger.error("Failed to process template page '{}'", templatePage.getUrl(), ioe);
			throw new ServiceMethodExecutionException(ioe);
		}
	}

	private static LocalizedPage missingTemplate(String templatePageUrl) {
		LocalizedPage missing = new Page().localize(Locales.DEFAULT);
		missing.setUrl(templatePageUrl);
		missing.setTitle("Missing template '" + templatePageUrl + "'");
		missing.setHtml("Missing template '" + templatePageUrl + "'");
		return missing;
	}

}
