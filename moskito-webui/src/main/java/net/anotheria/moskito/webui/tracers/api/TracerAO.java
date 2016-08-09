package net.anotheria.moskito.webui.tracers.api;

import java.io.Serializable;

/**
 * This class represents a tracer from APIs point of view.
 *
 * @author lrosenberg
 * @since 05.05.15 00:43
 */
public class TracerAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id of the producer this Tracer is bound to.
	 */
	private String producerId;
	/**
	 * Number of recorded traces.
	 */
	private int entryCount;
	/**
	 * State of the Tracer -> enabled or disabled. Disabled tracer doesn't collect any traces.
	 */
	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isDisabled(){
        return !enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public void setEntryCount(int entryCount) {
		this.entryCount = entryCount;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
}
