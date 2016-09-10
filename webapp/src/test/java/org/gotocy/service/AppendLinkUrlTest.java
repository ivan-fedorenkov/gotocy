package org.gotocy.service;

import org.junit.Assert;
import org.junit.Test;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

/**
 * @author ifedorenkov
 */
public class AppendLinkUrlTest {

	@Test
	public void testAppendLinkUrl() {
		PolicyFactory sanitizer = new HtmlPolicyBuilder()
			.withPreprocessor(AppendLinkUrl::new)
			.toFactory();

		String sanitized = sanitizer.sanitize(
			"<a class='anything' href='http://www.example.com' disabled><span class='text-important'>link</span><br> title</a>");
		Assert.assertEquals("link title (http://www.example.com)", sanitized);
	}

}
