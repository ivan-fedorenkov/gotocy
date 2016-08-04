package org.gotocy.integration.user;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyContactsDisplayOption;
import org.gotocy.integration.IntegrationTestBase;
import org.gotocy.integration.config.WithGtcUser;
import org.gotocy.test.factory.PropertyFactory;
import org.junit.Test;

import static org.gotocy.integration.utils.PropertyTestUtils.populatedPropertySubmissionForm;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

/**
 * @author ifedorenkov
 */
public class PropertiesManagementIntegrationTest extends IntegrationTestBase {

	@Test
	@WithGtcUser
	public void propertySubmission() throws Exception {
		// Prepare property that should be created
		Property property = PropertyFactory.INSTANCE.get(p -> {
			// Property's offer should be inactive
			p.setOfferStatus(OfferStatus.INACTIVE);
			// By default, property should have the OWNER contacts display option
			p.setContactsDisplayOption(PropertyContactsDisplayOption.OWNER);
		});

		mvc.perform(fileUpload("/user/properties")
			.with(csrf())
			.with(populatedPropertySubmissionForm(property)))
			.andExpect(redirectedUrlPattern("/user/properties/?"));

	}

}
