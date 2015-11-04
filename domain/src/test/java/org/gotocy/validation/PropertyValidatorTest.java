package org.gotocy.validation;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.domain.validation.PropertyValidator;
import org.gotocy.factory.PropertyFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.gotocy.FieldValidationAsserts.*;

/**
 * @author ifedorenkov
 */
public class PropertyValidatorTest {

	private static final Set<PropertyType> ALL_PROPERTY_TYPES = EnumSet.allOf(PropertyType.class);
	private static final Set<PropertyStatus> ALL_PROPERTY_STATUSES = EnumSet.allOf(PropertyStatus.class);
	private static final Set<OfferStatus> ALL_OFFER_STATUSES = EnumSet.allOf(OfferStatus.class);

	@Test
	public void shouldSupportPropertyClass() {
		Assert.assertTrue(PropertyValidator.INSTANCE.supports(Property.class));
	}

	@Test
	public void checkValidPropertyWithInactiveOffer() {
		Assert.assertFalse(validateProperty(PropertyFactory.INSTANCE.get()).hasErrors());
	}

	// Common validation for all properties

	@Test
	public void titleValidation() {
		forEachTypeAndStatus(p -> shouldNotBeEmptyOrWhitespace(p, "title", PropertyValidatorTest::validateProperty));
	}

	@Test
	public void locationValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "location", PropertyValidatorTest::validateProperty));
	}

	@Test
	public void addressValidation() {
		forEachTypeAndStatus(p -> shouldNotBeEmptyOrWhitespace(p, "address", PropertyValidatorTest::validateProperty));
	}

	@Test
	public void propertyTypeValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "propertyType", PropertyValidatorTest::validateProperty));
	}

	@Test
	public void propertyStatusValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "propertyStatus", PropertyValidatorTest::validateProperty));
	}

	@Test
	public void offerStatusValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "offerStatus", PropertyValidatorTest::validateProperty));
	}

	// More complex checks that are type-dependent

	@Test
	public void priceValidation() {
		forEach(
			ALL_PROPERTY_TYPES,
			ALL_PROPERTY_STATUSES,
			EnumSet.of(OfferStatus.ACTIVE),
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "price", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void coveredAreaValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "coveredArea", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void plotSizeValidation() {
		forEach(
			EnumSet.of(PropertyType.HOUSE, PropertyType.LAND),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "plotSize", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void bedroomsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			ALL_PROPERTY_STATUSES,
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "bedrooms", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void levelsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "levels", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void guestsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SHORT_TERM),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "guests", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void distanceToSeaValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SHORT_TERM),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE, 
			p -> shouldBePositiveInt(p, "distanceToSea", PropertyValidatorTest::validateProperty)
		);
	}

	@Test
	public void furnishingValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.LONG_TERM),
			ALL_OFFER_STATUSES,
			PropertyFactory.INSTANCE,
			p -> shouldNotBeNull(p, "furnishing", PropertyValidatorTest::validateProperty)
		);
	}

	private static Errors validateProperty(Property property) {
		Errors errors = new BeanPropertyBindingResult(property, "property");
		PropertyValidator.INSTANCE.validate(property, errors);
		return errors;
	}

	private static void forEach(Set<PropertyType> propertyTypes, Set<PropertyStatus> propertyStatuses,
		Set<OfferStatus> offerStatuses, PropertyFactory factory, Consumer<Property> test)
	{
		for (PropertyType propertyType : propertyTypes) {
			for (PropertyStatus propertyStatus : propertyStatuses) {
				for (OfferStatus offerStatus : offerStatuses) {
					Property property = factory.get();
					property.setPropertyType(propertyType);
					property.setPropertyStatus(propertyStatus);
					property.setOfferStatus(offerStatus);

					test.accept(property);
				}
			}
		}
	}

	private static void forEachTypeAndStatus(Consumer<Property> test) {
		forEach(ALL_PROPERTY_TYPES, ALL_PROPERTY_STATUSES, ALL_OFFER_STATUSES, PropertyFactory.INSTANCE, test);
	}

}
