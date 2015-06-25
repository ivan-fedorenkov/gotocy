package org.gotocy.format;

import org.gotocy.domain.Location;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Formatter for property locations.
 *
 * @author ifedorenkov
 */
public class LocationFormatter implements Formatter<Location> {

	private final MessageSource messageSource;

	public LocationFormatter(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public Location parse(String locationStr, Locale locale) throws ParseException {
		try {
			return Location.valueOf(locationStr);
		} catch (IllegalArgumentException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}

	@Override
	public String print(Location location, Locale locale) {
		return messageSource.getMessage(location, locale);
	}

}
