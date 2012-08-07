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
	private Map<String, List<String>> stepsReversed = new HashMap<String, List<String>>();
	private List<TraceStepBean> elements = new ArrayList<TraceStepBean>();
	private int counter = 0;
	
	public JourneyCallIntermediateContainerBean(){
		
	}
	
	public void add(TraceStepBean step){
		elements.add(step);
		step.setId(""+(counter++));
		List<String> occurencies = stepsReversed.get(step.getCall());
		if (occurencies==null){
			occurencies = new ArrayList<String>();
			stepsReversed.put(step.getCall(), occurencies);
		}
		occurencies.add(step.getId());
	}
	
	public List<TraceStepBean> getElements(){
		return elements;
	}
	
	public Map<String, List<String>> getReversedSteps(){
		return stepsReversed;
	}
	
}
