package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.Location;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class SeoLocationFormatterTest {

	private static SeoLocationFormatter formatter;

	@BeforeClass
	public static void setup() {
		formatter = new SeoLocationFormatter();
	}

	@Test
	public void testAllLocations() throws ParseException {
		List<Location> locations = new ArrayList<>();
		locations.add(null);
		locations.addAll(Arrays.asList(Location.values()));

		for (Location location : locations) {
			for (Locale locale : Locales.SUPPORTED) {
				String printedLocation = formatter.print(location, locale);
				Assert.assertNotNull(printedLocation);
				Assert.assertEquals(location, formatter.parse(printedLocation, locale));
				Assert.assertEquals(printedLocation, formatter.print(location, locale));
			}
		}
	}

	@Test(expected = ParseException.class)
	public void localeMismatchRuStr() throws ParseException {
		formatter.parse("na-kipre", Locales.EN);
	}

	@Test(expected = ParseException.class)
	public void localeMismatchEnStr() throws ParseException {
		formatter.parse("in-cyprus", Locales.RU);
	}

}
