package net.java.dev.moskito.core.usecase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class UseCaseManager {
	
	private Map<String, UseCase> useCases;
		
	private static Logger log;
	
	static{
		log = Logger.getLogger(UseCaseManager.class);
	}
	
	UseCaseManager(){
		useCases = new ConcurrentHashMap<String,UseCase>();
	}
	
	public void addUseCase(String useCaseName){
		UseCase u = new UseCase(useCaseName);
		if (!useCases.containsKey(useCaseName))
			useCases.put(useCaseName, u);
		else
			log.warn("Trying to overwrite useCase: "+useCaseName);
			
	}
	
	public UseCase getUseCase(String useCaseName){
		return useCases.get(useCaseName);
	}
}
