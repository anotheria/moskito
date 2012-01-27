package net.anotheria.moskito.api.exception;

/**
 * Base moskito api runtime exception.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoAPIRuntimeException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2943000600954412537L;

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public MoskitoAPIRuntimeException(String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public MoskitoAPIRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Public constructor.
	 * 
	 * 
	 * @param message
	 *            - exception message
	 * @param cause
	 *            - exception cause
	 */
	public MoskitoAPIRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
