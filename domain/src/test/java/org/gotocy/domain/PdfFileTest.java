package org.gotocy.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class PdfFileTest {

	@Test
	public void getDisplayName() {
		PdfFile file = new PdfFile();

		file.setKey("abc.pdf");
		Assert.assertEquals("abc.pdf", file.getDisplayName());

		file.setKey("some_folder/abc.pdf");
		Assert.assertEquals("abc.pdf", file.getDisplayName());

		file.setKey(null);
		Assert.assertEquals("", file.getDisplayName());

		file.setKey("");
		Assert.assertEquals("", file.getDisplayName());

		file.setKey("some_folder/");
		Assert.assertEquals("", file.getDisplayName());

		file.setKey("/");
		Assert.assertEquals("", file.getDisplayName());
	}

}
