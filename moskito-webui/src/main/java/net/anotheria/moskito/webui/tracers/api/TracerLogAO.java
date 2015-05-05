package net.anotheria.moskito.webui.tracers.api;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:43
 */
public class TracerLogAO implements Serializable {
	private List<TracerCallLogAO> calls = new LinkedList<TracerCallLogAO>();

	public void addTracerCallLog(TracerCallLogAO call){
		calls.add(call);
	}


}
