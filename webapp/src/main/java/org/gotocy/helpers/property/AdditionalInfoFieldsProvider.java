package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * Additional info fields provider.
 *
 * @author ifedorenkov
 */
class AdditionalInfoFieldsProvider implements FieldsProvider {

	private static final FieldFormat[] LONG_TERM = new FieldFormat[]{
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING
	};

	private static final FieldFormat[] SHORT_TERM = new FieldFormat[]{
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.DISTANCE_TO_SEA_SHORT
	};


	private static final FieldFormat[] SALE_HOUSE = new FieldFormat[]{
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_APARTMENT = new FieldFormat[]{
		FieldFormat.COVERED_AREA,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	};

	private static final FieldFormat[] SALE_LAND = new FieldFormat[]{
		FieldFormat.PLOT_SIZE
	};

	@Override
	public FieldFormat[] getFields(Property property) {
		switch (property.getPropertyStatus()) {
		case LONG_TERM:
			return AdditionalInfoFieldsProvider.LONG_TERM;
		case SHORT_TERM:
			return AdditionalInfoFieldsProvider.SHORT_TERM;
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
