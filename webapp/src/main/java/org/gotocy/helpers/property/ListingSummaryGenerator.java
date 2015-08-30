package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * DL listing summary generator.
 *
 * @author ifedorenkov
 */
enum ListingSummaryGenerator {

	LONG_TERM(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING,
		FieldFormat.HEATING_SYSTEM
	),

	SHORT_TERM(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.AIR_CONDITIONER,
		FieldFormat.DISTANCE_TO_SEA
	),

	SALE(
		FieldFormat.LOCATION,
		FieldFormat.PROPERTY_TYPE,
		FieldFormat.READY_TO_MOVE_IN,
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE,
		FieldFormat.BEDROOMS,
		FieldFormat.DISTANCE_TO_SEA
	);

	private final FieldFormat[] fields;

	ListingSummaryGenerator(FieldFormat... fields) {
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
