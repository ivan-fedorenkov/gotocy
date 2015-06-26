package org.gotocy.format;

import org.gotocy.domain.Location;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Enums formatter which is suitable for enums that implement {@link org.springframework.context.MessageSourceResolvable}
 * interface.
 *
 * @author ifedorenkov
 */
public class EnumsFormatter<T extends Enum<T> & MessageSourceResolvable> implements Formatter<T> {

	private final MessageSource messageSource;
	private final Class<T> clazz;

	public EnumsFormatter(Class<T> clazz, MessageSource messageSource) {
		this.clazz = clazz;
		this.messageSource = messageSource;
	}

	@Override
	public T parse(String str, Locale locale) throws ParseException {
		try {
			return Enum.<T>valueOf(clazz, str);
		} catch (IllegalArgumentException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}

	@Override
	public String print(T object, Locale locale) {
		return messageSource.getMessage(object, locale);
	}

}
