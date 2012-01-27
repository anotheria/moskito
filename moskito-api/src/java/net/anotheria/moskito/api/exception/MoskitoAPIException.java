package net.anotheria.moskito.api.exception;

/**
 * Base moskito api exception.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoAPIException extends Exception {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = -6302392150639066807L;

	/**
	 * Public constructor.
	 * 
	 * @param message
	 *            - exception message
	 */
	public MoskitoAPIException(String message) {
		super(message);
	}

	/**
	 * Public constructor.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public MoskitoAPIException(Throwable cause) {
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
	public MoskitoAPIException(String message, Throwable cause) {
		super(message, cause);
	}

}
