package org.gotocy.domain.validation;

/**
 * Common validation constraints.
 *
 * @author ifedorenkov
 */
public interface ValidationConstraints {
	String NOT_EMPTY = "org.gotocy.validation.constraints.NotEmpty.message";
	String POSITIVE_INT = "org.gotocy.validation.constraints.PositiveInt.message";
	String MAX = "org.gotocy.validation.constraints.Max.message";
	String MAX_SIZE = "org.gotocy.validation.constraints.MaxSize.message";
	String CONTENT_TYPE = "org.gotocy.validation.constraints.ContentType.message";
	String NON_UNIQUE = "org.gotocy.validation.constraints.NonUnique.message";
}
