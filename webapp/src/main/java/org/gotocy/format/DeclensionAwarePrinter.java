package org.gotocy.format;

import org.springframework.format.Printer;

import java.util.Locale;

/**
 * Printer that is aware of different object declensions.
 *
 * @author ifedorenkov
 */
public interface DeclensionAwarePrinter<T> extends Printer<T> {

	/**
	 * Prints the given object using the appropriate declension.
	 */
	String print(T object, Declension declension, Locale locale);

}
