package org.gotocy.format;

import org.gotocy.config.Locales;
import org.gotocy.domain.Location;
import org.springframework.context.MessageSource;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class LocationFormatter extends EnumsFormatter<Location> {

	public LocationFormatter(MessageSource messageSource) {
		super(Location.class, messageSource);
	}

	@Override
	public Location parse(String localizedLocation, Locale l) throws ParseException {
		// Try to obtain location from the string using all the available translations
		for (Locale locale : Locales.SUPPORTED) {
			for (Location location : Location.values()) {
				if (messageSource.getMessage(location, locale).equals(localizedLocation))
					return location;
			}
		}

		// Failed, so fall back and try to obtain location from the enum name
		return super.parse(localizedLocation, l);
	}

}
