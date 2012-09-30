package net.anotheria.moskito.core.usecase.recorder;

/**
 * Exception which is thrown if a non existing use-case is requested.
 * @author lrosenberg
 *
 */
public class NoSuchRecordedUseCaseException extends Exception{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of the exception.
	 * @param name
	 */
	public NoSuchRecordedUseCaseException(String name){
		super("No recorded use case with name: "+name);
	}
	
}
