package org.gotocy.domain.i18n;

import lombok.Getter;
import org.gotocy.domain.Page;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class LocalizedPage extends LocalizedEntity<Page, LocalizedPage> {

	private static final String URL_KEY = "url";
	private static final String TITLE_KEY = "title";
	private static final String HTML_KEY = "html";

	@Getter
	private String url;
	@Getter
	private String title;
	@Getter
	private String html;

	public LocalizedPage(Page page, Locale locale) {
		super(page, locale);

		url = getFieldValue(URL_KEY).orElse("");
		title = getFieldValue(TITLE_KEY).orElse("");
		html = getFieldValue(HTML_KEY).orElse("");
	}

	public void setUrl(String url) {
		this.url = url;
		updateStringField(URL_KEY, url);
	}

	public void setTitle(String title) {
		this.title = title;
		updateStringField(TITLE_KEY, title);
	}

	public void setHtml(String html) {
		this.html = html;
		updateTextField(HTML_KEY, html);
	}

	// Delegate to original page

	public long getId() {
		return getOriginal().getId();
	}

	public boolean isVisible() {
		return getOriginal().isVisible();
	}

	/**
	 * Tests if this page is fully translated in the given {@link Locale}.
	 */
	public boolean isFullyTranslated() {
		return !url.isEmpty() && !title.isEmpty() && !html.isEmpty();
	}

}
