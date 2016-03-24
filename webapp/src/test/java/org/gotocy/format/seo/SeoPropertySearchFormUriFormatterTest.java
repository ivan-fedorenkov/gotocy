package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.forms.PropertiesSearchForm;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.*;

/**
 * @author ifedorenkov
 */
@RunWith(Parameterized.class)
public class SeoPropertySearchFormUriFormatterTest {

	private static SeoPropertySearchFormUriFormatter formatter;

	@BeforeClass
	public static void initFormatter() {
		formatter = new SeoPropertySearchFormUriFormatter();
	}

	private PropertiesSearchForm propertiesSearchForm;
	private Locale locale;

	public SeoPropertySearchFormUriFormatterTest(PropertiesSearchForm propertiesSearchForm, Locale locale) {
		this.propertiesSearchForm = propertiesSearchForm;
		this.locale = locale;
	}

	@Test
	public void test() throws ParseException {
		String printedUri = formatter.print(propertiesSearchForm, locale);
		Assert.assertNotNull(printedUri);

		PropertiesSearchForm parsedForm = formatter.parse(printedUri, locale);
		Assert.assertEquals(parsedForm.getPropertyType(), propertiesSearchForm.getPropertyType());
		Assert.assertEquals(parsedForm.getPropertyStatus(), propertiesSearchForm.getPropertyStatus());
		Assert.assertEquals(parsedForm.getLocation(), propertiesSearchForm.getLocation());

		Assert.assertEquals(printedUri, formatter.print(parsedForm, locale));
	}

	@Parameterized.Parameters
	public static Collection<Object[]> params() {
		List<Object[]> params = new ArrayList<>();

		SeoPropertySearchFormUriFormatter formatter = new SeoPropertySearchFormUriFormatter();
		List<PropertyType> propertyTypes = new ArrayList<>();
		propertyTypes.add(null);
		propertyTypes.addAll(Arrays.asList(PropertyType.values()));

		List<PropertyStatus> propertyStatuses = new ArrayList<>();
		propertyStatuses.add(null);
		propertyStatuses.addAll(Arrays.asList(PropertyStatus.values()));

		List<Location> locations = new ArrayList<>();
		locations.add(null);
		locations.addAll(Arrays.asList(Location.values()));

		List<Locale> locales = Arrays.asList(Locales.EN, Locales.RU);

		for (Locale locale : locales) {
			for (PropertyType propertyType : propertyTypes) {
				for (PropertyStatus propertyStatus : propertyStatuses) {
					for (Location location : locations) {
						PropertiesSearchForm form = new PropertiesSearchForm();
						form.setPropertyType(propertyType);
						form.setPropertyStatus(propertyStatus);
						form.setLocation(location);
						params.add(new Object[] {form, locale});
					}
				}
			}
		}

		return params;
	}

}
