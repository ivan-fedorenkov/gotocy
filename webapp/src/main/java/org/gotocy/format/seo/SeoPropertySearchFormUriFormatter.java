package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.gotocy.domain.Location;
import org.gotocy.domain.PropertyStatus;
import org.gotocy.domain.PropertyType;
import org.gotocy.format.Declension;
import org.gotocy.forms.PropertiesSearchForm;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

/**
 * Parses the request uri to an instance of the {@link PropertiesSearchForm} object.
 * Prints the uri prefix of the {@link PropertiesSearchForm} object.
 *
 * @author ifedorenkov
 */
public class SeoPropertySearchFormUriFormatter implements Formatter<PropertiesSearchForm> {

	private static final Pattern RU_FORM_PARAMS_PATTERN = Pattern.compile(
		"(" + Arrays.stream(SeoPropertyStatusFormatter.RU_STATUSES).map(status -> status + "-").collect(joining("|")) + ")?" +
		"(" + Arrays.stream(SeoPropertyTypeFormatter.RU_TYPES).collect(joining("|")) + ")" +
		"-(" + Arrays.stream(SeoLocationFormatter.RU_LOCATIONS).collect(joining("|")) + ")"
	);

	private static final Pattern FORM_PARAMS_PATTERN = Pattern.compile(
		"(" + Arrays.stream(SeoPropertyTypeFormatter.TYPES).collect(joining("|")) + ")" +
		"(" + Arrays.stream(SeoPropertyStatusFormatter.STATUSES).map(status -> "-" + status).collect(joining("|")) + ")?" +
		"-(" + Arrays.stream(SeoLocationFormatter.LOCATIONS).collect(joining("|")) + ")"
	);

	private static final SeoPropertyStatusFormatter PROPERTY_STATUS_FORMATTER = new SeoPropertyStatusFormatter();
	private static final SeoPropertyTypeFormatter PROPERTY_TYPE_FORMATTER = new SeoPropertyTypeFormatter();
	private static final SeoLocationFormatter LOCATION_FORMATTER = new SeoLocationFormatter();

	@Override
	public PropertiesSearchForm parse(String uri, Locale locale) throws ParseException {
		Pattern p = locale == Locales.RU ? RU_FORM_PARAMS_PATTERN : FORM_PARAMS_PATTERN;
		Matcher m = p.matcher(uri);
		if (!m.matches())
			throw new ParseException("Can't parse property form from the given uri string: " + uri, 0);

		PropertiesSearchForm form = new PropertiesSearchForm();
		if (locale == Locales.RU) {
			String propertyStatusStr = m.group(1);
			if (propertyStatusStr != null) {
				propertyStatusStr = propertyStatusStr.substring(0, propertyStatusStr.length() - 1);
				form.setPropertyStatus(PROPERTY_STATUS_FORMATTER.parse(propertyStatusStr, locale));
			}
			form.setPropertyType(PROPERTY_TYPE_FORMATTER.parse(m.group(2), locale));
			form.setLocation(LOCATION_FORMATTER.parse(m.group(3), locale));
		} else {
			form.setPropertyType(PROPERTY_TYPE_FORMATTER.parse(m.group(1), locale));
			String propertyStatusStr = m.group(2);
			if (propertyStatusStr != null) {
				propertyStatusStr = propertyStatusStr.substring(1);
				form.setPropertyStatus(PROPERTY_STATUS_FORMATTER.parse(propertyStatusStr, locale));
			}
			form.setLocation(LOCATION_FORMATTER.parse(m.group(3), locale));
		}
		return form;
	}

	@Override
	public String print(PropertiesSearchForm form, Locale locale) {
		StringJoiner uriJoiner = new StringJoiner("-");
		if (locale == Locales.RU) {
			if (form.getPropertyStatus() == null) {
				uriJoiner.add(PROPERTY_TYPE_FORMATTER.print(form.getPropertyType(), Declension.NOMINATIVE, locale));
			} else {
				uriJoiner.add(PROPERTY_STATUS_FORMATTER.print(form.getPropertyStatus(), locale));
				uriJoiner.add(PROPERTY_TYPE_FORMATTER.print(form.getPropertyType(), Declension.ACCUSATIVE, locale));
			}
			uriJoiner.add(LOCATION_FORMATTER.print(form.getLocation(), locale));
		} else {
			uriJoiner.add(PROPERTY_TYPE_FORMATTER.print(form.getPropertyType(), locale));
			if (form.getPropertyStatus() != null) {
				uriJoiner.add(PROPERTY_STATUS_FORMATTER.print(form.getPropertyStatus(), locale));
			}
			uriJoiner.add(LOCATION_FORMATTER.print(form.getLocation(), locale));
		}
		return uriJoiner.toString();
	}

}
