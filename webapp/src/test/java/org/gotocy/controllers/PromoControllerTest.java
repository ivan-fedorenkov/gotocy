package org.gotocy.controllers;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.service.PropertyService;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ifedorenkov
 */
public class PromoControllerTest {

	private MockMvc mvc;
	private PropertyService propertyService;

	@Before
	public void setUp() throws Exception {
		propertyService = mock(PropertyService.class);
		
		FormattingConversionService formattingConversionService = new DefaultFormattingConversionService();
		formattingConversionService.addConverter(String.class, Property.class, new Converter<String, Property>() {
			@Override
			public Property convert(String idStr) {
				return propertyService.findOne(Long.parseLong(idStr));
			}
		});
		
		mvc = MockMvcBuilders
			.standaloneSetup(new PromoController(propertyService))
			.setConversionService(formattingConversionService)
			.build();
	}

	@Test
	public void testShowPromoProperty() throws Exception {
		Property promo = PropertyFactory.INSTANCE.get(p -> {
			p.setId(1L);
			p.setOfferStatus(OfferStatus.PROMO);
		});
		Property crawled = PropertyFactory.INSTANCE.get(p -> {
			p.setId(2L);
			p.setOfferStatus(OfferStatus.ACTIVE);
			p.setCrawlSource("anything");
		});
		Property existing = PropertyFactory.INSTANCE.get(p -> {
			p.setId(3L);
			p.setOfferStatus(OfferStatus.ACTIVE);
		});

		when(propertyService.findOne(1L)).thenReturn(promo);
		when(propertyService.findOne(2L)).thenReturn(crawled);
		when(propertyService.findOne(3L)).thenReturn(existing);

		mvc.perform(get("/promo/properties/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("promo/property"));

		mvc.perform(get("/promo/properties/2"))
			.andExpect(status().isOk())
			.andExpect(view().name("property/show"));

		mvc.perform(get("/promo/properties/3"))
			.andExpect(redirectedUrl("/properties/3"));
	}

}
