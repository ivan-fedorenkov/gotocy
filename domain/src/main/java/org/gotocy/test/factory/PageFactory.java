package org.gotocy.test.factory;

import org.gotocy.domain.Page;
import org.gotocy.domain.i18n.LocalizedPage;

import java.util.Locale;
import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
public class PageFactory extends BaseFactory<Page> {

	public static final PageFactory INSTANCE = new PageFactory();

	private PageFactory() {
	}

	@Override
	public Page get() {
		return new Page();
	}

	public Page get(Locale... locales) {
		return get(page -> {}, locales);
	}

	public Page get(Consumer<Page> pagePreparer, Locale... locales) {
		Page page = new Page();
		for (Locale locale : locales)
			getLocalized(page, locale);
		pagePreparer.accept(page);
		return page;
	}

	public LocalizedPage getLocalized(Page page, Locale locale) {
		return getLocalized(page, locale, lp -> {});
	}

	public LocalizedPage getLocalized(Page page, Locale locale, Consumer<LocalizedPage> entityPreparer) {
		LocalizedPage localizedPage = page.localize(locale);
		localizedPage.setUrl(ANY_STRING);
		localizedPage.setTitle(ANY_STRING);
		localizedPage.setHtml(ANY_STRING);
		entityPreparer.accept(localizedPage);
		return localizedPage;
	}

}
