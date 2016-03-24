package org.gotocy.format;

import org.springframework.format.number.NumberStyleFormatter;

/**
 * The distance formatter.
 *
 * @author ifedorenkov
 */
public class DistanceFormatter extends NumberStyleFormatter {

	private static final String PATTERN = "###.#";

	public DistanceFormatter() {
		super(PATTERN);
	}

}
