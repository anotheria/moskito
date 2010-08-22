package net.java.dev.moskito.core.usecase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * Manager for use-cases.
 * @author lrosenberg
 *
 */
public class UseCaseManager {
	
	/**
	 * Stored use cases.
	 */
	private Map<String, UseCase> useCases;
		
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(UseCaseManager.class);
	
	
	/**
	 * Package protected constructor.
	 */
	UseCaseManager(){
		useCases = new ConcurrentHashMap<String,UseCase>();
	}
	/**
	 * Adds a use case.
	 * @param useCaseName
	 */
	public void addUseCase(String useCaseName){
		UseCase u = new UseCase(useCaseName);
		if (!useCases.containsKey(useCaseName))
			useCases.put(useCaseName, u);
		else
			log.warn("Trying to overwrite useCase: "+useCaseName);
			
	}
	/**
	 * Returns the use case by the use case name.
	 * @param useCaseName
	 * @return
	 */
	public UseCase getUseCase(String useCaseName){
		return useCases.get(useCaseName);
	}
}
