package org.gotocy.domain.validation;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
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

	private static final Predicate<Property> SALE = p -> p.getPropertyStatus() == PropertyStatus.SALE;
	private static final Predicate<Property> LONG_TERM = p -> p.getPropertyStatus() == PropertyStatus.LONG_TERM;
	private static final Predicate<Property> SHORT_TERM = p -> p.getPropertyStatus() == PropertyStatus.SHORT_TERM;

	private static final Predicate<Property> HOUSE = p -> p.getPropertyType() == PropertyType.HOUSE;
	private static final Predicate<Property> APARTMENT = p -> p.getPropertyType() == PropertyType.APARTMENT;
	private static final Predicate<Property> LAND = p -> p.getPropertyType() == PropertyType.LAND;

	private static final FieldValidator NOT_NULL = new NullValidator();
	private static final FieldValidator NOT_EMPTY = new EmptyStringValidator();

	private static final IntFieldValidator POSITIVE_INT = new PositiveIntValidator();

	private PropertyValidator() {
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Property.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Property property = (Property) target;

		validate(errors, "title", property.getTitle(), NOT_EMPTY);
		validate(errors, "address", property.getAddress(), NOT_EMPTY);
		validate(errors, "location", property.getLocation(), NOT_NULL);
		validate(errors, "propertyType", property.getPropertyType(), NOT_NULL);
		validate(errors, "propertyStatus", property.getPropertyStatus(), NOT_NULL);
		validate(errors, "offerStatus", property.getOfferStatus(), NOT_NULL);

		if (ACTIVE_OFFER.test(property))
			validate(errors, "price", property.getPrice(), POSITIVE_INT);

		if (HOUSE.or(APARTMENT).and(SALE).test(property))
			validate(errors, "coveredArea", property.getCoveredArea(), POSITIVE_INT);

		if (HOUSE.or(LAND).and(SALE).test(property))
			validate(errors, "plotSize", property.getPlotSize(), POSITIVE_INT);

		if (HOUSE.or(APARTMENT).test(property))
			validate(errors, "bedrooms", property.getBedrooms(), POSITIVE_INT);

		if (APARTMENT.or(HOUSE).and(SALE).test(property))
			validate(errors, "levels", property.getLevels(), POSITIVE_INT);

		if (APARTMENT.or(HOUSE).and(LONG_TERM).test(property))
			validate(errors, "furnishing", property.getFurnishing(), NOT_NULL);

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			validate(errors, "guests", property.getGuests(), POSITIVE_INT);

		if (APARTMENT.or(HOUSE).and(SHORT_TERM).test(property))
			validate(errors, "distanceToSea", property.getDistanceToSea(), POSITIVE_INT);
	}

	private static void validate(Errors errors, String field, Object fieldValue, FieldValidator... validators)	{
		for (FieldValidator validator : validators) {
			// TODO: log errors
			if (validator.supports(errors.getFieldType(field)))
				validator.validate(field, fieldValue, errors);
		}
	}

	private static void validate(Errors errors, String field, int fieldValue, IntFieldValidator validator) {
		validator.validate(field, fieldValue, errors);
	}

}
