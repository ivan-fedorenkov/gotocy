package org.gotocy;

import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class HelperTest {

	@Test
	public void price() {
		Assert.assertEquals("$ 1", Helper.price(1));
		Assert.assertEquals("$ 100", Helper.price(100));
		Assert.assertEquals("$ 100,000", Helper.price(100000));
		Assert.assertEquals("$ 100,000,000", Helper.price(100000000));
	}

	@Test
	public void url() {
		Property p = new Property();
		p.setId(1L);
		LocalizedProperty lp = new LocalizedProperty();
		lp.setProperty(p);

		// Language is not specified

		// Just an ordinary path
		Assert.assertEquals("/property/1", Helper.path(p));

		// Path of LocalizedProperty uses id of parent Property object, adding the language prefix (like localized path
		// on a Property object).
		lp.setLocale("en");
		Assert.assertEquals("/property/1", Helper.path(lp));
		lp.setLocale("ru");
		Assert.assertEquals("/ru/property/1", Helper.path(lp));

		// Language is specified explicitly

		// Default language, no prefix
		Assert.assertEquals("/property/1", Helper.path(p, "en"));
		// Russian language prefix
		Assert.assertEquals("/ru/property/1", Helper.path(p, "ru"));

		// The given language overrides LocalizedProperty's locale language
		lp.setLocale("ru");
		Assert.assertEquals("/property/1", Helper.path(lp, "en"));
		lp.setLocale("en");
		Assert.assertEquals("/ru/property/1", Helper.path(lp, "ru"));

	}


}
