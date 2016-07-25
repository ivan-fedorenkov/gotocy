package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyContactsDisplayOption;
import org.gotocy.forms.PropertyForm;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.ContactsFactory;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author ifedorenkov
 */
public class PropertyIntegrationTest extends IntegrationTestBase {

	@Autowired
	private PropertyRepository propertyRepository;

	@Test
	public void propertyCreation() throws Exception {
		// Prepare property that should be created
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setOfferStatus(OfferStatus.PROMO);
			// By default, property should have the OWNER contacts display option
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OWNER);
		});

		// Post the property
		ResultActions result = mockMvc.perform(fileUpload("/properties").with(csrf())
			.param("title", property.getTitle())
			.param("propertyType", property.getPropertyType().name())
			.param("propertyStatus", property.getPropertyStatus().name())
			.param("location", property.getLocation().name())
			.param("address", property.getAddress())
			.param("shortAddress", property.getShortAddress())
			.param("price", String.valueOf(property.getPrice()))
			.param("latitude", String.valueOf(property.getLatitude()))
			.param("longitude", String.valueOf(property.getLongitude()))
			.param("coveredArea", String.valueOf(property.getCoveredArea()))
			.param("plotSize", String.valueOf(property.getPlotSize()))
			.param("levels", String.valueOf(property.getLevels()))
			.param("bedrooms", String.valueOf(property.getBedrooms()))
			.param("furnishing", property.getFurnishing().name())
			.param("readyToMoveIn", String.valueOf(property.isReadyToMoveIn()))
			.param("airConditioner", String.valueOf(property.hasAirConditioner()))
			.param("heatingSystem", String.valueOf(property.hasHeatingSystem()))
			.param("vatIncluded", String.valueOf(property.isVatIncluded())));

		// Verify that the property has been created
		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);

		// Verify fields that must be set disregard to what a user typed in
		Assert.assertEquals(OfferStatus.PROMO, createdProperty.getOfferStatus());

		// Verify the response status and the response page
		result.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/promo/properties/" + createdProperty.getId()));
	}


	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void propertyCreationByAdmin() throws Exception {
		// A property with overridden contacts
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OVERRIDDEN);
			p.setOverriddenContacts(ContactsFactory.INSTANCE.get());
		});

		mockMvc.perform(post("/master/properties").with(csrf())
			.param("title", property.getTitle())
			.param("propertyType", property.getPropertyType().name())
			.param("propertyStatus", property.getPropertyStatus().name())
			.param("offerStatus", property.getOfferStatus().name())
			.param("address", property.getAddress())
			.param("shortAddress", property.getShortAddress())
			.param("price", String.valueOf(property.getPrice()))
			.param("latitude", String.valueOf(property.getLatitude()))
			.param("longitude", String.valueOf(property.getLongitude()))
			.param("bedrooms", String.valueOf(property.getBedrooms()))
			.param("furnishing", property.getFurnishing().name())
			.param("readyToMoveIn", String.valueOf(property.isReadyToMoveIn()))
			.param("airConditioner", String.valueOf(property.hasAirConditioner()))
			.param("heatingSystem", String.valueOf(property.hasHeatingSystem()))
			.param("vatIncluded", String.valueOf(property.isVatIncluded()))
			.param("contactsDisplayOption", PropertyContactsDisplayOption.OVERRIDDEN.name())
			.param("contactName", property.getOverriddenContacts().getName())
			.param("contactEmail", property.getOverriddenContacts().getEmail())
			.param("contactPhone", property.getOverriddenContacts().getPhone())
			.param("contactSpokenLanguages", property.getOverriddenContacts().getSpokenLanguages()))
			.andExpect(MockMvcResultMatchers.status().isOk());

		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);
	}

	private static void assertPropertiesEquals(Property expected, Property actual) {
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
