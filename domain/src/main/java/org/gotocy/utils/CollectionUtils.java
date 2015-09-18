package org.gotocy.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Utility methods for collections.
 *
 * @author ifedorenkov
 */
public class CollectionUtils {

	private CollectionUtils() {
	}

	/**
	 * Determines if the given collections and their elements are equal.
	 * Unit test: CollectionUtilsTest#collectionsEqualsTest
	 */
	public static <T> boolean collectionsEquals(Collection<? extends T> left, Collection<? extends T> right) {
		if (left == right)
			return true;
		if (left == null || right == null)
			return false;
		if (left.size() != right.size())
			return false;

		boolean equals = true;

		Iterator<? extends T> leftIt = left.iterator();
		Iterator<? extends T> rightIt = right.iterator();

		while (leftIt.hasNext())
			equals &= Objects.equals(leftIt.next(), rightIt.next());

		return equals;
	}

	/**
	 * Updates the given {@param current} collection to the state of the {@param updated} collection, preserving
	 * the existing collection elements if they are equals.
	 * Motivation: reduce the number of delete and insert operations in the database.
	 * Unit test: CollectionUtilsTest#updateCollectionTest
	 *
	 * @param current current collection
	 * @param updated updated collection
	 * @return the updated current collection, the returning value may be ignored.
	 */
	public static <T> Collection<T> updateCollection(Collection<T> current, Collection<T> updated) {
		T[] curArr = (T[]) current.toArray();
		T[] updArr = (T[]) updated.toArray();

		for (int i = 0; i < updArr.length; i++) {
			for (int j = 0; j < curArr.length; j++) {
				if (Objects.equals(updArr[i], curArr[j])) {
					updArr[i] = curArr[j];
					break;
				}
			}
		}

		current.clear();
		current.addAll(Arrays.asList(updArr));
		return current;
	}

}
