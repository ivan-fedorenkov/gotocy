package org.gotocy.service;

import freemarker.template.Configuration;
import org.gotocy.config.Locales;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.repository.PageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author ifedorenkov
 */
public class TemplatesServiceTest {

	private TemplatesService templatesService;
	private PageRepository pageRepository;

	@Before
	public void setUp() {
		Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_25);
		pageRepository = mock(PageRepository.class);
		templatesService = new TemplatesServiceImpl(pageRepository, freemarkerConfiguration);
	}

	@Test
	public void testTemplatesProcessing() {
		Page templatePage = new Page();
		String templatePageUrl = "template-under-test";

		LocalizedPage enTemplatePage = templatePage.localize(Locales.DEFAULT);
		String templatePageTitle = "a title!";
		String templatePageHtml = "doesn't matter";
		enTemplatePage.setUrl(templatePageUrl);
		enTemplatePage.setTitle(templatePageTitle);
		enTemplatePage.setHtml(templatePageHtml);

		LocalizedPage ruTemplatePage = templatePage.localize(Locales.RU);
		String ruTemplatePageTitle = "заголовок!";
		String ruTemplatePageHtml = "что угодно";
		ruTemplatePage.setUrl(templatePageUrl);
		ruTemplatePage.setTitle(ruTemplatePageTitle);
		ruTemplatePage.setHtml(ruTemplatePageHtml);

		when(pageRepository.findByUrl(templatePageUrl)).thenReturn(templatePage);
		LocalizedPage processed;

		processed = templatesService.getProcessedTemplate(templatePageUrl, Collections.emptyMap(), Locales.EN);
		Assert.assertEquals(templatePageTitle, processed.getTitle());
		Assert.assertEquals(templatePageHtml, processed.getHtml());

		processed = templatesService.getProcessedTemplate(templatePageUrl, Collections.emptyMap(), Locales.RU);
		Assert.assertEquals(ruTemplatePageTitle, processed.getTitle());
		Assert.assertEquals(ruTemplatePageHtml, processed.getHtml());

		processed = templatesService.getProcessedTemplate(templatePageUrl, Collections.emptyMap(), Locales.EL);
		Assert.assertEquals(templatePageTitle, processed.getTitle());
		Assert.assertEquals(templatePageHtml, processed.getHtml());
	}

}
