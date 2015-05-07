package net.anotheria.moskito.core.tracer;

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
		//int = 1 and not 0, we are copying one less than we could, because we want exactly max amount.
		for (int i=1; i<=maxAmount; i++) {
			traces.add(oldTraces.get(i + offset));
		}
	}

	public List<Trace> getTraces(){
		return traces;
	}
}
