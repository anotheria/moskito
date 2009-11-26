package net.java.dev.moskito.webcontrol.configuration;

public class SourceConfiguration {
	private String name;
	private String url;
	
	public SourceConfiguration(String aName, String anUrl){
		name = aName;
		url  = anUrl;
	}
	
	public String toString(){
		return name + " = "+ url;
	}
	
	public String getName(){
		return name;
	}
	
	public String getUrl(){
		return url; 
	}
	
}
