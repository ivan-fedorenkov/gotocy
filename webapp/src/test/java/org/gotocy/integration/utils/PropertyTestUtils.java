package org.gotocy.integration.utils;

import org.gotocy.domain.Property;
import org.gotocy.forms.PropertySubmissionForm;
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

}
