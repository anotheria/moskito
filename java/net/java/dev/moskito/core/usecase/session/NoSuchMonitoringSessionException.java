package net.java.dev.moskito.core.usecase.session;

public class NoSuchMonitoringSessionException extends Exception{
	public NoSuchMonitoringSessionException(String name){
		super("No monitoring session with name: "+name);
	}
}
