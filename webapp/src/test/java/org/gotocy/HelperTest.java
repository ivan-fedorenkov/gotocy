package org.gotocy;

import org.gotocy.config.Locales;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Developer;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.helpers.Helper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author ifedorenkov
 */
public class HelperTest {

	@Test
	public void entityPathTest() {
		Property property = new Property();
		property.setId(1L);
		property.setOfferStatus(OfferStatus.ACTIVE);

		Complex complex = new Complex();
		complex.setId(2L);

		Developer developer = new Developer();
		developer.setId(3L);

		Property promoProperty = new Property();
		promoProperty.setId(4L);
		promoProperty.setOfferStatus(OfferStatus.PROMO);

		// Language is not specified

		LocaleContextHolder.setLocale(Locales.DEFAULT);
		Assert.assertEquals("/properties/1", Helper.path(property));
		Assert.assertEquals("/complexes/2", Helper.path(complex));
		Assert.assertEquals("/developers/3", Helper.path(developer));
		Assert.assertEquals("/promo/properties/4", Helper.path(promoProperty));

		LocaleContextHolder.setLocale(Locales.RU);
		Assert.assertEquals("/ru/properties/1", Helper.path(property));
		Assert.assertEquals("/ru/complexes/2", Helper.path(complex));
		Assert.assertEquals("/ru/developers/3", Helper.path(developer));
		Assert.assertEquals("/ru/promo/properties/4", Helper.path(promoProperty));

		// Language is specified explicitly

		// Default language, no prefix

		Assert.assertEquals("/properties/1", Helper.path(property, "en"));
		Assert.assertEquals("/complexes/2", Helper.path(complex, "en"));
		Assert.assertEquals("/developers/3", Helper.path(developer, "en"));
		Assert.assertEquals("/promo/properties/4", Helper.path(promoProperty, "en"));
		// Russian language prefix
		Assert.assertEquals("/ru/properties/1", Helper.path(property, "ru"));
		Assert.assertEquals("/ru/complexes/2", Helper.path(complex, "ru"));
		Assert.assertEquals("/ru/developers/3", Helper.path(developer, "ru"));
		Assert.assertEquals("/ru/promo/properties/4", Helper.path(promoProperty, "ru"));
	}

	@Test
	public void stringPathTest() {
		// Language is not specified
		LocaleContextHolder.setLocale(Locales.DEFAULT);
		Assert.assertEquals("/some-path", Helper.path("/some-path"));

		LocaleContextHolder.setLocale(Locales.RU);
		Assert.assertEquals("/ru/some-path", Helper.path("/some-path"));

		// Language is specified explicitly
		Assert.assertEquals("/some-path", Helper.path("/some-path", "en"));
		Assert.assertEquals("/ru/some-path", Helper.path("/some-path", "ru"));
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
