package org.gotocy.helpers;

import org.gotocy.beans.AssetsProvider;
import org.gotocy.domain.*;
import org.gotocy.helpers.property.PropertyHelper;
import org.springframework.context.MessageSource;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A helper object for the view layer. Contains a number of utility methods, such as price formatting, etc.
 *
 * @author ifedorenkov
 */
public class Helper {

	private final AssetsProvider assetsProvider;
	private final PropertyHelper propertyHelper;

	public Helper(@NotNull MessageSource messageSource, @NotNull AssetsProvider assetsProvider) {
		this.assetsProvider = assetsProvider;
		propertyHelper = new PropertyHelper(messageSource);
	}

	/**
	 * Exposes the property helper object.
	 */
	public PropertyHelper getProperty() {
		return propertyHelper;
	}

	/**
	 * Generates url for a given asset, using the configured {@link AssetsProvider} instance.
	 * TODO: unit test
	 */
	public String url(Asset asset) {
		return assetsProvider.getUrl(asset);
	}

	/**
	 * Generate url for a given image asset, using the configured {@link AssetsProvider} instance and the given image
	 * size.
	 * TODO: unit test
	 */
	public String imageUrl(Image image, ImageSize size) {
		return assetsProvider.getImageUrl(image, size);
	}

	/**
	 * Generates a list of urls for a given images collection, using the configured {@link AssetsProvider} instance and
	 * the {@link ImageSize#BIG} size.
	 *
	 * TODO: unit test
	 */
	public List<String> imageUrls(Collection<Image> assets) {
		List<String> urls = new ArrayList<>(assets.size());
		assets.forEach(asset -> urls.add(imageUrl(asset, ImageSize.BIG)));
		return urls;
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

	private static String getPrefixForLanguage(String language) {
		return Objects.equals(language, "ru") ? "/ru" : "";
	}

}
