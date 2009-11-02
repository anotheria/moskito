package net.java.dev.moskito.core.usecase;

public class UseCaseManagerFactory {
	/**
	 * Manager singleton instance. 
	 */
	private static final UseCaseManager manager = new UseCaseManager();
	
	public static final UseCaseManager getUseCaseManager(){
		return manager;
	}
}
