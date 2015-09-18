package org.gotocy.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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

	@Test
	public void updateCollectionTest() {
		CollectionClass preserved;
		List<CollectionClass> current = new ArrayList<>(Arrays.asList(
			preserved = new CollectionClass(1, "1"),
			new CollectionClass(2, "2"),
			new CollectionClass(3, "3")
		));

		List<CollectionClass> updated = new ArrayList<>(Arrays.asList(
			new CollectionClass(-1, "0"), // insert a new element before the first one
			new CollectionClass(-1, "1"), // stays the same
			new CollectionClass(-1, "2-updated"), // updated
			// new CollectionClass(3, "3"), // removed
			new CollectionClass(-1, "4") // insert a new element
		));

		Collection<? extends CollectionClass> result = CollectionUtils.updateCollection(current, updated);
		Assert.assertArrayEquals("Must become equal to the updated collection.", updated.toArray(), result.toArray());
		Assert.assertSame("Must not touch the same elements.", preserved, result.toArray()[1]);
	}

	/**
	 * The class which imitates domain object. It has an id and a key fields, but only key is the field
	 * that should be considered in {@link #equals(Object)} and {@link #hashCode()} methods.
	 */
	@Getter
	@Setter
	@ToString
	@AllArgsConstructor
	private static class CollectionClass {
		private int id;
		private String key;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			CollectionClass that = (CollectionClass) o;
			return Objects.equals(getKey(), that.getKey());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getKey());
		}
	}

}
