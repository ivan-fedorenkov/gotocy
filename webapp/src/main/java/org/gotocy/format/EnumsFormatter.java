package org.gotocy.format;

import org.gotocy.i18n.I18n;
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

	private final Class<T> clazz;

	public EnumsFormatter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T parse(String str, Locale locale) throws ParseException {
		return Enum.<T>valueOf(clazz, str);
	}

	@Override
	public String print(T object, Locale locale) {
		return I18n.t(object);
	}

}
