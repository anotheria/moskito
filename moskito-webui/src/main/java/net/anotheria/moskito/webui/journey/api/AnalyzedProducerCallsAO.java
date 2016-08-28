package net.anotheria.moskito.webui.journey.api;

import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.io.Serializable;

/**
 * This bean contains analysis results for a single producer, it contains producer id, number of calls to this producer and the time spent.
 * @author lrosenberg
 *
 */
public class AnalyzedProducerCallsAO implements IComparable<AnalyzedProducerCallsAO> , Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -1915426639945537782L;

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
	
	public AnalyzedProducerCallsAO(String aProducerId){
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
		return MoSKitoWebUIContext.getCallContext().getCurrentTimeUnit().transformNanos(totalTimeSpent);
	}
	
	public String getProducerId(){
		return producerId;
	}

	@Override
	public int compareTo(IComparable<? extends AnalyzedProducerCallsAO> anotherComparable,	int method) {
		AnalyzedProducerCallsAO anotherBean = (AnalyzedProducerCallsAO)anotherComparable;
		switch(method){
		case AnalyzedProducerCallsAOSortType.SORT_BY_NAME:
			return BasicComparable.compareString(producerId, anotherBean.producerId);
		case AnalyzedProducerCallsAOSortType.SORT_BY_CALLS:
			return BasicComparable.compareLong(numberOfCalls, anotherBean.numberOfCalls);
		case AnalyzedProducerCallsAOSortType.SORT_BY_DURATION:
			return BasicComparable.compareLong(totalTimeSpent, anotherBean.totalTimeSpent);
		default: 
			throw new IllegalArgumentException("Unsupported method "+method);
		}
		
	}

}
