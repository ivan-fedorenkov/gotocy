package org.gotocy.controllers.user;

import com.mysema.query.types.Predicate;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.service.PropertyService;
import org.gotocy.service.TemplatesService;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

/**
 * @author ifedorenkov
 */
public class UserWelcomeControllerTest {

	private MockMvc mvc;
	private PropertyService propertyService;

	@Before
	public void setUp() throws Exception {
		TemplatesService templatesService = mock(TemplatesService.class);
		propertyService = mock(PropertyService.class);
		mvc = MockMvcBuilders.standaloneSetup(new UserWelcomeController(templatesService, propertyService)).build();
	}

	@Test
	public void testEditFirstInactiveOffer() throws Exception {
		// Should redirect to offer editor form of the first property with an offer in the Inactive state
		Property propertyWithInactiveOffer = PropertyFactory.INSTANCE.get(p -> {
			p.setId(1);
			p.setOfferStatus(OfferStatus.INACTIVE);
		});

		when(propertyService.findOne(any(Predicate.class))).thenReturn(propertyWithInactiveOffer);
		mvc.perform(get("/user/welcome/redirect-to-first-inactive-offer"))
			.andExpect(redirectedUrl("/user/properties/1/offers/edit"));


		// Otherwise should try to redirect to the last created property with an offer in one of publicly visible states
		Property p2 = PropertyFactory.INSTANCE.get(p -> {
			p.setId(2);
			p.setOfferStatus(OfferStatus.ACTIVE);
			p.setCreationDate(LocalDate.now().minusDays(1));
		});
		Property p3 = PropertyFactory.INSTANCE.get(p -> {
			p.setId(3);
			p.setOfferStatus(OfferStatus.ACTIVE);
			p.setCreationDate(LocalDate.now());
		});
		Property p4 = PropertyFactory.INSTANCE.get(p -> {
			p.setId(4);
			p.setOfferStatus(OfferStatus.ACTIVE);
			p.setCreationDate(LocalDate.now().minusDays(2));
		});

		when(propertyService.findOne(any(Predicate.class))).thenReturn(null);
		when(propertyService.findPubliclyVisible(any(Predicate.class), any()))
			.thenReturn(new PageImpl<>(Arrays.asList(p2, p3, p4)));
		mvc.perform(get("/user/welcome/redirect-to-first-inactive-offer"))
			.andExpect(redirectedUrl("/user/properties/3/offers/edit"));

		// Otherwise should redirect to property creation form
		when(propertyService.findOne(any(Predicate.class))).thenReturn(null);
		when(propertyService.findPubliclyVisible(any(Predicate.class), any()))
			.thenReturn(new PageImpl<>(Collections.emptyList()));
		mvc.perform(get("/user/welcome/redirect-to-first-inactive-offer"))
			.andExpect(redirectedUrl("/user/properties/new"));
	}

}
