package org.gotocy.format;

import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyType;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author ifedorenkov
 */
public class PropertyTypeFormatter implements Formatter<PropertyType> {

	private final MessageSource messageSource;

	public PropertyTypeFormatter(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public PropertyType parse(String propertyTypeStr, Locale locale) throws ParseException {
		try {
			return PropertyType.valueOf(propertyTypeStr);
		} catch (IllegalArgumentException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}

	@Override
	public String print(PropertyType propertyType, Locale locale) {
		return messageSource.getMessage(propertyType, locale);
	}
}
