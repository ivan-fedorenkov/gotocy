package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.OfferType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class SeoOfferTypeFormatterTest {

	private static SeoOfferTypeFormatter formatter;

	@BeforeClass
	public static void setup() {
		formatter = new SeoOfferTypeFormatter();
	}

	@Test
	public void testAllStatuses() throws ParseException {
		for (OfferType offerType : OfferType.values()) {
			for (Locale locale : Locales.SUPPORTED) {
				String printedStatus = formatter.print(offerType, locale);
				Assert.assertNotNull(printedStatus);
				Assert.assertEquals(offerType, formatter.parse(printedStatus, locale));
				Assert.assertEquals(printedStatus, formatter.print(offerType, locale));
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
