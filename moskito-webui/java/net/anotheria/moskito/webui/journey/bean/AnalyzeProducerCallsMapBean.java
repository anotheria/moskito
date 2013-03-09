package net.anotheria.moskito.webui.journey.bean;

import net.anotheria.moskito.webui.CurrentSelection;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This bean is used to perform analyze producer calls and order analyze producer calls bean by producer id.
 */
public class AnalyzeProducerCallsMapBean {
	/**
	 * Map with AnalyzeProducerCallsBean beans for each producer.
	 */
	private Map<String, AnalyzeProducerCallsBean> beans;
	/**
	 * Name of the analyzed part.
	 */
	private String name;

	/**
	 * Total number of calls in the analyzed part.
	 */
	private long totalCalls;
	/**
	 * Total duration of the analyzed part.
	 */
	private long totalDuration;
	
	public AnalyzeProducerCallsMapBean(String aName){
		name = aName;
		beans = new HashMap<String, AnalyzeProducerCallsBean>();
	}
	
	@Override public String toString(){
		return name + " "+beans;
	}

	/**
	 * Adds a new producer call. The duration and the number of calls for each producer will be increased accordingly.
	 * @param producerId id of the producer.
	 * @param duration duration of the call.
	 */
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
