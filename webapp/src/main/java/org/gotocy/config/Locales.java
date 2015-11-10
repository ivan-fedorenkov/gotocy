package org.gotocy.config;

import java.util.*;

/**
 * The list of supported locales.
 *
 * @author ifedorenkov
 */
public interface Locales {

	Locale EN = Locale.ENGLISH;
	Locale RU = new Locale("ru");

	Locale DEFAULT = EN;

	Set<Locale> SUPPORTED = new HashSet<>(Arrays.asList(EN, RU));

}
