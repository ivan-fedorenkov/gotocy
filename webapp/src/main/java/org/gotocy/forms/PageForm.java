package org.gotocy.forms;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Locale;

/**
 * @author ifedorenkov
 */
@Getter
@Setter
public class PageForm {

	private Long id;
	private String url;
	private String title;
	private String html;
	private boolean visible;

	public PageForm() {
	}

	public PageForm(Page page, Locale locale) {
		LocalizedPage localizedPage = page.localize(locale);
		id = localizedPage.getId();
		url = localizedPage.getUrl();
		title = localizedPage.getTitle();
		html = localizedPage.getHtml();
		visible = localizedPage.isVisible();
	}

	public Page mergeWithPage(Page page, Locale locale) {
		LocalizedPage localizedPage = page.localize(locale);
		localizedPage.setUrl(url);
		localizedPage.setTitle(title);
		localizedPage.setHtml(html);
		page.setVisible(visible);
		return page;
	}

}
