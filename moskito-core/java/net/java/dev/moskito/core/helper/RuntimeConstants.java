package net.java.dev.moskito.core.helper;

public class RuntimeConstants {
	private static String applicationName = "";
	
	public static final String getApplicationName(){
		return applicationName;
	}
	
	public static final void setApplicationName(String value){
		applicationName = value;
	}
}
