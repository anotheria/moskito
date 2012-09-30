package net.anotheria.moskito.core.journey;
/**
 * This exception is thrown if a non-existing session is requested from the manager.
 * @author lrosenberg.
 *
 */
public class NoSuchJourneyException extends Exception{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new NoSuchMonitoringSessionException.
	 * @param name
	 */
	public NoSuchJourneyException(String name){
		super("No monitoring session with name: "+name);
	}
}
