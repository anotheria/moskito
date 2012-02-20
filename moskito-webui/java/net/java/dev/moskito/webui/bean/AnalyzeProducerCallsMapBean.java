package net.java.dev.moskito.webui.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anotheria.util.sorter.StaticQuickSorter;
import net.java.dev.moskito.webui.CurrentSelection;

public class AnalyzeProducerCallsMapBean {
	private Map<String, AnalyzeProducerCallsBean> beans;
	private String name;
	
	private long totalCalls;
	private long totalDuration;
	
	public AnalyzeProducerCallsMapBean(String aName){
		name = aName;
		beans = new HashMap<String, AnalyzeProducerCallsBean>();
	}
	
	public String toString(){
		return name + " "+beans;
	}
	
	public void addProducerCall(String producerId, long duration){
		AnalyzeProducerCallsBean bean = beans.get(producerId);
		if (bean==null){
			bean = new AnalyzeProducerCallsBean(producerId);
			beans.put(producerId, bean);
		}
		bean.addCall(duration);
		
		totalCalls++;
		totalDuration+=duration;
	}
	
	public String getName(){
		return name;
	}
	
	public List<AnalyzeProducerCallsBean> getProducerCallsBeans(){
		return StaticQuickSorter.sort(beans.values(), CurrentSelection.get().getAnalyzeProducerCallsSortType());
	}
	
	public boolean isEmpty(){
		return beans == null || beans.size() == 0;
	}

	public long getTotalCalls() {
		return totalCalls;
	}

	public void setTotalCalls(long totalCalls) {
		this.totalCalls = totalCalls;
	}

	public long getTotalDuration() {
		return totalDuration;
	}
	
	public long getTotalDurationTransformed(){
		return CurrentSelection.get().getCurrentTimeUnit().transformNanos(getTotalDuration());
	}

	public void setTotalDuration(long totalDuration) {
		this.totalDuration = totalDuration;
	}
}
