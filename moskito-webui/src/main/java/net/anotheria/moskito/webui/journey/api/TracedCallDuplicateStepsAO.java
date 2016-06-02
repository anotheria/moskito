package net.anotheria.moskito.webui.journey.api;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.io.Serializable;
import java.util.List;

/**
 * This bean represents a duplicate step during a journey call. 
 * @author lrosenberg
 *
 */
public class TracedCallDuplicateStepsAO implements IComparable<TracedCallDuplicateStepsAO>, Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Call description aka method name and parameters.
	 */
	private String call;
	/**
	 * Positions in journey call it occures at.
	 */
	private List<String> positions;
	
	/**
	 * Total duration of the calls in this duplicate.
	 */
	private long duration;
	
	/**
	 * Total time spent int the calls in this duplicate.
	 */
	private long timespent;

	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}
	public List<String> getPositions() {
		return positions;
	}
	public void setPositions(List<String> positions) {
		this.positions = positions;
	}
	
	public int getNumberOfCalls(){
		return positions.size();
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getTimespent() {
		return timespent;
	}
	public void setTimespent(long timespent) {
		this.timespent = timespent;
	}
	@Override
	public int compareTo(
			IComparable<? extends TracedCallDuplicateStepsAO> anotherObject,
			int method) {
		switch(method){
		case TracedCallDuplicateStepsAOSortType.SORT_BY_CALL:
			return BasicComparable.compareString(call, ((TracedCallDuplicateStepsAO)anotherObject).call);
		case TracedCallDuplicateStepsAOSortType.SORT_BY_POSITIONS:
			return BasicComparable.compareList(positions, ((TracedCallDuplicateStepsAO)anotherObject).positions);
		case TracedCallDuplicateStepsAOSortType.SORT_BY_TIMESPENT:
			return BasicComparable.compareLong(timespent, ((TracedCallDuplicateStepsAO)anotherObject).timespent);
		case TracedCallDuplicateStepsAOSortType.SORT_BY_DURATION:
			return BasicComparable.compareLong(duration, ((TracedCallDuplicateStepsAO)anotherObject).duration);
		default:
			throw new IllegalArgumentException("Unsupported method: "+method);
		}
	}
}
