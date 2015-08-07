package org.gotocy.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.gotocy.utils.CollectionUtils.collectionsEquals;

/**
 * @author ifedorenkov
 */
public class CollectionUtilsTest {

	@Test
	public void collectionsEqualsTest() {

		// Null collections are equals
		Assert.assertTrue(collectionsEquals(null, null));

		List<String> left = new ArrayList<>();
		List<String> right = new ArrayList<>();

		// Empty collections are equals
		Assert.assertTrue(collectionsEquals(left, right));

		// If any collection is null and other is not null, collections are not equals
		Assert.assertFalse(collectionsEquals(left, null));
		Assert.assertFalse(collectionsEquals(null, right));

		// And some more examples

		left.add("one");
		right.add("one");
		Assert.assertTrue(collectionsEquals(left, right));

		right.add("two");
		Assert.assertFalse(collectionsEquals(left, right));

		left.add("two");
		Assert.assertTrue(collectionsEquals(left, right));

	}

}
