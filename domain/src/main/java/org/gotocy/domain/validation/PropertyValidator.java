package org.gotocy.domain.validation;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.OfferType;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.function.Predicate;

/**
 * Property validator. Singleton, thread safe.
 *
 * @author ifedorenkov
 */
public class PropertyValidator implements Validator {

	public static final PropertyValidator INSTANCE = new PropertyValidator();

	private static final Predicate<Property> ACTIVE_OFFER = p -> p.getOfferStatus() == OfferStatus.ACTIVE;

	private static final Predicate<Property> SALE = p -> p.getOfferType() == OfferType.SALE;
	private static final Predicate<Property> LONG_TERM = p -> p.getOfferType() == OfferType.LONG_TERM;
	private static final Predicate<Property> SHORT_TERM = p -> p.getOfferType() == OfferType.SHORT_TERM;

	private static final Predicate<Property> HOUSE = p -> p.getPropertyType() == PropertyType.HOUSE;
	private static final Predicate<Property> APARTMENT = p -> p.getPropertyType() == PropertyType.APARTMENT;
	private static final Predicate<Property> LAND = p -> p.getPropertyType() == PropertyType.LAND;

	private PropertyValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Property.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Property property = (Property) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", property.getTitle());
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", property.getAddress());
		ValidationUtils.rejectIfNull(errors, "location", property.getLocation());
		ValidationUtils.rejectIfNull(errors, "propertyType", property.getPropertyType());
		ValidationUtils.rejectIfNull(errors, "offerType", property.getOfferType());
		ValidationUtils.rejectIfNull(errors, "offerStatus", property.getOfferStatus());
		ValidationUtils.rejectIfNull(errors, "contactsDisplayOption", property.getContactsDisplayOption());

		if (ACTIVE_OFFER.test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "price", property.getPrice());

		if (HOUSE.or(APARTMENT).and(SALE).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "coveredArea", property.getCoveredArea());

		if (HOUSE.or(LAND).and(SALE).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "plotSize", property.getPlotSize());

		if (HOUSE.or(APARTMENT).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "bedrooms", property.getBedrooms());

		if (APARTMENT.or(HOUSE).and(SALE).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "levels", property.getLevels());

		if (APARTMENT.or(HOUSE).and(LONG_TERM).test(property))
			ValidationUtils.rejectIfNull(errors, "furnishing", property.getFurnishing());

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "guests", property.getGuests());

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			ValidationUtils.rejectIfNegativeOrZero(errors, "distanceToSea", property.getDistanceToSea());
	}

}
