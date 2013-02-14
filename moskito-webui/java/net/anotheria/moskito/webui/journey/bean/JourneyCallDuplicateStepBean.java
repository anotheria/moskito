package net.anotheria.moskito.webui.journey.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.util.List;

/**
 * This bean represents a duplicate step during a journey call. 
 * @author lrosenberg
 *
 */
public class JourneyCallDuplicateStepBean implements IComparable<JourneyCallDuplicateStepBean>{
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
			IComparable<? extends JourneyCallDuplicateStepBean> anotherObject,
			int method) {
		switch(method){
		case JourneyCallDuplicateStepBeanSortType.SORT_BY_CALL:
			return BasicComparable.compareString(call, ((JourneyCallDuplicateStepBean)anotherObject).call);
		case JourneyCallDuplicateStepBeanSortType.SORT_BY_POSITIONS:
			return BasicComparable.compareList(positions, ((JourneyCallDuplicateStepBean)anotherObject).positions);
		case JourneyCallDuplicateStepBeanSortType.SORT_BY_TIMESPENT:
			return BasicComparable.compareLong(timespent, ((JourneyCallDuplicateStepBean)anotherObject).timespent);
		case JourneyCallDuplicateStepBeanSortType.SORT_BY_DURATION:
			return BasicComparable.compareLong(duration, ((JourneyCallDuplicateStepBean)anotherObject).duration);
		default:
			throw new IllegalArgumentException("Unsupported method: "+method);
		}
	}
}
