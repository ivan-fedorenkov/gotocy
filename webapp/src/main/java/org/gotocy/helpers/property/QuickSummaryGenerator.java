package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * @author ifedorenkov
 */
enum QuickSummaryGenerator {
	LONG_TERM(
		QuickSummaryField.LOCATION,
		QuickSummaryField.PROPERTY_TYPE,
		QuickSummaryField.RENTAL_TYPE,
		QuickSummaryField.PRICE,
		QuickSummaryField.BEDROOMS,
		QuickSummaryField.FURNISHING,
		QuickSummaryField.HEATING_SYSTEM
	),

	SHORT_TERM (
		QuickSummaryField.LOCATION,
		QuickSummaryField.PROPERTY_TYPE,
		QuickSummaryField.RENTAL_TYPE,
		QuickSummaryField.PRICE,
		QuickSummaryField.BEDROOMS,
		QuickSummaryField.GUESTS,
		QuickSummaryField.AIR_CONDITIONER,
		QuickSummaryField.DISTANCE_TO_SEA
	),

	SALE_HOUSE(
		QuickSummaryField.LOCATION,
		QuickSummaryField.PROPERTY_TYPE,
		QuickSummaryField.READY_TO_MOVE_IN,
		QuickSummaryField.PRICE,
		QuickSummaryField.VAT,
		QuickSummaryField.COVERED_AREA,
		QuickSummaryField.PLOT_SIZE,
		QuickSummaryField.BEDROOMS,
		QuickSummaryField.LEVELS
	),

	SALE_APARTMENT(
		QuickSummaryField.LOCATION,
		QuickSummaryField.PROPERTY_TYPE,
		QuickSummaryField.READY_TO_MOVE_IN,
		QuickSummaryField.PRICE,
		QuickSummaryField.VAT,
		QuickSummaryField.COVERED_AREA,
		QuickSummaryField.BEDROOMS,
		QuickSummaryField.LEVELS
	),

	SALE_LAND(
		QuickSummaryField.LOCATION,
		QuickSummaryField.PROPERTY_TYPE,
		QuickSummaryField.PRICE,
		QuickSummaryField.VAT,
		QuickSummaryField.PLOT_SIZE
	);

	private final QuickSummaryField[] fields;

	QuickSummaryGenerator(QuickSummaryField... fields) {
		this.fields = fields;
	}

	public String generateHtml(MessageSource ms, Property p) {
		StringBuilder dl = new StringBuilder();

		dl.append("<dl>");
		for (QuickSummaryField field : fields)
			dl.append(field.generateHtml(ms, p));
		dl.append("</dl>");

		return dl.toString();
	}
}
