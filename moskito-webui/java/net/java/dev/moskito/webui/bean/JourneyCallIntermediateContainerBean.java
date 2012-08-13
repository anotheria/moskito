package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class holds some information of the currently proceed (aka prepared for presentation) journey call.
 * It holds some intermediate results of the calculations. It dies after the request.
 * @author lrosenberg
 *
 */
public class JourneyCallIntermediateContainerBean {
	//private Map<String, List<String>> stepsReversed = new HashMap<String, List<String>>();
	private Map<String, ReversedCallHelper> stepsReversed = new HashMap<String, ReversedCallHelper>();
	private List<TraceStepBean> elements = new ArrayList<TraceStepBean>();
	private int counter = 0;
	private final ParentHolder parentHolder = new ParentHolder();
	
	public JourneyCallIntermediateContainerBean(){
		
	}
	
	public void add(TraceStepBean step){
		elements.add(step);
		step.setId((counter++));
		ReversedCallHelper helper = stepsReversed.get(step.getCall());
		if (helper == null){
			helper = new ReversedCallHelper();
			stepsReversed.put(step.getCall(), helper);
		}
		
		helper.add(step.getId(), step.getTimespent(), step.getDuration());
		step.setParentId(parentHolder.getParentIdByLayer(step.getLayer(), step.getId()));
	}
	
	public List<TraceStepBean> getElements(){
		return elements;
	}

	/**
	 * Returns the map with collected steps.
	 * @return
	 */
	public Map<String, ReversedCallHelper> getReversedSteps(){
		return stepsReversed;
	}
	
	public static class ReversedCallHelper{
		private List<String> positions = new ArrayList<String>();
		private long timespent = 0;
		private long duration = 0;
		
		public void add(String aPosition, long someTimespent, long aDuration){
			positions.add(aPosition);
			timespent += someTimespent;
			duration += aDuration;
		}
		
		public List<String> getPositions(){
			return positions;
		}
		
		public long getTimespent(){
			return timespent;
		}
		
		public long getDuration(){
			return duration;
		}
	}
	
	private class ParentHolder{
		
		private Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
		
		private final int getParentIdByLayer(int layer, String currentId){
			int _currentId = Integer.parseInt(currentId);
			parents.put(layer, _currentId);
			
			if (layer==0)
				return -1;

			int storedId = parents.get(layer-1);
			return storedId;
		}
	}
	
	
	
}
