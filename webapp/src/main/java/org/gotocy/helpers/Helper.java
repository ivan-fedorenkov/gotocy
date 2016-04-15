package org.gotocy.helpers;

import org.gotocy.config.ApplicationProperties;
import org.gotocy.config.Locales;
import org.gotocy.domain.*;
import org.gotocy.forms.PropertiesSearchForm;
import org.gotocy.helpers.property.PropertyHelper;
import org.gotocy.helpers.propertysearch.PropertySearchFormHelper;
import org.gotocy.service.AssetsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

/**
 * A helper object that contains a number of utility methods such as price formatting, etc.
 *
 * @author ifedorenkov
 */
@Component
public class Helper {

	private final AssetsManager assetsManager;
	private final PropertyHelper propertyHelper;

	@Autowired
	public Helper(ApplicationProperties applicationProperties, AssetsManager assetsManager) {
		this.assetsManager = assetsManager;
		propertyHelper = new PropertyHelper(applicationProperties, assetsManager);
	}

	/**
	 * Exposes the property helper object.
	 */
	public PropertyHelper getProperty() {
		return propertyHelper;
	}

	/**
	 * Generates url for a given asset, using the configured {@link AssetsManager} instance.
	 */
	public String url(Asset asset) {
		return assetsManager.getPublicUrl(asset).orElse("");
	}

	/**
	 * Generate url for a given image asset, using the configured {@link AssetsManager} instance and the given image
	 * size.
	 */
	public String imageUrl(Image image, ImageSize size) {
		return assetsManager.getPublicUrl(image, size).orElse("");
	}

	/**
	 * Generates a list of urls for a given images collection, using the configured {@link AssetsManager} instance and
	 * the {@link ImageSize#BIG} size.
	 */
	public List<String> imageUrls(Collection<Image> assets) {
		return assets.stream().map(a -> imageUrl(a, ImageSize.BIG)).collect(toList());
	}

	/**
	 * Returns path of the given object using the current (thread local) locale.
	 * Unit test: HelperTest#entityPathTest
	 */
	public static String path(Object object) {
		return path(object, LocaleContextHolder.getLocale());
	}

	/**
	 * Returns path of the given object using the given locale.
	 * Unit test: HelperTest#entityPathTest
	 */
	public static String path(Object object, Locale locale) {
		String path;
		if (object instanceof Property) {
			path = PropertyHelper.path((Property) object, locale);
		} else if (object instanceof Complex) {
			path = "/complexes/" + ((Complex) object).getId();
		} else if (object instanceof Developer) {
			path = "/developers/" + ((Developer) object).getId();
		} else if (object instanceof PropertiesSearchForm) {
			path = PropertySearchFormHelper.path((PropertiesSearchForm) object, locale);
		} else if (object instanceof String) {
			path = (String) object;
		} else {
			throw new IllegalArgumentException("Unsupported object type: " + object.getClass());
		}
		return getPrefixForLocale(locale) + path;
	}

	/**
	 * Appends the page query param if necessary.
	 *
	 * @param uri that should be appended by the page query param
	 * @param page page number indexed from 0
	 *
	 * Unit test: HelperTest#appendPageTest
	 */
	public static String appendPage(String uri, int page) {
		return uri + (page > 0 ? uri.contains("?") ? "&" : "?" : "") + (page > 0 ? "page=" + page : "");
	}

	/**
	 * Encloses all the text subparts separated by the new line character into the p tags.
	 * Unit test: HelperTest#newLinesToParagraphs
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
	 * Unit test: HelperTest#pluralize
	 */
	public static String pluralize(String code, int number) {
		return number > 1 ? code + ".plural" : code;
	}

	private static String getPrefixForLocale(Locale locale) {
		if (Locales.RU.equals(locale)) {
			return "/ru";
		} else if (Locales.EL.equals(locale)) {
			return "/el";
		} else {
			return "";
		}
	}

	/**
	 * Creates pagination 'ul' as appropriate.
	 *
	 * @param page instance of {@link Page}
	 * @param object entity who's path is to be used for pagination construction
	 *
	 *
	 * TODO: unit test
	 * TODO: rewrite this
	 */
	public static String pagination(Page<?> page, Object object) {
		StringBuilder pagination = new StringBuilder();

		int currentPage = page.getNumber();
		int totalPages = page.getTotalPages();

		// This algorithm count pages from '1'
		currentPage += 1;

		if (totalPages > 1) {
			pagination.append("<ul class='pagination'>");
			for(int i = 1; i <= totalPages; i++) {
				if (totalPages > 9) {
					if ((i == 4) && (currentPage > 5)) {
						pagination.append("<li class='disabled'><a>...</a></li>");
						i = currentPage < totalPages ? currentPage - 1 : currentPage - 2;
					}
					else {
						if ((((currentPage > 5) && (i == (currentPage + 2))) || ((currentPage <= 5) && (i == 7))) &&
							(totalPages > (currentPage + 3)))
						{
							pagination.append("<li class='disabled'><a>...</a></li>");
							i = totalPages - 2;
						}
					}
				}

				if (i == currentPage) {
					pagination.append("<li class='active'>");
				} else {
					pagination.append("<li>");
				}
				pagination.append("<a href=\"").append(appendPage(path(object), i - 1)).append("\">").append(i)
					.append("</a>").append("</li>");
			}
			pagination.append("</ul>");
		}
		return pagination.toString();
	}

}
