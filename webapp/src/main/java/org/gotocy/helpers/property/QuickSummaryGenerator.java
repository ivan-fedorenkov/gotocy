package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * DL quick summary generator.
 *
 * @author ifedorenkov
 */
enum QuickSummaryGenerator {
	LONG_TERM(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.PRICE,
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING,
		FieldFormat.HEATING_SYSTEM
	),

	SHORT_TERM (
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.RENTAL_TYPE,
		FieldFormat.PRICE,
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.AIR_CONDITIONER,
		FieldFormat.DISTANCE_TO_SEA
	),

	SALE_HOUSE(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	),

	SALE_APARTMENT(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.COVERED_AREA,
		FieldFormat.BEDROOMS,
		FieldFormat.LEVELS
	),

	SALE_LAND(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.PRICE,
		FieldFormat.VAT,
		FieldFormat.PLOT_SIZE
	);

	private final FieldFormat[] fields;

	QuickSummaryGenerator(FieldFormat... fields) {
		this.fields = fields;
	}

	public String generateHtml(MessageSource ms, Property p) {
		StringBuilder dl = new StringBuilder();

		for (FieldFormat field : fields) {
			dl.append("<dt>").append(field.formatHeadingKey(ms, p)).append(":").append("</dt>");
			dl.append("<dd>").append(field.formatValue(ms, p)).append("</dd>");
		}

		return dl.toString();
	}
}
