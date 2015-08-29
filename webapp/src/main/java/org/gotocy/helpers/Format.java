package org.gotocy.helpers;

import org.springframework.format.number.NumberStyleFormatter;

/**
 * Common formatters to be used in the various view layer helpers.
 *
 * @author ifedorenkov
 */
public class Format {

	public static final NumberStyleFormatter CURRENCY_FORMATTER = new NumberStyleFormatter("#,###");
	public static final NumberStyleFormatter DISTANCE_FORMATTER = new NumberStyleFormatter("###.#");

}
