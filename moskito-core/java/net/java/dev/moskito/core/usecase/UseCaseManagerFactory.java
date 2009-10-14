package net.java.dev.moskito.core.usecase;

public class UseCaseManagerFactory {
	private static final UseCaseManager manager = new UseCaseManager();
	
	public static final UseCaseManager getUseCaseManager(){
		return manager;
	}
}
