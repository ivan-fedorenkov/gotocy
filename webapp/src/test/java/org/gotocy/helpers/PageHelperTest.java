package org.gotocy.helpers;

import org.gotocy.config.Locales;
import org.gotocy.domain.Page;
import org.gotocy.helpers.page.PageHelper;
import org.gotocy.test.factory.PageFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class PageHelperTest {

	@Test
	public void pathTest() {
		String enPath = "some-path";
		String ruPath = "kakoi-to-put";

		Page page = PageFactory.INSTANCE.get(p -> {
			p.setUrl(enPath, Locales.EN);
			p.setUrl(ruPath, Locales.RU);
		});

		Assert.assertEquals("/" + enPath, PageHelper.path(page, Locales.EN));
		Assert.assertEquals("/" + ruPath, PageHelper.path(page, Locales.RU));
		Assert.assertEquals("/" + enPath, PageHelper.path(page, Locales.EL));
	}

}
