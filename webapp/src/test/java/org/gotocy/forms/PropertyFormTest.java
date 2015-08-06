package org.gotocy.forms;

import org.gotocy.domain.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author ifedorenkov
 */
public class PropertyFormTest {

	@Test
	public void getSpecifications() {
		LocalizedProperty lp = new LocalizedProperty();

		LocalizedPropertySpecification spec = new LocalizedPropertySpecification();
		spec.setSpecification("first");
		lp.addSpecification(spec);

		spec = new LocalizedPropertySpecification();
		spec.setSpecification("second");
		lp.addSpecification(spec);

		spec = new LocalizedPropertySpecification();
		spec.setSpecification("third");
		lp.addSpecification(spec);

		PropertyForm form = new PropertyForm(lp, lp);

		Assert.assertEquals("first\nsecond\nthird", form.getRuSpecifications());
		Assert.assertEquals("first\nsecond\nthird", form.getEnSpecifications());
	}

	@Test
	public void getImages() {
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

		Property p = new Property();
		p.setImageSet(is);
		LocalizedProperty lp = new LocalizedProperty();
		lp.setProperty(p);

		PropertyForm form = new PropertyForm(lp, lp);

		Assert.assertEquals("firstImage\nsecondImage\nthirdImage", form.getImages());
	}

}
