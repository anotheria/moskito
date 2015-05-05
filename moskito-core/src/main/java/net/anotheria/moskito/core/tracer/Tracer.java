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

	public void addTrace(Trace aTrace){
		traces.add(aTrace);
	}

	public List<Trace> getTraces(){
		return traces;
	}
}
