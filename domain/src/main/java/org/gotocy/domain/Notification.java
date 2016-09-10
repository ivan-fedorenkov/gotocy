package org.gotocy.domain;

import lombok.Getter;

import java.util.Locale;
import java.util.Map;

/**
 * A base class for all system notifications.
 *
 * @author ifedorenkov
 */
@Getter
public class Notification {

	private final String to;
	private final String templatePageUrl;
	private final Locale locale;
	private final Map<String, Object> model;

	public Notification(String to, String templatePageUrl, Locale locale, Map<String, Object> model) {
		this.to = to;
		this.templatePageUrl = templatePageUrl;
		this.locale = locale;
		this.model = model;
	}

	@Override
	public String toString() {
		return "Notification{" +
			"to='" + to + '\'' +
			", templatePageUrl='" + templatePageUrl + '\'' +
			", locale='" + locale + '\'' +
			", model=" + model +
			'}';
	}

}
