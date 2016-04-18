package org.gotocy.domain.i18n;

import org.gotocy.domain.Page;
import org.gotocy.test.factory.PageFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class PageLocalizedFieldsManagerTest {

	public static final String URL_KEY = "url";
	private static final String TITLE_KEY = "title";
	private static final String HTML_KEY = "html";

	@Test
	public void setFieldsTest() {
		Page page = PageFactory.INSTANCE.get();

		String url = "some-url";
		String title = "some title";
		String html = "some html";
		List<LocalizedField> localizedFields = new ArrayList<>();
		localizedFields.add(new LocalizedStringField(URL_KEY, url, Locale.ENGLISH.getLanguage()));
		localizedFields.add(new LocalizedStringField(TITLE_KEY, title, Locale.ENGLISH.getLanguage()));
		localizedFields.add(new LocalizedStringField(HTML_KEY, html, Locale.ENGLISH.getLanguage()));
		page.setLocalizedFields(localizedFields);

		PageLocalizedFieldsManager manager = new PageLocalizedFieldsManager(page);
		manager.setFields(Locale.ENGLISH);

		Assert.assertEquals(url, page.getUrl());
		Assert.assertEquals(title, page.getTitle());
		Assert.assertEquals(html, page.getHtml());
	}

	/**
	 * The manager should fall back to the {@link Locale#ENGLISH} when there is no translation for the given locale.
	 */
	@Test
	public void setFieldsFallbackTest() {
		Page page = PageFactory.INSTANCE.get();

		String url = "some-url";
		String title = "some title";
		String html = "some html";
		List<LocalizedField> localizedFields = new ArrayList<>();
		localizedFields.add(new LocalizedStringField(URL_KEY, url, Locale.ENGLISH.getLanguage()));
		localizedFields.add(new LocalizedStringField(TITLE_KEY, title, Locale.ENGLISH.getLanguage()));
		localizedFields.add(new LocalizedStringField(HTML_KEY, html, Locale.ENGLISH.getLanguage()));
		page.setLocalizedFields(localizedFields);

		PageLocalizedFieldsManager manager = new PageLocalizedFieldsManager(page);
		manager.setFields(new Locale("ru"));

		Assert.assertEquals(url, page.getUrl());
		Assert.assertEquals(title, page.getTitle());
		Assert.assertEquals(html, page.getHtml());
	}

}
