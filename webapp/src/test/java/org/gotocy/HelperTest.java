package org.gotocy;

import org.gotocy.config.Locales;
import org.gotocy.domain.Complex;
import org.gotocy.domain.Developer;
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
		Property p = new Property();
		p.setId(1L);

		Complex c = new Complex();
		c.setId(2L);

		Developer d = new Developer();
		d.setId(3L);

		// Language is not specified

		LocaleContextHolder.setLocale(Locales.DEFAULT);
		Assert.assertEquals("/property/1", Helper.path(p));
		Assert.assertEquals("/complex/2", Helper.path(c));
		Assert.assertEquals("/developer/3", Helper.path(d));

		LocaleContextHolder.setLocale(Locales.RU);
		Assert.assertEquals("/ru/property/1", Helper.path(p));
		Assert.assertEquals("/ru/complex/2", Helper.path(c));
		Assert.assertEquals("/ru/developer/3", Helper.path(d));

		// Language is specified explicitly

		// Default language, no prefix

		Assert.assertEquals("/property/1", Helper.path(p, "en"));
		Assert.assertEquals("/complex/2", Helper.path(c, "en"));
		Assert.assertEquals("/developer/3", Helper.path(d, "en"));
		// Russian language prefix
		Assert.assertEquals("/ru/property/1", Helper.path(p, "ru"));
		Assert.assertEquals("/ru/complex/2", Helper.path(c, "ru"));
		Assert.assertEquals("/ru/developer/3", Helper.path(d, "ru"));
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
