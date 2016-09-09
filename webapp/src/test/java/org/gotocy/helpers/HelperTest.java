package org.gotocy.helpers;

import org.gotocy.config.Locales;
import org.gotocy.config.Paths;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Developer;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.util.UriComponentsBuilder;

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

		String anyStringPath = "/any/string/path";

		// Language is not specified

		LocaleContextHolder.setLocale(Locales.DEFAULT);
		Assert.assertEquals("/properties/1", Helper.path(property));
		Assert.assertEquals("/complexes/2", Helper.path(complex));
		Assert.assertEquals("/developers/3", Helper.path(developer));
		Assert.assertEquals("/promo/properties/4", Helper.path(promoProperty));
		Assert.assertEquals("/any/string/path", Helper.path(anyStringPath));

		LocaleContextHolder.setLocale(Locales.RU);
		Assert.assertEquals("/ru/properties/1", Helper.path(property));
		Assert.assertEquals("/ru/complexes/2", Helper.path(complex));
		Assert.assertEquals("/ru/developers/3", Helper.path(developer));
		Assert.assertEquals("/ru/promo/properties/4", Helper.path(promoProperty));
		Assert.assertEquals("/ru/any/string/path", Helper.path(anyStringPath));

		// Language is specified explicitly

		// Default language, no prefix

		Assert.assertEquals("/properties/1", Helper.path(property, Locales.EN));
		Assert.assertEquals("/complexes/2", Helper.path(complex, Locales.EN));
		Assert.assertEquals("/developers/3", Helper.path(developer, Locales.EN));
		Assert.assertEquals("/promo/properties/4", Helper.path(promoProperty, Locales.EN));
		Assert.assertEquals("/any/string/path", Helper.path(anyStringPath, Locales.EN));

		// Russian language prefix
		Assert.assertEquals("/ru/properties/1", Helper.path(property, Locales.RU));
		Assert.assertEquals("/ru/complexes/2", Helper.path(complex, Locales.RU));
		Assert.assertEquals("/ru/developers/3", Helper.path(developer, Locales.RU));
		Assert.assertEquals("/ru/promo/properties/4", Helper.path(promoProperty, Locales.RU));
		Assert.assertEquals("/ru/any/string/path", Helper.path(anyStringPath, Locales.RU));
	}

	/**
	 * Secured path to promo properties should not contain the '/promo' path segment.
	 */
	@Test
	public void securedPathToPromoProperties() {
		Property promo = PropertyFactory.INSTANCE.get(p -> {
			p.setId(1L);
			p.setOfferStatus(OfferStatus.PROMO);
		});
		LocaleContextHolder.setLocale(Locales.DEFAULT);

		Assert.assertEquals("/user/properties/1", Helper.path(Paths.USER, promo));
		Assert.assertEquals("/master/properties/1", Helper.path(Paths.MASTER, promo));
	}

	@Test
	public void appendPageTest() {
		String anyPath = "/any-path";
		String anyPathWithParam = "/any-path?param=value";
		int firstPage = 0;
		int secondPage = 1;

		Assert.assertEquals("/any-path", Helper.appendPage(anyPath, firstPage));
		Assert.assertEquals(UriComponentsBuilder.fromPath("/any-path").queryParam("page", "1").toUriString(),
			Helper.appendPage(anyPath, secondPage));
		Assert.assertEquals(UriComponentsBuilder.fromPath("/any-path").queryParam("param", "value").toUriString(),
			Helper.appendPage(anyPathWithParam, firstPage));
		Assert.assertEquals(UriComponentsBuilder.fromPath("/any-path").queryParam("param", "value")
			.queryParam("page", "1").toUriString(), Helper.appendPage(anyPathWithParam, secondPage));
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
