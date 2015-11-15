package org.gotocy.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that should be thrown when the required domain object could not be found.
 *
 * @author ifedorenkov
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DomainObjectNotFoundException extends NotFoundException {
}
