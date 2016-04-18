package org.gotocy.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for access denied condition.
 *
 * @author ifedorenkov
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {
}
