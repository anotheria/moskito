package net.java.dev.moskito.webui.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;
import net.java.dev.moskito.webui.CurrentSelection;

/**
 * This beans contains analysis results for a single producer, it contains producer id, number of calls to this producer and the time spent.
 * @author lrosenberg
 *
 */
public class AnalyzeProducerCallsBean implements IComparable<AnalyzeProducerCallsBean> {
	/**
	 * ProducerId.
	 */
	private String producerId;
	/**
	 * Number of calls to this producer.
	 */
	private long numberOfCalls = 0;
	/**
	 * Total time spent in this producer.
	 */
	private long totalTimeSpent = 0;
	
	public AnalyzeProducerCallsBean(String aProducerId){
		producerId = aProducerId;
	}
	
	public void addCall(long duration){
		numberOfCalls++;
		totalTimeSpent+=duration;
	}
	
	@Override public String toString(){
		return numberOfCalls+" "+totalTimeSpent;
	}

	public long getNumberOfCalls() {
		return numberOfCalls;
	}

	public long getTotalTimeSpent() {
		return totalTimeSpent;
	}
	
	public long getTotalTimeSpentTransformed(){
		return CurrentSelection.get().getCurrentTimeUnit().transformNanos(totalTimeSpent);
	}
	
	public String getProducerId(){
		return producerId;
	}

	@Override
	public int compareTo(IComparable<? extends AnalyzeProducerCallsBean> anotherComparable,	int method) {
		AnalyzeProducerCallsBean anotherBean = (AnalyzeProducerCallsBean)anotherComparable;
		switch(method){
		case AnalyzeProducerCallsBeanSortType.SORT_BY_NAME:
			return BasicComparable.compareString(producerId, anotherBean.producerId);
		case AnalyzeProducerCallsBeanSortType.SORT_BY_CALLS:
			return BasicComparable.compareLong(numberOfCalls, anotherBean.numberOfCalls);
		case AnalyzeProducerCallsBeanSortType.SORT_BY_DURATION:
			return BasicComparable.compareLong(totalTimeSpent, anotherBean.totalTimeSpent);
		default: 
			throw new IllegalArgumentException("Unsupported method "+method);
		}
		
	}

}
