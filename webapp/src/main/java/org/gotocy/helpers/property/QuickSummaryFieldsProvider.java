package org.gotocy.helpers.property;

import org.gotocy.domain.OfferStatus;
import org.gotocy.domain.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Quick summary fields provider.
 *
 * @author ifedorenkov
 */
class QuickSummaryFieldsProvider implements FieldsProvider {

	private static final Predicate<Property> INACTIVE_OFFER = p -> p.getOfferStatus() != OfferStatus.ACTIVE;

	private static final FieldFormat[] LONG_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.PRICE,
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING,
		FieldFormat.HEATING_SYSTEM
	};

	private static final FieldFormat[] RENTED_LONG_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING,
		FieldFormat.HEATING_SYSTEM
	};

	private static final FieldFormat[] SHORT_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.PRICE,
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.AIR_CONDITIONER,
		FieldFormat.DISTANCE_TO_SEA
	};

	private static final FieldFormat[] RENTED_SHORT_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.AIR_CONDITIONER,
		FieldFormat.DISTANCE_TO_SEA
	};

	private static final FieldFormat[] SALE_HOUSE = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SOLD_HOUSE = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_APARTMENT = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.COVERED_AREA,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SOLD_APARTMENT = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.COVERED_AREA,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_LAND = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.PLOT_SIZE
	};

	private static final FieldFormat[] SOLD_LAND = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.PLOT_SIZE
	};

	public FieldFormat[] getFields(Property property) {
		if (property.getOfferType() == null)
			return getFieldsDisregardingOffer(property);

		switch (property.getOfferType()) {
		case LONG_TERM:
			return INACTIVE_OFFER.test(property) ? RENTED_LONG_TERM : LONG_TERM;
		case SHORT_TERM:
			return INACTIVE_OFFER.test(property) ? RENTED_SHORT_TERM : SHORT_TERM;
		case SALE:
			switch (property.getPropertyType()) {
			case HOUSE:
				return INACTIVE_OFFER.test(property) ? SOLD_HOUSE : SALE_HOUSE;
			case APARTMENT:
				return INACTIVE_OFFER.test(property) ? SOLD_APARTMENT : SALE_APARTMENT;
			case LAND:
				return INACTIVE_OFFER.test(property) ? SOLD_LAND : SALE_LAND;
			}
		}
		return new FieldFormat[0];
	}

	private static FieldFormat[] getFieldsDisregardingOffer(Property property) {
		List<FieldFormat> fieldsToDisplay = new ArrayList<>();
		fieldsToDisplay.add(FieldFormat.LOCATION);
		fieldsToDisplay.add(FieldFormat.PROPERTY_TYPE);
		if (property.getPlotSize() != 0)
			fieldsToDisplay.add(FieldFormat.PLOT_SIZE);
		if (property.getCoveredArea() != 0)
			fieldsToDisplay.add(FieldFormat.COVERED_AREA);
		if (property.getLevels() != 0)
			fieldsToDisplay.add(FieldFormat.LEVELS);
		if (property.getBedrooms() != 0)
			fieldsToDisplay.add(FieldFormat.BEDROOMS);
		if (property.getGuests() != 0)
			fieldsToDisplay.add(FieldFormat.GUESTS);
		if (property.getDistanceToSea() != 0)
			fieldsToDisplay.add(FieldFormat.DISTANCE_TO_SEA);
		fieldsToDisplay.add(FieldFormat.FURNISHING);
		return fieldsToDisplay.toArray(new FieldFormat[fieldsToDisplay.size()]);
	}

}
