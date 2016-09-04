package org.gotocy.integration.master;

import org.gotocy.config.Roles;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyContactsDisplayOption;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.repository.PropertyRepository;
import org.gotocy.test.factory.ContactsFactory;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.gotocy.integration.utils.PropertyTestUtils.assertPropertiesEquals;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ifedorenkov
 */
public class PropertyIntegrationTest extends IntegrationTestBase {

	@Autowired
	private PropertyRepository propertyRepository;

	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void propertyCreation() throws Exception {
		// A property with overridden contacts
		Property property = PropertyFactory.INSTANCE.get(p -> {
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OVERRIDDEN);
			p.setOverriddenContacts(ContactsFactory.INSTANCE.get());
		});

		mvc.perform(post("/master/properties").with(csrf())
			.param("title", property.getTitle())
			.param("propertyType", property.getPropertyType().name())
			.param("offerType", property.getOfferType().name())
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
			.andExpect(status().is3xxRedirection());

		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);
	}

	@Test
	@WithMockUser(roles = Roles.MASTER)
	public void testGenerationOfRegistrationLink() throws Exception {
		// Any property that has no registration key attached
		Property property = PropertyFactory.INSTANCE.get();
		property = propertyRepository.save(property);
		Assert.assertNull(property.getRegistrationKey());

		ResultActions resultActions = mvc.perform(get("/master/properties/" + property.getId() + "/registration-link"))
			.andExpect(status().isOk());

		// Registration key should be set
		Property updated = propertyRepository.findOne(property.getId());
		Assert.assertNotNull(updated.getRegistrationKey());
		String expectedRegistrationLink = UriComponentsBuilder.fromPath("/users/new")
			.queryParam("relPropertyId", property.getId())
			.queryParam("relPropertySecret", property.getRegistrationKey().getKey())
			.build().toString();
		resultActions.andExpect(content().string(endsWith(expectedRegistrationLink)));
		Assert.assertEquals(updated.getRegistrationKey().getEol(), LocalDate.now().plusWeeks(1));
	}

}
