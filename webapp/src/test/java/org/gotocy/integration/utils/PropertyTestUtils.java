package org.gotocy.integration.utils;

import org.gotocy.domain.Property;
import org.gotocy.forms.PropertySubmissionForm;
import org.junit.Assert;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 * Various static utils for integration tests that deal with properties.
 *
 * @author ifedorenkov
 */
public class PropertyTestUtils {

	/**
	 * Creates and returns post processor that sets {@link PropertySubmissionForm} params.
	 */
	public static RequestPostProcessor populatedPropertySubmissionForm(Property property) {
		return request -> {
			request.setParameter("title", property.getTitle());
			request.setParameter("propertyType", property.getPropertyType().name());
			request.setParameter("propertyStatus", property.getPropertyStatus().name());
			request.setParameter("location", property.getLocation().name());
			request.setParameter("address", property.getAddress());
			request.setParameter("shortAddress", property.getShortAddress());
			request.setParameter("price", String.valueOf(property.getPrice()));
			request.setParameter("latitude", String.valueOf(property.getLatitude()));
			request.setParameter("longitude", String.valueOf(property.getLongitude()));
			request.setParameter("coveredArea", String.valueOf(property.getCoveredArea()));
			request.setParameter("plotSize", String.valueOf(property.getPlotSize()));
			request.setParameter("levels", String.valueOf(property.getLevels()));
			request.setParameter("bedrooms", String.valueOf(property.getBedrooms()));
			request.setParameter("furnishing", property.getFurnishing().name());
			request.setParameter("readyToMoveIn", String.valueOf(property.isReadyToMoveIn()));
			request.setParameter("airConditioner", String.valueOf(property.hasAirConditioner()));
			request.setParameter("heatingSystem", String.valueOf(property.hasHeatingSystem()));
			request.setParameter("vatIncluded", String.valueOf(property.isVatIncluded()));
			return request;
		};
	}

	/**
	 * Assertion of properties equality.
	 */
	public static void assertPropertiesEquals(Property expected, Property actual) {
		Assert.assertEquals(expected.getTitle(), actual.getTitle());
		Assert.assertEquals(expected.getPropertyType(), actual.getPropertyType());
		Assert.assertEquals(expected.getPropertyStatus(), actual.getPropertyStatus());
		Assert.assertEquals(expected.getOfferStatus(), actual.getOfferStatus());
		Assert.assertEquals(expected.getAddress(), actual.getAddress());
		Assert.assertEquals(expected.getShortAddress(), actual.getShortAddress());
		Assert.assertEquals(expected.getPrice(), actual.getPrice());
		Assert.assertEquals(expected.getLatitude(), actual.getLatitude(), 0d);
		Assert.assertEquals(expected.getLongitude(), actual.getLongitude(), 0d);
		Assert.assertEquals(expected.getBedrooms(), actual.getBedrooms());
		Assert.assertEquals(expected.getFurnishing(), actual.getFurnishing());
		Assert.assertEquals(expected.isReadyToMoveIn(), actual.isReadyToMoveIn());
		Assert.assertEquals(expected.hasAirConditioner(), actual.hasAirConditioner());
		Assert.assertEquals(expected.hasHeatingSystem(), actual.hasHeatingSystem());
		Assert.assertEquals(expected.isVatIncluded(), actual.isVatIncluded());
		Assert.assertEquals(expected.getContactsDisplayOption(), actual.getContactsDisplayOption());
		Assert.assertEquals(expected.getContacts(), actual.getContacts());
	}

}
