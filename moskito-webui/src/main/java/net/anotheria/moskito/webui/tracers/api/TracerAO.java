package net.anotheria.moskito.webui.tracers.api;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:43
 */
public class TracerAO implements Serializable {
	private String producerId;
	private int entryCount;
	private boolean enabled;

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isDisabled(){
		return !isEnabled();
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
