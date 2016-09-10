package org.gotocy.service;

import org.owasp.html.HtmlStreamEventReceiver;
import org.owasp.html.HtmlStreamEventReceiverWrapper;
import org.owasp.html.HtmlTextEscapingMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Appends link href as a text after the "a" tags.
 * Unit test: AppendLinkUrlTest
 *
 * {@link org.owasp.html.examples.UrlTextExample}
 *
 * @author ifedorenkov
 */
public class AppendLinkUrl extends HtmlStreamEventReceiverWrapper {
	private final List<String> pendingText = new ArrayList<>();

	public AppendLinkUrl(HtmlStreamEventReceiver underlying) {
		super(underlying);
	}

	@Override
	public void openTag(String elementName, List<String> attrs) {
		underlying.openTag(elementName, attrs);

		String url = null;

		if ("a".equals(elementName)) {
			for (int i = 0; i < attrs.size(); i += 2) {
				if ("href".equals(attrs.get(i))) {
					url = attrs.get(i + 1);
					break;
				}
			}
		}

		if (!HtmlTextEscapingMode.isVoidElement(elementName))
			pendingText.add(url);
	}

	@Override
	public void closeTag(String elementName) {
		underlying.closeTag(elementName);
		int pendingTextSize = pendingText.size();
		if (pendingTextSize != 0) {
			String url = pendingText.remove(pendingTextSize - 1);
			if (url != null)
				text(" (" + url + ")");
		}
	}

}
