package net.anotheria.moskito.webui.journey.api;

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
public class JourneyCallIntermediateContainer {
	/**
	 * The reversed map of steps used to determine duplicates.
	 */
	private Map<String, ReversedCallHelper> stepsReversed = new HashMap<>();
	/**
	 * Element in this countainer.
	 */
	private List<TracedCallStepAO> elements = new ArrayList<>();
	/**
	 * Counter.
	 */
	private int counter = 0;

	/**
	 * Parent holder ;-)
	 */
	private final ParentHolder parentHolder = new ParentHolder();
	
	public JourneyCallIntermediateContainer(){
		
	}

	/**
	 * Adds a new TracedCallStepAO.
	 * @param step
	 */
	public void add(TracedCallStepAO step){
		elements.add(step);
		step.setId((counter++));
		ReversedCallHelper helper = stepsReversed.get(step.getCall());
		if (helper == null){
			helper = new ReversedCallHelper();
			stepsReversed.put(step.getCall(), helper);
		}
		
		helper.add(step.getNiceId(), step.getTimespent(), step.getDuration());
		step.setParentId(parentHolder.getParentIdByLayer(step.getLayer(), step.getNiceId()));
	}
	
	public List<TracedCallStepAO> getElements(){
		return elements;
	}

	/**
	 * Returns the map with collected steps.
	 * @return
	 */
	public Map<String, ReversedCallHelper> getReversedSteps(){
		return stepsReversed;
	}
	
	/**
	 * A small helper for calculation of duplicates.
	 * @author lrosenberg
	 *
	 */
	public static class ReversedCallHelper{
		private List<String> positions = new ArrayList<>();
		/**
		 * Time spent in this call (overall incl. subcalls).
		 */
		private long timespent = 0;
		/**
		 * Duration of the call.
		 */
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
	
	/**
	 * Helper class for parentId linkage.
	 * @author lrosenberg
	 *
	 */
	static private class ParentHolder{
		/**
		 * Holds known parents.
 		 */
		private Map<Integer, Integer> parents = new HashMap<>();
		
		private int getParentIdByLayer(int layer, String currentId){
			int _currentId = Integer.parseInt(currentId);
			parents.put(layer, _currentId);
			
			if (layer==0)
				return -1;

			int storedId = parents.get(layer-1);
			return storedId;
		}
	}
	
	
	
}
