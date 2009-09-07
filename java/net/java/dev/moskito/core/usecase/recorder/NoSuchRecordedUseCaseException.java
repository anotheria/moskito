package net.java.dev.moskito.core.usecase.recorder;

public class NoSuchRecordedUseCaseException extends Exception{
	public NoSuchRecordedUseCaseException(String name){
		super("No recorded use case with name: "+name);
	}
	
}
