package org.gotocy.service;

/**
 * Specific service layer {@link RuntimeException} with more meaningful name.
 *
 * @author ifedorenkov
 */
public class ServiceMethodExecutionException extends RuntimeException {

	public ServiceMethodExecutionException() {
	}

	public ServiceMethodExecutionException(String message) {
		super(message);
	}

	public ServiceMethodExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceMethodExecutionException(Throwable cause) {
		super(cause);
	}

	public ServiceMethodExecutionException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
