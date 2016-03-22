package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.PropertyType;
import org.gotocy.format.Declension;
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
public class SeoPropertyTypeFormatterTest {

	private static SeoPropertyTypeFormatter formatter;

	@BeforeClass
	public static void setup() {
		formatter = new SeoPropertyTypeFormatter();
	}

	@Test
	public void testAllTypes() throws ParseException {
		List<PropertyType> propertyTypes = new ArrayList<>();
		propertyTypes.add(null);
		propertyTypes.addAll(Arrays.asList(PropertyType.values()));

		for (PropertyType propertyType : propertyTypes) {
			for (Locale locale : Locales.SUPPORTED) {
				for (Declension declension : Declension.values()) {
					String printedPropertyType = formatter.print(propertyType, declension, locale);
					Assert.assertNotNull(printedPropertyType);
					Assert.assertEquals(propertyType, formatter.parse(printedPropertyType, locale));
					Assert.assertEquals(printedPropertyType, formatter.print(propertyType, declension, locale));
				}
			}
		}
	}

	@Test(expected = ParseException.class)
	public void localeMismatchRuStr() throws ParseException {
		formatter.parse("nedvizhimost", Locales.EN);
	}

	@Test(expected = ParseException.class)
	public void localeMismatchEnStr() throws ParseException {
		formatter.parse("properties", Locales.RU);
	}

}
