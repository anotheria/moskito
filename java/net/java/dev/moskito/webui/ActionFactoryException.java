package net.java.dev.moskito.webui;

public class ActionFactoryException extends Exception{
	public ActionFactoryException(Exception source){
		super("Action instantiation failed because: "+source.getMessage(), source);
	}
}
