package org.gotocy.helpers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.ImageSize;
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
		return messageSource.getMessage(priceKey(property), new Object[] {price(property.getPrice())},
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

	private static String getPrefixForLanguage(String language) {
		return Objects.equals(language, "ru") ? "/ru" : "";
	}

}