package org.gotocy.controllers.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect that ensures the required domain object is found.
 * Works only if the domain object is the first argument in a method.
 *
 * @author ifedorenkov
 */
@Aspect
public class RequiredDomainObjectAspect {

	@Pointcut("within(@org.springframework.stereotype.Controller org.gotocy.controllers..*)")
	public void controller() {}

	@Pointcut("execution(public * *(@org.gotocy.controllers.aop.RequiredDomainObject (org.gotocy.domain.*), ..)) && args(entity, ..)")
	public void markedTargetDomain(Object entity) {}

	@Before(value = "controller() && markedTargetDomain(entity)", argNames = "entity")
	public void handleDomainObjectNotFound(Object entity) throws DomainObjectNotFoundException {
		if (entity == null)
			throw new DomainObjectNotFoundException();
	}

}
