package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.PropertyStatus;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class SeoPropertyStatusFormatterTest {

	private static SeoPropertyStatusFormatter formatter;

	@BeforeClass
	public static void setup() {
		formatter = new SeoPropertyStatusFormatter();
	}

	@Test
	public void testAllStatuses() throws ParseException {
		for (PropertyStatus propertyStatus : PropertyStatus.values()) {
			for (Locale locale : Locales.SUPPORTED) {
				String printedStatus = formatter.print(propertyStatus, locale);
				Assert.assertNotNull(printedStatus);
				Assert.assertEquals(propertyStatus, formatter.parse(printedStatus, locale));
				Assert.assertEquals(printedStatus, formatter.print(propertyStatus, locale));
			}
		}
	}

	@Test(expected = ParseException.class)
	public void localeMismatchRuStr() throws ParseException {
		formatter.parse("prodazha", Locales.EN);
	}

	@Test(expected = ParseException.class)
	public void localeMismatchEnStr() throws ParseException {
		formatter.parse("for-sale", Locales.RU);
	}

}
