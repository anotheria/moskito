package net.anotheria.moskito.core.exceptions;

public class ExcServiceDetailedException extends ExcServiceException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcServiceDetailedException(String message){
		super("Detailed exc: "+message);
	}
}
