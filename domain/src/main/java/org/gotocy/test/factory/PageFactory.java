package org.gotocy.test.factory;

import org.gotocy.domain.Page;

/**
 * @author ifedorenkov
 */
public class PageFactory extends BaseFactory<Page> {

	public static final PageFactory INSTANCE = new PageFactory();

	private PageFactory() {
	}

	@Override
	public Page get() {
		Page page = new Page();
		page.setTitle(ANY_STRING);
		page.setHtml(ANY_STRING);
		page.setUrl(ANY_STRING);
		return page;
	}

}
