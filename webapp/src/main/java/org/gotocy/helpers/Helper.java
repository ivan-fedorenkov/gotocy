package org.gotocy.helpers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.beans.ImageSize;
import org.gotocy.domain.Asset;
import org.gotocy.domain.BaseEntity;
import org.gotocy.domain.LocalizedProperty;
import org.gotocy.domain.Property;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.NumberPointType;
import org.thymeleaf.util.NumberUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A helper object for the view layer. Contains a number of utility methods, such as price formatting, etc.
 *
 * @author ifedorenkov
 */
public class Helper {

	private final AssetsProvider assetsProvider;

	public Helper(@NotNull AssetsProvider assetsProvider) {
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
	public String imageUrl(Asset asset, ImageSize size) {
		return assetsProvider.getImageUrl(asset, size);
	}

	/**
	 * Returns a string price representation adding the dollar symbol.
	 */
	public static String price(int price) {
		return "$ " + NumberUtils.format(price, 1, NumberPointType.COMMA, LocaleContextHolder.getLocale());
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

	private static String getPrefixForLanguage(String language) {
		return Objects.equals(language, "ru") ? "/ru" : "";
	}

}
