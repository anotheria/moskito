package net.java.dev.moskito.core.usecase;

public class UseCasePath {
	private String path;
	
	private long requestCount;
	
	public UseCasePath(String aPath){
		path = aPath;
	}
	
	public String toString(){
		return "path: "+path+", count: "+requestCount;
	}
	
	public void addProcessedUseCase(){
		requestCount++;
	}
	
	
}
