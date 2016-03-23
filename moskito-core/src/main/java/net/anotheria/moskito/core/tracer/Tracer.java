package net.anotheria.moskito.core.tracer;

import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 04.05.15 17:40
 */
public class Tracer {
	private String producerId;
	private boolean enabled;

	private static JourneyManager journeyManager = JourneyManagerFactory.getJourneyManager();

	private List<Trace> traces;

	public Tracer(String aProducerId){
		producerId = aProducerId;
		enabled = true;
		traces = new CopyOnWriteArrayList<Trace>();
	}

	public String getProducerId(){
		return producerId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getEntryCount(){
		return traces == null ? 0 : traces.size();
	}

	public void addTrace(Trace aTrace, int toleratedAmount, int maxAmount){
		traces.add(aTrace);
		if (traces.size() <= toleratedAmount)
			return;
		List<Trace> oldTraces = traces;
		traces = new CopyOnWriteArrayList<Trace>();
		int offset = toleratedAmount - maxAmount;

		for (int i=0; i<oldTraces.size(); i++){
			if (i>=(1+offset) && i<=(maxAmount+offset)){
				traces.add(oldTraces.get(i));
			}else{
				journeyManager.getOrCreateJourney(Tracers.getJourneyNameForTracers()).removeStepByName(Tracers.getCallName(oldTraces.get(i)));
			}
		}

	}

	public List<Trace> getTraces(){
		return traces;
	}
}
