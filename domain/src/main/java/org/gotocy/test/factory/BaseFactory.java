package org.gotocy.test.factory;

import org.gotocy.domain.BaseEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ifedorenkov
 */
public abstract class BaseFactory<T> implements Supplier<T> {

	protected static final String ANY_STRING = "any_string";
	protected static final String ANY_EMAIL = "test@test.test";
	protected static final Integer ANY_INT = 1;
	protected static final Integer ZERO = 0;
	protected static final Integer NEGATIVE_INT = -1;
	protected static final Double ANY_DOUBLE = 1D;
	protected static final Boolean ANY_BOOLEAN = Boolean.FALSE;

	/**
	 * Creates the fully populated and valid entity instance and then applies the given preparer on that instance.
	 */
	public T get(Consumer<T> entityPreparer) {
		T entity = get();
		entityPreparer.accept(entity);
		return entity;
	}

}
