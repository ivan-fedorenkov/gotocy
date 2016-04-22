package org.gotocy.helpers;

import org.gotocy.config.Locales;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;
import org.gotocy.helpers.page.PageHelper;
import org.gotocy.repository.PageRepository;
import org.gotocy.test.factory.PageFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Locale;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public class PageHelperTest {

	@Test
	public void getTest() {
		// Existing page
		LocalizedPage localizedPage = PageFactory.INSTANCE.getLocalized(Locales.EN, lp -> {});

		// Non existing page
		String nonExistingPageUrl = "non-existing-page-url";

		PageRepository pageRepository = Mockito.mock(PageRepository.class);
		Mockito.when(pageRepository.findByUrl(localizedPage.getUrl())).thenReturn(localizedPage.getOriginal());
		Mockito.when(pageRepository.findByUrl(nonExistingPageUrl)).thenReturn(null);

		PageHelper pageHelper = new PageHelper(pageRepository);
		Assert.assertEquals(localizedPage.getHtml(), pageHelper.get(localizedPage.getUrl(), Locales.EN));
		Assert.assertEquals("", pageHelper.get(localizedPage.getUrl(), Locales.RU));
		Assert.assertEquals("", pageHelper.get(nonExistingPageUrl, Locales.DEFAULT));
	}

	@Test
	public void pathTest() {
		Page page = PageFactory.INSTANCE.get();

		String enPath = "/some-path";
		LocalizedPage enPage = PageFactory.INSTANCE.getLocalized(page, Locales.EN, lp -> lp.setUrl(enPath.substring(1)));
		String ruPath = "/kakoi-to-put";
		LocalizedPage ruPage = PageFactory.INSTANCE.getLocalized(page, Locales.RU, lp -> lp.setUrl(ruPath.substring(1)));

		Assert.assertEquals(enPath, PageHelper.path(page, Locales.EN).get());
		Assert.assertEquals(enPath, PageHelper.path(enPage).get());
		Assert.assertEquals(ruPath, PageHelper.path(enPage, Locales.RU).get());

		Assert.assertEquals(ruPath, PageHelper.path(page, Locales.RU).get());
		Assert.assertEquals(ruPath, PageHelper.path(ruPage).get());
		Assert.assertEquals(enPath, PageHelper.path(ruPage, Locales.EN).get());

		Assert.assertEquals(Optional.empty(), PageHelper.path(enPage, Locales.EL));
		Assert.assertEquals(Optional.empty(), PageHelper.path(ruPage, Locales.EL));
		Assert.assertEquals(Optional.empty(), PageHelper.path(page, Locales.EL));
	}

	@Test
	public void supportedLocalesTest() {
		Page page = PageFactory.INSTANCE.get(Locales.EN, Locales.RU);
		Assert.assertArrayEquals(new Locale[] {Locales.EN, Locales.RU}, PageHelper.supportedLocales(page).toArray());
	}

}
