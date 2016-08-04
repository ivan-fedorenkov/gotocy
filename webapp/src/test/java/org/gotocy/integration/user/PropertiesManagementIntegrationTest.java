package org.gotocy.integration.user;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyContactsDisplayOption;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.integration.config.WithGtcUser;
import org.gotocy.repository.PropertyPredicates;
import org.gotocy.service.PropertyService;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.gotocy.integration.utils.PropertyTestUtils.assertPropertiesEquals;
import static org.gotocy.integration.utils.PropertyTestUtils.populatedPropertySubmissionForm;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

/**
 * @author ifedorenkov
 */
public class PropertiesManagementIntegrationTest extends IntegrationTestBase {

	@Autowired
	private PropertyService propertyService;

	@Test
	@WithGtcUser
	public void propertySubmission() throws Exception {
		GtcUser user = (GtcUser) TestSecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Prepare property that should be created
		Property property = PropertyFactory.INSTANCE.get(p -> {
			// Property's offer should be inactive
			p.setOfferStatus(OfferStatus.INACTIVE);
			// By default, property should have the OWNER contacts display option
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OWNER);
			p.setOwner(user);
		});

		mvc.perform(fileUpload("/user/properties")
			.with(csrf())
			.with(populatedPropertySubmissionForm(property)))
			.andExpect(redirectedUrlPattern("/user/properties/?"));



		Iterable<Property> props = propertyService.find(PropertyPredicates.ofUser(user), new Sort("id"));
		List<Property> userProperties = StreamSupport.stream(props.spliterator(), false).collect(Collectors.toList());

		Assert.assertThat(userProperties, hasSize(1));
		assertPropertiesEquals(property, userProperties.get(0));
	}

}
