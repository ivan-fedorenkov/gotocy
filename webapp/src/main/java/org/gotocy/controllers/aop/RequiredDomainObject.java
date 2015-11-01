package org.gotocy.controllers.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that marks the first domain object as the required.
 * Works in conjunction with the {@link RequiredDomainObjectAspect} aspect.
 *
 * @author ifedorenkov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequiredDomainObject {
}
