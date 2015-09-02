package org.gotocy.helpers.property;

/**
 * Formatted property field.
 *
 * @author ifedorenkov
 */
public class FormattedField {

	private final String heading;
	private final String value;

	public FormattedField(String heading, String value) {
		this.heading = heading;
		this.value = value;
	}

	public String getHeading() {
		return heading;
	}

	public String getValue() {
		return value;
	}
}
