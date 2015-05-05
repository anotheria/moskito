package net.anotheria.moskito.core.tracer;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 04.05.15 17:40
 */
public class Tracer {
	private String producerId;
	private boolean enabled;

	private List<Object> traces;

	public Tracer(String aProducerId){
		producerId = aProducerId;
		enabled = true;
		traces = new LinkedList<Object>();
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
}
