package org.gotocy.forms.user;

import lombok.Getter;
import lombok.Setter;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.OfferType;
import org.gotocy.domain.Property;

/**
 * Form that allows users to edit property's offers.
 *
 * @author ifedorenkov
 */
@Getter
@Setter
public class OffersForm {

	private OfferType offerType;
	private OfferStatus offerStatus;
	private int price;

	public OffersForm() {
		offerType = OfferType.SALE;
		offerStatus = OfferStatus.ACTIVE;
		price = 0;
	}

	public  OffersForm(Property property) {
		offerType = property.getOfferType();
		offerStatus = property.getOfferStatus();
		price = property.getPrice();
	}

	public Property mergeWithProperty(Property property) {
		property.setOfferType(offerType);
		property.setOfferStatus(offerStatus);
		property.setPrice(price);
		return property;
	}

}
