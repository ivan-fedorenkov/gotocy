package org.gotocy.format.seo;

import org.gotocy.config.Locales;
import org.junit.Test;

import java.text.ParseException;

/**
 * @author ifedorenkov
 */
public class SeoPropertySearchFormUrlFormatterTest {

	@Test
	public void testIt() throws ParseException {

		SeoPropertySearchFormUrlFormatter formatter = new SeoPropertySearchFormUrlFormatter();
		formatter.parse("houses-in-famagusta", Locales.EN);

	}

}
