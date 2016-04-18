package org.gotocy.domain.i18n;

import org.gotocy.domain.Page;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author ifedorenkov
 */
public class PageLocalizedFieldsManager extends LocalizedFieldsManager {

	private static final String URL_KEY = "url";
	private static final String TITLE_KEY = "title";
	private static final String HTML_KEY = "html";

	private final Page page;

	public PageLocalizedFieldsManager(Page page) {
		this.page = page;
	}

	@Override
	public void setFields(Locale locale) {
		page.setUrl(getFieldValue(URL_KEY, locale).orElse(getFieldValue(URL_KEY, Locale.ENGLISH).orElse("")));
		page.setTitle(getFieldValue(TITLE_KEY, locale).orElse(getFieldValue(TITLE_KEY, Locale.ENGLISH).orElse("")));
		page.setHtml(getFieldValue(HTML_KEY, locale).orElse(getFieldValue(HTML_KEY, Locale.ENGLISH).orElse("")));
	}

	@Override
	public List<LocalizedField> getFields() {
		return page.getLocalizedFields();
	}

	public void setUrl(String url, Locale locale) {
		updateStringField(URL_KEY, url, locale);
	}

	public Optional<String> getUrl(Locale locale) {
		return getFieldValue(URL_KEY, locale);
	}

	public void setTitle(String title, Locale locale) {
		updateStringField(TITLE_KEY, title, locale);
	}

	public Optional<String> getTitle(Locale locale) {
		return getFieldValue(TITLE_KEY, locale);
	}

	public void setHtml(String html, Locale locale) {
		updateTextField(HTML_KEY, html, locale);
	}

	public Optional<String> getHtml(Locale locale) {
		return getFieldValue(HTML_KEY, locale);
	}

}
