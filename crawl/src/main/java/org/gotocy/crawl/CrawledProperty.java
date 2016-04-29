package org.gotocy.crawl;

import org.gotocy.domain.Property;

/**
 * Abstract base class for crawled property that could be converted to {@link org.gotocy.domain.Property}.
 *
 * @author ifedorenkov
 */
public abstract class CrawledProperty {

	private static final String SQM_SUFFIXES[] = {"m&sup2;", "m2"};

	/**
	 * Determines if the instance is supported (e.g. all the fields are correctly set).
	 */
	public abstract boolean isSupported();

	/**
	 * Converts the instance to {@link Property}.
	 */
	public abstract Property toProperty();

	/**
	 * Extracts the number from the given string.
	 */
	protected static int extractNumber(String numberString) {
		int number = 0;

		if (numberString == null || numberString.isEmpty())
			return number;

		for (int i = 0; i < numberString.length(); i++) {
			char c = numberString.charAt(i);
			if (c >= '0' && c <= '9')
				number = number * 10 + (c - '0');
		}

		return number;
	}

	/**
	 * Extracts sqm area from the given string.
	 */
	protected static int extractSQM(String sqm) {
		for (String sqmSuffix : SQM_SUFFIXES) {
			if (sqm.endsWith(sqmSuffix))
				sqm = sqm.substring(0, sqm.length() - sqmSuffix.length()).trim();
		}
		return extractNumber(sqm);
	}

}
