package org.gotocy.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Utility methods for collections.
 * TODO: unit tests
 *
 * @author ifedorenkov
 */
public class CollectionUtils {

	private CollectionUtils() {
	}

	/**
	 * Determines if the given collections and their elements are equal.
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

}
