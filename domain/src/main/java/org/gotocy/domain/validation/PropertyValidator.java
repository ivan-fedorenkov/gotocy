package org.gotocy.domain.validation;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.function.Predicate;

/**
 * Property validator.
 *
 * @author ifedorenkov
 */
public class PropertyValidator implements Validator {

	private static final Predicate<Property> ACTIVE_OFFER = p -> p.getOfferStatus() == OfferStatus.ACTIVE;

	private static final Predicate<Property> SALE = p -> p.getPropertyStatus() == PropertyStatus.SALE;
	private static final Predicate<Property> LONG_TERM = p -> p.getPropertyStatus() == PropertyStatus.LONG_TERM;
	private static final Predicate<Property> SHORT_TERM = p -> p.getPropertyStatus() == PropertyStatus.SHORT_TERM;

	private static final Predicate<Property> HOUSE = p -> p.getPropertyType() == PropertyType.HOUSE;
	private static final Predicate<Property> APARTMENT = p -> p.getPropertyType() == PropertyType.APARTMENT;
	private static final Predicate<Property> LAND = p -> p.getPropertyType() == PropertyType.LAND;

	private static final FieldValidator NOT_NULL = new NullValidator();
	private static final FieldValidator NOT_EMPTY = new EmptyStringValidator();
	private static final FieldValidator POSITIVE_INTEGER = new PositiveIntegerValidator();

	@Override
	public boolean supports(Class<?> clazz) {
		return Property.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Property property = (Property) target;

		validate(errors, "title", NOT_EMPTY);
		validate(errors, "address", NOT_EMPTY);
		validate(errors, "location", NOT_NULL);
		validate(errors, "latitude", NOT_NULL);
		validate(errors, "longitude", NOT_NULL);
		validate(errors, "propertyType", NOT_NULL);
		validate(errors, "propertyStatus", NOT_NULL);
		validate(errors, "offerStatus", NOT_NULL);

		if (ACTIVE_OFFER.test(property))
			validate(errors, "price", NOT_NULL, POSITIVE_INTEGER);

		if (SALE.test(property))
			validate(errors, "vatIncluded", NOT_NULL);

		if (HOUSE.or(APARTMENT).and(SALE).test(property))
			validate(errors, "coveredArea", NOT_NULL, POSITIVE_INTEGER);

		if (HOUSE.or(LAND).and(SALE).test(property))
			validate(errors, "plotSize", NOT_NULL, POSITIVE_INTEGER);

		if (HOUSE.or(APARTMENT).test(property))
			validate(errors, "bedrooms", NOT_NULL, POSITIVE_INTEGER);

		if (APARTMENT.or(HOUSE).and(SALE).test(property))
			validate(errors, "readyToMoveIn", NOT_NULL);

		if (APARTMENT.or(HOUSE).and(SALE).test(property))
			validate(errors, "levels", NOT_NULL, POSITIVE_INTEGER);

		if (APARTMENT.or(HOUSE).and(LONG_TERM).test(property))
			validate(errors, "furnishing", NOT_NULL);

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			validate(errors, "guests", NOT_NULL, POSITIVE_INTEGER);

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			validate(errors, "airConditioner", NOT_NULL);

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			validate(errors, "distanceToSea", NOT_NULL, POSITIVE_INTEGER);

		if (APARTMENT.or(HOUSE).and(LONG_TERM).test(property))
			validate(errors, "heatingSystem", NOT_NULL);
	}

	private static void validate(Errors errors, String field, FieldValidator... validators)	{
		for (FieldValidator validator : validators) {
			// TODO: log errors
			if (validator.supports(errors.getFieldType(field)))
				validator.validate(field, errors);
		}
	}

}
