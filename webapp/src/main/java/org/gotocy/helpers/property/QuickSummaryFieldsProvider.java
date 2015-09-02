package org.gotocy.helpers.property;

import org.gotocy.domain.Property;

/**
 * Quick summary fields provider.
 *
 * @author ifedorenkov
 */
class QuickSummaryFieldsProvider implements FieldsProvider {

	private static final FieldFormat[] LONG_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.PRICE,
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

	private static final FieldFormat[] SALE_LAND = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.PLOT_SIZE
	};

	public FieldFormat[] getFields(Property property) {
		switch (property.getPropertyStatus()) {
		case LONG_TERM:
			return QuickSummaryFieldsProvider.LONG_TERM;
		case SHORT_TERM:
			return QuickSummaryFieldsProvider.SHORT_TERM;
		case SALE:
			switch (property.getPropertyType()) {
			case HOUSE:
				return QuickSummaryFieldsProvider.SALE_HOUSE;
			case APARTMENT:
				return QuickSummaryFieldsProvider.SALE_APARTMENT;
			case LAND:
				return QuickSummaryFieldsProvider.SALE_LAND;
			}
		}
		return new FieldFormat[0];
	}
}
