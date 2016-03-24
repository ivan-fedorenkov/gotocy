package org.gotocy.format;

import org.springframework.format.number.NumberStyleFormatter;

/**
 * The currency formatter.
 *
 * @author ifedorenkov
 */
public class CurrencyFormatter extends NumberStyleFormatter {

	private static final String PATTERN = "#,###";

	public CurrencyFormatter() {
		super(PATTERN);
	}

}
