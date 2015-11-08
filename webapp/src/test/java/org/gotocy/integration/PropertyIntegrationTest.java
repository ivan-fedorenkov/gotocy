package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.Profiles;
import org.gotocy.config.SecurityProperties;
import org.gotocy.domain.Contact;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.factory.ContactFactory;
import org.gotocy.domain.factory.PropertyFactory;
import org.gotocy.repository.PropertyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true, value = "debug")
@ActiveProfiles(Profiles.TEST)
@Transactional
public class PropertyIntegrationTest {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void propertyCreation() throws Exception {
		// Prepare property that should be created
		Property property = PropertyFactory.INSTANCE.get(p -> p.setOfferStatus(OfferStatus.PROMO));

		// Post the property
		ResultActions result = mockMvc.perform(fileUpload("/property")
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
		Assert.assertNull(createdProperty.getPrimaryContact());

		// Verify the response status and the response page
		result.andExpect(MockMvcResultMatchers.status().isFound())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/promo/property/" + createdProperty.getId()));

	}


	@Test
	public void propertyCreationByAdmin() throws Exception {
		Property property = PropertyFactory.INSTANCE.get(p -> p.setPrimaryContact(ContactFactory.INSTANCE.get()));
		mockMvc.perform(post("/master/properties")
			.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
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
			.param("contactName", property.getPrimaryContact().getName())
			.param("contactEmail", property.getPrimaryContact().getEmail())
			.param("contactPhone", property.getPrimaryContact().getPhone())
			.param("contactSpokenLanguages", property.getPrimaryContact().getSpokenLanguages()))
			.andExpect(MockMvcResultMatchers.status().isOk());

		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);
		assertPrimaryContactsEquals(property.getPrimaryContact(), createdProperty.getPrimaryContact());
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
	}

	private static void assertPrimaryContactsEquals(Contact expected, Contact actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getSpokenLanguages(), actual.getSpokenLanguages());
	}

}
