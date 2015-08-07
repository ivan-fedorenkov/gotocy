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
	public void setSpecifications() {
		String specifications = "first\n\rsecond\n\rthird";

		LocalizedProperty ruLP = new LocalizedProperty();
		LocalizedProperty enLP = new LocalizedProperty();

		PropertyForm form = new PropertyForm(ruLP, enLP);

		form.setEnSpecifications(specifications);
		form.setRuSpecifications(specifications);

		LocalizedPropertySpecification[] expected = new LocalizedPropertySpecification[3];
		expected[0] = new LocalizedPropertySpecification();
		expected[0].setSpecification("first");
		expected[1] = new LocalizedPropertySpecification();
		expected[1].setSpecification("second");
		expected[2] = new LocalizedPropertySpecification();
		expected[2].setSpecification("third");

		Assert.assertArrayEquals(expected, enLP.getSpecifications().toArray());
		Assert.assertArrayEquals(expected, ruLP.getSpecifications().toArray());
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

	@Test
	public void setImages() {
		String images = "firstImagePath\n\rsecondImagePath";

		Property p = new Property();
		ImageSet is = new ImageSet();
		p.setImageSet(is);
		LocalizedProperty lp = new LocalizedProperty();
		lp.setProperty(p);

		PropertyForm form = new PropertyForm(lp, lp);

		Image[] expected = new Image[2];
		expected[0] = new Image();
		expected[0].setKey("firstImagePath");
		expected[1] = new Image();
		expected[1].setKey("secondImagePath");

		form.setImages(images);

		Assert.assertArrayEquals(expected, is.getImages().toArray());
	}

}
