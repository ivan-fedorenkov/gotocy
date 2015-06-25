package org.gotocy.helpers;

import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.NumberPointType;
import org.thymeleaf.util.NumberUtils;

/**
 * A helper object for the view layer. Contains a number of utility methods, such as price formatting, etc.
 *
 * @author ifedorenkov
 */
public class Helper {

	/**
	 * Returns a string price representation adding the dollar symbol.
	 */
	public static String price(int price) {
		return "$ " + NumberUtils.format(price, 1, NumberPointType.COMMA, LocaleContextHolder.getLocale());
	}

}
