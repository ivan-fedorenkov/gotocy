package org.gotocy.domain;

import lombok.Getter;

import java.util.Map;

/**
 * A base class for all system notifications.
 *
 * @author ifedorenkov
 */
@Getter
public class Notification {

	private final String to;
	private final String subject;
	private final String message;
	private final String viewName;
	private final Map<String, Object> model;

	public Notification(String to, String subject, String message, String viewName, Map<String, Object> model) {
		this.to = to;
		this.subject = subject;
		this.message = message;
		this.viewName = viewName;
		this.model = model;
	}

	@Override
	public String toString() {
		return "Notification{" +
			"to='" + to + '\'' +
			", subject='" + subject + '\'' +
			", message='" + message + '\'' +
			", viewName='" + viewName + '\'' +
			", model=" + model +
			'}';
	}

}
