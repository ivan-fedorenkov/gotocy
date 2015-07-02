package org.gotocy;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.gotocy.helpers.Helper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
	public void entityPathTest() {
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

	@Test
	public void stringPathTest() {
		// Adds a language prefix to the given path
		Assert.assertEquals("/some-path", Helper.path("/some-path", "en"));
		Assert.assertEquals("/ru/some-path", Helper.path("/some-path", "ru"));
	}

	@Test
	public void priceKey() {
		Map<PropertyStatus, String> statusToPriceKey = new HashMap<>();
		statusToPriceKey.put(PropertyStatus.SALE, "org.gotocy.domain.property.sale-price");
		statusToPriceKey.put(PropertyStatus.SHORT_TERM, "org.gotocy.domain.property.short-term-price");
		statusToPriceKey.put(PropertyStatus.LONG_TERM, "org.gotocy.domain.property.long-term-price");

		Property p = new Property();
		for (PropertyStatus status : PropertyStatus.values()) {
			p.setPropertyStatus(status);
			Assert.assertEquals(statusToPriceKey.get(status), Helper.priceKey(p));
		}
	}

	@Test
	public void newLinesToParagraphs() {
		String initialText = "First line\nSecond line\nThird line";
		String expectedResult = "<p>First line</p><p>Second line</p><p>Third line</p>";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "Single line";
		expectedResult = "<p>Single line</p>";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "";
		expectedResult = "";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "\n";
		expectedResult = "";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "\n\nA line after the extra new lines at the beginning";
		expectedResult = "<p>A line after the extra new lines at the beginning</p>";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "A line before the extra new lines at the end\n\n";
		expectedResult = "<p>A line before the extra new lines at the end</p>";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));

		initialText = "\nThe\nTest\n\n";
		expectedResult = "<p>The</p><p>Test</p>";

		Assert.assertEquals(expectedResult, Helper.newLinesToParagraphs(initialText));
	}

	@Test
	public void pluralize() {
		String initialCode = "org.gotocy.some-code";
		Assert.assertEquals(initialCode, Helper.pluralize(initialCode, 1));
		Assert.assertEquals(initialCode + ".plural", Helper.pluralize(initialCode, 2));
	}

}
