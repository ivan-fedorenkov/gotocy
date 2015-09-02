package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * Listing summary fields provider.
 *
 * @author ifedorenkov
 */
class ListingSummaryFieldsProvider implements FieldsProvider {

	private static final FieldFormat[] LONG_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING,
		FieldFormat.HEATING_SYSTEM
	};

	private static final FieldFormat[] SHORT_TERM = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.AIR_CONDITIONER,
		FieldFormat.DISTANCE_TO_SEA
	};

	private static final FieldFormat[] SALE_HOUSE = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_APARTMENT = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.COVERED_AREA,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_LAND = new FieldFormat[]{
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.PLOT_SIZE
	};

	@Override
	public FieldFormat[] getFields(Property property) {
		switch (property.getPropertyStatus()) {
		case LONG_TERM:
			return ListingSummaryFieldsProvider.LONG_TERM;
		case SHORT_TERM:
			return ListingSummaryFieldsProvider.SHORT_TERM;
		case SALE:
			switch (property.getPropertyType()) {
			case HOUSE:
				return SALE_HOUSE;
			case APARTMENT:
				return SALE_APARTMENT;
			case LAND:
				return SALE_LAND;
			}
		}
		return new FieldFormat[0];
	}
}
