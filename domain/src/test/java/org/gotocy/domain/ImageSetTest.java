package org.gotocy.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class ImageSetTest {

	@Test
	public void getImagesAsString() {

		ImageSet is = new ImageSet();

		Image im = new Image();
		im.setKey("firstImage");
		is.addImage(im);

		im = new Image();
		im.setKey("secondImage");
		is.addImage(im);

		im = new Image();
		im.setKey("thirdImage");
		is.addImage(im);

		Assert.assertEquals("firstImage\nsecondImage\nthirdImage", is.getImagesAsString());
	}

}
