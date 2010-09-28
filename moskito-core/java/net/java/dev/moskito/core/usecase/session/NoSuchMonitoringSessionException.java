package net.java.dev.moskito.core.usecase.session;
/**
 * This exception is thrown if a non-existing session is requested from the manager.
 * @author lrosenberg.
 *
 */
public class NoSuchMonitoringSessionException extends Exception{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NoSuchMonitoringSessionException.
	 * @param name
	 */
	public NoSuchMonitoringSessionException(String name){
		super("No monitoring session with name: "+name);
	}
}
