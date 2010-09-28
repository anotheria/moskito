package net.java.dev.moskito.core.usecase;

/**
 * Calculation of use cases along same path.
 * @author lrosenberg
 *
 */
public class UseCasePath {
	/**
	 * Path of the request.
	 */
	private String path;
	/**
	 * Number of requests in this path.
	 */
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
