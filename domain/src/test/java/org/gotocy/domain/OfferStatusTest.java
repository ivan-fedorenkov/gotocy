package org.gotocy.domain;

import org.gotocy.test.factory.PropertyFactory;
import org.gotocy.test.factory.UserFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;

/**
 * @author ifedorenkov
 */
public class OfferStatusTest {

	@Test
	public void testGetAvailableForUser() {
		GtcUser user = UserFactory.INSTANCE.get();
		GtcUser master = UserFactory.INSTANCE.getMaster();

		Property promo = PropertyFactory.INSTANCE.get(p -> p.setOfferStatus(OfferStatus.PROMO));
		Assert.assertThat(OfferStatus.getAvailableForUser(user, promo), arrayContainingInAnyOrder(OfferStatus.PROMO));
		Assert.assertThat(OfferStatus.getAvailableForUser(master, promo), arrayContainingInAnyOrder(OfferStatus.values()));

		Property property = PropertyFactory.INSTANCE.get(p -> p.setOfferStatus(OfferStatus.ACTIVE));
		Assert.assertThat(OfferStatus.getAvailableForUser(user, property), arrayContainingInAnyOrder(
			OfferStatus.ACTIVE, OfferStatus.INACTIVE, OfferStatus.BOOKED, OfferStatus.RENTED, OfferStatus.SOLD));
		Assert.assertThat(OfferStatus.getAvailableForUser(master, property), arrayContainingInAnyOrder(OfferStatus.values()));
	}

}
