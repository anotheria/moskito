package net.java.dev.moskito.webui.action;

public class ActionForward {
	String name;
	String path;
	
	public ActionForward(String aName, String aPath){
		name = aName;
		path = aPath;
	}
	
	public String toString(){
		return name+"->"+path;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPath(){
		return path;
	}
}
