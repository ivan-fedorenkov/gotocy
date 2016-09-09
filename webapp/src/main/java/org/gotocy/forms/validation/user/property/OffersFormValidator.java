package org.gotocy.forms.validation.user.property;

import org.gotocy.domain.GtcUser;
import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.validation.ValidationConstraints;
import org.gotocy.forms.user.property.OffersForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator of the {@link org.gotocy.forms.user.property.OffersForm}.
 *
 * @author ifedorenkov
 */
public class OffersFormValidator implements Validator {

	private final GtcUser user;
	private final Property property;

	/**
	 * @param user current user
	 * @param property whose offer is going to be changed
	 */
	public OffersFormValidator(GtcUser user, Property property) {
		this.user = user;
		this.property = property;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return OffersForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OffersForm form = (OffersForm) target;

		if (user.isMaster())
			return;

		// User can't set the PROMO offer status
		if (form.getOfferStatus() == OfferStatus.PROMO && property.getOfferStatus() != OfferStatus.PROMO)
			errors.reject(ValidationConstraints.INSUFFICIENT_RIGHTS);

		// User can't change the PROMO offer status
		if (form.getOfferStatus() != OfferStatus.PROMO && property.getOfferStatus() == OfferStatus.PROMO)
			errors.reject(ValidationConstraints.INSUFFICIENT_RIGHTS);
	}

}
