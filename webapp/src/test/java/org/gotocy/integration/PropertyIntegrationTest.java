package org.gotocy.integration;

import org.gotocy.Application;
import org.gotocy.config.SecurityProperties;
import org.gotocy.domain.Contact;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.factory.PropertyFactory;
import org.gotocy.repository.PropertyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author ifedorenkov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true, value = {
	"gotocy.s3.secretKey=test",
	"gotocy.s3.accessKey=test",
	"gotocy.s3.bucket=test",
	"gotocy.webapp.profile=test",
	"gotocy.webapp.security.login=test",
	"gotocy.webapp.security.password=test",
	"debug"
})
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
		Property property = PropertyFactory.build();
		mockMvc.perform(post("/property")
			.param("title", property.getTitle())
			.param("propertyType", property.getPropertyType().name())
			.param("propertyStatus", property.getPropertyStatus().name())
			.param("fullAddress", property.getAddress())
			.param("shortAddress", property.getShortAddress())
			.param("price", String.valueOf(property.getPrice()))
			.param("latitude", String.valueOf(property.getLatitude()))
			.param("longitude", String.valueOf(property.getLongitude()))
			.param("bedrooms", String.valueOf(property.getBedrooms()))
			.param("furnishing", property.getFurnishing().name())
			.param("readyToMoveIn", String.valueOf(property.getReadyToMoveIn()))
			.param("airConditioner", String.valueOf(property.getAirConditioner()))
			.param("heatingSystem", String.valueOf(property.getHeatingSystem()))
			.param("vatIncluded", String.valueOf(property.getVatIncluded())))
			.andExpect(MockMvcResultMatchers.status().isOk());

		List<Property> properties = propertyRepository.findAll();
		Assert.assertEquals(1, properties.size());
		Property createdProperty = properties.get(0);
		assertPropertiesEquals(property, createdProperty);
		// A user can only add properties with the active offer and without primary contact
		Assert.assertEquals(OfferStatus.ACTIVE, createdProperty.getOfferStatus());
		Assert.assertNull(createdProperty.getPrimaryContact());
	}


	@Test
	public void propertyCreationByAdmin() throws Exception {
		Property property = PropertyFactory.build();
		mockMvc.perform(post("/master/properties")
			.sessionAttr(SecurityProperties.SESSION_KEY, Boolean.TRUE)
			.param("title", property.getTitle())
			.param("propertyType", property.getPropertyType().name())
			.param("propertyStatus", property.getPropertyStatus().name())
			.param("offerStatus", property.getOfferStatus().name())
			.param("fullAddress", property.getAddress())
			.param("shortAddress", property.getShortAddress())
			.param("price", String.valueOf(property.getPrice()))
			.param("latitude", String.valueOf(property.getLatitude()))
			.param("longitude", String.valueOf(property.getLongitude()))
			.param("bedrooms", String.valueOf(property.getBedrooms()))
			.param("furnishing", property.getFurnishing().name())
			.param("readyToMoveIn", String.valueOf(property.getReadyToMoveIn()))
			.param("airConditioner", String.valueOf(property.getAirConditioner()))
			.param("heatingSystem", String.valueOf(property.getHeatingSystem()))
			.param("vatIncluded", String.valueOf(property.getVatIncluded()))
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
		Assert.assertEquals(expected.getLatitude(), actual.getLatitude());
		Assert.assertEquals(expected.getLongitude(), actual.getLongitude());
		Assert.assertEquals(expected.getBedrooms(), actual.getBedrooms());
		Assert.assertEquals(expected.getFurnishing(), actual.getFurnishing());
		Assert.assertEquals(expected.getReadyToMoveIn(), actual.getReadyToMoveIn());
		Assert.assertEquals(expected.getAirConditioner(), actual.getAirConditioner());
		Assert.assertEquals(expected.getHeatingSystem(), actual.getHeatingSystem());
		Assert.assertEquals(expected.getVatIncluded(), actual.getVatIncluded());
	}

	private static void assertPrimaryContactsEquals(Contact expected, Contact actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getPhone(), actual.getPhone());
		Assert.assertEquals(expected.getSpokenLanguages(), actual.getSpokenLanguages());
	}

}
