package org.gotocy.helpers.property;

import org.gotocy.domain.Property;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * A strategy that specifies the set of property fields to be formatted.
 *
 * @author ifedorenkov
 */
public enum FieldsStrategy {

	QUICK_SUMMARY(new QuickSummaryFieldsProvider()),
	LISTING_SUMMARY(new ListingSummaryFieldsProvider()),
	ADDITIONAL_INFO(new AdditionalInfoFieldsProvider());

	private final FieldsProvider fieldsProvider;

	FieldsStrategy(FieldsProvider fieldsProvider) {
		this.fieldsProvider = fieldsProvider;
	}

	/**
	 * Returns the formatted property fields.
	 */
	public List<FormattedField> getFormattedFields(MessageSource ms, Property p) {
		return Arrays.stream(fieldsProvider.getFields(p))
			.map(f -> new FormattedField(f.formatHeadingKey(ms, p), f.formatValue(ms, p)))
			.collect(toList());
	}

}
