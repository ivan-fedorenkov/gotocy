package org.gotocy.validation;

import org.gotocy.domain.*;
import org.gotocy.domain.validation.PropertyValidator;
import org.gotocy.domain.validation.ValidationConstraints;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author ifedorenkov
 */
public class PropertyValidatorTest {

	private static final String ANY_STRING = "any_string";
	private static final Integer ANY_INT = 1;
	private static final Integer ZERO = 0;
	private static final Integer NEGATIVE_INT = -1;
	private static final Double ANY_DOUBLE = 1D;
	private static final Boolean ANY_BOOLEAN = Boolean.FALSE;

	private static final Set<PropertyType> ALL_PROPERTY_TYPES = EnumSet.allOf(PropertyType.class);
	private static final Set<PropertyStatus> ALL_PROPERTY_STATUSES = EnumSet.allOf(PropertyStatus.class);
	private static final Set<OfferStatus> ALL_OFFER_STATUSES = EnumSet.allOf(OfferStatus.class);

	@Test
	public void shouldSupportPropertyClass() {
		Assert.assertTrue(PropertyValidator.INSTANCE.supports(Property.class));
	}

	@Test
	public void checkValidPropertyWithInactiveOffer() {
		Assert.assertFalse(validateProperty(getAnyValidProperty()).hasErrors());
	}

	// Common validation for all properties

	@Test
	public void titleValidation() {
		forEachTypeAndStatus(p -> shouldNotBeEmptyOrWhitespace(p, "title"));
	}

	@Test
	public void locationValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "location"));
	}

	@Test
	public void addressValidation() {
		forEachTypeAndStatus(p -> shouldNotBeEmptyOrWhitespace(p, "address"));
	}

	@Test
	public void propertyTypeValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "propertyType"));
	}

	@Test
	public void propertyStatusValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "propertyStatus"));
	}

	@Test
	public void offerStatusValidation() {
		forEachTypeAndStatus(p -> shouldNotBeNull(p, "offerStatus"));
	}

	// More complex checks that are type-dependent

	@Test
	public void priceValidation() {
		forEach(
			ALL_PROPERTY_TYPES,
			ALL_PROPERTY_STATUSES,
			EnumSet.of(OfferStatus.ACTIVE),
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "price")
		);
	}

	@Test
	public void coveredAreaValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "coveredArea")
		);
	}

	@Test
	public void plotSizeValidation() {
		forEach(
			EnumSet.of(PropertyType.HOUSE, PropertyType.LAND),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "plotSize")
		);
	}

	@Test
	public void bedroomsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			ALL_PROPERTY_STATUSES,
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "bedrooms")
		);
	}

	@Test
	public void levelsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SALE),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "levels")
		);
	}

	@Test
	public void guestsValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SHORT_TERM),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "guests")
		);
	}

	@Test
	public void distanceToSeaValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.SHORT_TERM),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldBePositiveInt(p, "distanceToSea")
		);
	}

	@Test
	public void furnishingValidation() {
		forEach(
			EnumSet.of(PropertyType.APARTMENT, PropertyType.HOUSE),
			EnumSet.of(PropertyStatus.LONG_TERM),
			ALL_OFFER_STATUSES,
			getAnyValidProperty(), p -> shouldNotBeNull(p, "furnishing")
		);
	}

	private static Errors validateProperty(Property property) {
		Errors errors = new BeanPropertyBindingResult(property, "property");
		PropertyValidator.INSTANCE.validate(property, errors);
		return errors;
	}

	private static void assertHasNotEmptyFieldError(Errors errors, String field) {
		Assert.assertTrue(errors.hasFieldErrors(field));
		Assert.assertEquals(ValidationConstraints.NOT_EMPTY, errors.getFieldError(field).getCode());
	}

	private static void assertHasPositiveIntFieldError(Errors errors, String field) {
		Assert.assertTrue(errors.hasFieldErrors(field));
		Assert.assertEquals(ValidationConstraints.POSITIVE_INT, errors.getFieldError(field).getCode());
	}

	private static void shouldNotBeNull(Property property, String field) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(property);
		wrapper.setPropertyValue(field, null);
		assertHasNotEmptyFieldError(validateProperty(property), field);
	}

	private static void shouldBePositiveInt(Property property, String field) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(property);
		wrapper.setPropertyValue(field, ZERO);
		assertHasPositiveIntFieldError(validateProperty(property), field);
		wrapper.setPropertyValue(field, NEGATIVE_INT);
		assertHasPositiveIntFieldError(validateProperty(property), field);
	}

	private static void shouldNotBeEmptyOrWhitespace(Property property, String field) {
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(property);
		wrapper.setPropertyValue(field, null);
		assertHasNotEmptyFieldError(validateProperty(property), field);
		wrapper.setPropertyValue(field, "");
		assertHasNotEmptyFieldError(validateProperty(property), field);
		wrapper.setPropertyValue(field, " ");
		assertHasNotEmptyFieldError(validateProperty(property), field);
	}

	private static void forEach(Set<PropertyType> propertyTypes, Set<PropertyStatus> propertyStatuses,
		Set<OfferStatus> offerStatuses, Property property, Consumer<Property> test)
	{
		for (PropertyType propertyType : propertyTypes) {
			for (PropertyStatus propertyStatus : propertyStatuses) {
				for (OfferStatus offerStatus : offerStatuses) {
					property.setPropertyType(propertyType);
					property.setPropertyStatus(propertyStatus);
					property.setOfferStatus(offerStatus);

					test.accept(property);
				}
			}
		}
	}

	private static void forEachTypeAndStatus(Property property, Consumer<Property> test) {
		forEach(ALL_PROPERTY_TYPES, ALL_PROPERTY_STATUSES, ALL_OFFER_STATUSES, property, test);
	}

	private static void forEachTypeAndStatus(Consumer<Property> test) {
		forEachTypeAndStatus(getAnyValidProperty(), test);
	}

	private static Property getAnyValidProperty() {
		Property property = new Property();
		property.setTitle(ANY_STRING);
		property.setLocation(Location.LARNACA);
		property.setAddress(ANY_STRING);
		property.setLatitude(ANY_DOUBLE);
		property.setLongitude(ANY_DOUBLE);
		property.setPropertyType(PropertyType.HOUSE);
		property.setPropertyStatus(PropertyStatus.SALE);
		property.setOfferStatus(OfferStatus.SOLD);
		property.setVatIncluded(ANY_BOOLEAN);
		property.setCoveredArea(ANY_INT);
		property.setPlotSize(ANY_INT);
		property.setBedrooms(ANY_INT);
		property.setReadyToMoveIn(ANY_BOOLEAN);
		property.setLevels(ANY_INT);
		property.setFurnishing(Furnishing.NONE);
		property.setGuests(ANY_INT);
		property.setAirConditioner(ANY_BOOLEAN);
		property.setDistanceToSea(ANY_INT);
		property.setHeatingSystem(ANY_BOOLEAN);

		return property;
	}

}
