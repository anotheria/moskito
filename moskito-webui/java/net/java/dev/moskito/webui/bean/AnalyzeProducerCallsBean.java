package net.java.dev.moskito.webui.bean;

public class AnalyzeProducerCallsBean {
	private String producerId;
	private long numberOfCalls = 0;
	private long totalTimeSpent = 0;
	
	public AnalyzeProducerCallsBean(String aProducerId){
		producerId = aProducerId;
	}
	
	public void addCall(long duration){
		numberOfCalls++;
		totalTimeSpent+=duration;
	}
	
	public String toString(){
		return numberOfCalls+" "+totalTimeSpent;
	}

	public long getNumberOfCalls() {
		return numberOfCalls;
	}

	public long getTotalTimeSpent() {
		return totalTimeSpent;
	}
	
	public String getProducerId(){
		return producerId;
	}

}
