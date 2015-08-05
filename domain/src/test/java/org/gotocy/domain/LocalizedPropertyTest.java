package org.gotocy.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ifedorenkov
 */
public class LocalizedPropertyTest {

	@Test
	public void getSpecificationsAsString() {
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

		Assert.assertEquals("first\nsecond\nthird", lp.getSpecificationsAsString());
	}

}
