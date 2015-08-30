package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

/**
 * Additional info UL generator.
 *
 * @author ifedorenkov
 */
enum AdditionalInfoGenerator {

	LONG_TERM(
		FieldFormat.BEDROOMS,
		FieldFormat.FURNISHING
	),

	SHORT_TERM(
		FieldFormat.BEDROOMS,
		FieldFormat.GUESTS,
		FieldFormat.DISTANCE_TO_SEA_SHORT
	),

	SALE(
		FieldFormat.COVERED_AREA,
		FieldFormat.PLOT_SIZE
	);

	private final FieldFormat[] fields;

	AdditionalInfoGenerator(FieldFormat... fields) {
		this.fields = fields;
	}

	public String generateHtml(MessageSource ms, Property p) {
		StringBuilder ul = new StringBuilder();

		for (FieldFormat field : fields) {
			ul.append("<li>");
			ul.append("<header>").append(field.formatHeadingKey(ms, p)).append(":").append("</header>");
			ul.append("<figure>").append(field.formatValue(ms, p)).append("</figure>");
			ul.append("</li>");
		}

		return ul.toString();
	}

}
