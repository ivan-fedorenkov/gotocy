package org.gotocy.helpers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.NumberPointType;
import org.thymeleaf.util.NumberUtils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A helper object for the view layer. Contains a number of utility methods, such as price formatting, etc.
 *
 * @author ifedorenkov
 */
public class Helper {

	private static final Map<PropertyStatus, String> PROPERTY_STATUS_TO_PRICE_KEY = new HashMap<>();

	static {
		for (PropertyStatus status : PropertyStatus.values()) {
			PROPERTY_STATUS_TO_PRICE_KEY.put(status, "org.gotocy.domain.property." +
				status.name().toLowerCase().replaceAll("_", "-") + "-price");
		}
	}

	private final MessageSource messageSource;
	private final AssetsProvider assetsProvider;

	public Helper(@NotNull MessageSource messageSource, @NotNull AssetsProvider assetsProvider) {
		this.messageSource = messageSource;
		this.assetsProvider = assetsProvider;
	}

	/**
	 * Generates url for a given asset, using the configured {@link AssetsProvider} instance.
	 */
	public String url(Asset asset) {
		return assetsProvider.getUrl(asset);
	}

	/**
	 * Generate url for a given image asset, using the configured {@link AssetsProvider} instance and the given image
	 * size.
	 */
	public String imageUrl(Image image, ImageSize size) {
		return assetsProvider.getImageUrl(image, size);
	}

	/**
	 * Returns a string price representation adding the dollar symbol.
	 */
	public static String price(int price) {
		return "$ " + NumberUtils.format(price, 1, NumberPointType.COMMA, LocaleContextHolder.getLocale());
	}

	/**
	 * Returns a resource bundle key for price of the given property.
	 */
	public static String priceKey(Property property) {
		return PROPERTY_STATUS_TO_PRICE_KEY.get(property.getPropertyStatus());
	}

	/**
	 * Returns a fully formatted price of the given property.
	 * TODO: unit test
	 */
	public String price(Property property) {
		return messageSource.getMessage(priceKey(property), new Object[]{price(property.getPrice())},
			LocaleContextHolder.getLocale());
	}

	/**
	 * Returns path of a given entity object.
	 */
	public static <T extends BaseEntity> String path(T entity) {
		if (entity instanceof Property) {
			return "/property/" + entity.getId();
		} else if (entity instanceof LocalizedProperty) {
			LocalizedProperty lp = (LocalizedProperty) entity;
			return getPrefixForLanguage(lp.getLocale()) + path(lp.getProperty());
		}
		// TODO: log error
		return "";
	}

	/**
	 * Returns path of of a given entity object using the given language.
	 * The language should follow {@link java.util.Locale} rules.
	 */
	public static <T extends BaseEntity> String path(T entity, String language) {
		if (entity instanceof Property) {
			return getPrefixForLanguage(language) + "/property/" + entity.getId();
		} else if (entity instanceof LocalizedProperty) {
			return path(((LocalizedProperty) entity).getProperty(), language);
		}
		// TODO: log error
		return "";
	}

	/**
	 * Returns localized version of the given path.
	 */
	public static String path(String path, String language) {
		return getPrefixForLanguage(language) + path;
	}

	/**
	 * Encloses all the text subparts separated by the new line character into the p tags.
	 */
	public static String newLinesToParagraphs(String text) {
		// No text - no paragraphs
		if (text == null || text.isEmpty())
			return text;

		// Remove all the extra new lines at the beginning
		int n = 0;
		for (; n < text.length(); n++) {
			if (text.charAt(n) == '\n')
				continue;
			break;
		}
		text = n > 0 ? text.substring(n) : text;

		// Remove all the extra new lines at the end
		n = text.length() - 1;
		for (; n > 0; n--) {
			if (text.charAt(n) == '\n')
				continue;
			break;
		}
		text = n != text.length() - 1 ? text.substring(0, n + 1) : text;

		// No text - no paragraphs
		if (text.isEmpty())
			return text;

		return "<p>" + text.replaceAll("[\\n\\r]+", "</p><p>") + "</p>";
	}

	/**
	 * Returns 'pluralized' message code by adding the '.plural' ending to the given code in case of number is greater
	 * then one.
	 * E.g.: 'com.example.code' would be converted to 'com.example.code.plural' in case of number is greater then one
	 * and would stay the same in case of number is less then one.
	 */
	public static String pluralize(String code, int number) {
		return number > 1 ? code + ".plural" : code;
	}

	/**
	 * Returns the distance in the appropriate form, which is: < 500 m - meters (m), > 500 m - kilometers (km).
	 */
	public static String distance(int meters) {
		if (meters < 500) {
			return NumberUtils.format(meters, 1, NumberPointType.NONE, LocaleContextHolder.getLocale()) + " m";
		} else {
			return NumberUtils.format(meters / 1000d, 1, NumberPointType.NONE, 1, NumberPointType.POINT,
				LocaleContextHolder.getLocale()) + " km";
		}
	}

	private static String getPrefixForLanguage(String language) {
		return Objects.equals(language, "ru") ? "/ru" : "";
	}

}
