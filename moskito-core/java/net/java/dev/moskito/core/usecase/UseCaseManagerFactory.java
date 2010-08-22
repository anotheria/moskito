package net.java.dev.moskito.core.usecase;

/**
 * Factory for the use-case manager.
 * @author lrosenberg
 *
 */
public class UseCaseManagerFactory {
	/**
	 * Manager singleton instance. 
	 */
	private static final UseCaseManager manager = new UseCaseManager();
	/**
	 * Returns the singleton instance of the UseCaseManager.
	 * @return
	 */
	public static final UseCaseManager getUseCaseManager(){
		return manager;
	}
}
