package net.anotheria.moskito.core.exceptions;

public class ExcServiceDetailedException extends ExcServiceException{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2751340169458879619L;

	public ExcServiceDetailedException(String message){
		super("Detailed exc: "+message);
	}
}
