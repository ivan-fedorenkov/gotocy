package org.gotocy.integration;

import org.gotocy.config.Roles;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyContactsDisplayOption;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.ContactsFactory;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.gotocy.integration.utils.PropertyTestUtils.assertPropertiesEquals;
import static org.gotocy.integration.utils.PropertyTestUtils.populatedPropertySubmissionForm;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class PropertyIntegrationTest extends IntegrationTestBase {

	@Autowired
	private PropertyRepository propertyRepository;

	@Test
	@Ignore
	public void accessToInactiveProperty() throws Exception {
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setOfferStatus(OfferStatus.INACTIVE);
		});
		property = propertyRepository.save(property);

		mvc.perform(get("/properties/" + property.getId()))
			.andExpect(status().isNotFound());
	}

	@Test
	public void propertyCreation() throws Exception {
		// Prepare property that should be created
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setOfferStatus(OfferStatus.PROMO);
			// By default, property should have the OWNER contacts display option
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OWNER);
		});

		// Post the property
		mvc.perform(fileUpload("/properties")
			.with(csrf())
			.with(populatedPropertySubmissionForm(property)))
			.andExpect(redirectedUrlPattern("/promo/properties/?"));

		// Verify that the property has been created
		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);

		// Verify fields that must be set disregard to what a user typed in
		Assert.assertEquals(OfferStatus.PROMO, createdProperty.getOfferStatus());

		// Verify fields that should be generated for registration purposes
		Assert.assertNotNull(createdProperty.getRegistrationKey());
	}


	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void propertyCreationByAdmin() throws Exception {
		// A property with overridden contacts
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OVERRIDDEN);
			p.setOverriddenContacts(ContactsFactory.INSTANCE.get());
		});

		mvc.perform(post("/master/properties").with(csrf())
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
			.andExpect(status().isOk());

		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);
	}

}
