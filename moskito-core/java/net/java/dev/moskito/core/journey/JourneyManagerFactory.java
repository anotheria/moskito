package net.java.dev.moskito.core.journey;

/**
 * The factory for the journey manager. 
 */
public class JourneyManagerFactory {
	/**
	 * Internally stored singleton instance.
	 */
	private static JourneyManager instance = new JourneyManagerImpl();
	/**
	 * Returns the singleton instance.
	 * @return
	 */
	public static JourneyManager getJourneyManager(){
		return instance;
	}
}
