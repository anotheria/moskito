package net.anotheria.moskito.core.entrypoint;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a chain of stored last measurements.
 * WARNING: This class is not YET thread safe.
 *
 * @author lrosenberg
 * @since 17.09.20 23:58
 */
public class PastMeasurementChainNode {

	/**
	 * Should be configureable at some point, max number of links in the chain.
	 */
	public static final int MAX_LENGTH = 10;

	/**
	 * Starttime of the measurement.
	 */
	private long starttime;
	/**
	 * Endtime of the measurement.
	 */
	private long endtime;
	/**
	 * Duration of the measuement in ms.
	 */
	private long duration;
	/**
	 * Call description.
	 */
	private String description;

	private String producerId;

	private PastMeasurementChainNode next;

	public PastMeasurementChainNode(){
		
	}

	public PastMeasurementChainNode(ActiveMeasurement measurement){
		starttime = measurement.getStartTime();
		endtime   = System.currentTimeMillis();
		duration = endtime - starttime;
		producerId = measurement.getProducerId();
		description = measurement.getDescription();
	}

	public int getLength(){
		return next == null ? 1 : next.getLength() + 1;
	}

	public PastMeasurementChainNode add(PastMeasurementChainNode anotherNode){
		if (next==null)
			next = anotherNode;
		else
			next.add(anotherNode);
		return this;
	}

	public static PastMeasurementChainNode addToChainIfLongerDuration(PastMeasurementChainNode chain, PastMeasurementChainNode aNode){
		//we have following cases,
		// 1 there is no chain yet - the new node becomes the chain.
		// 2 the new node has shortest duration, but the chain is to short, so its added at the end.
		// 3 the new node has longest duration and must become chain head
		// 4 the new node is inserted somewhere in the chain.
		// 5 new node is too short (duration) and falls through

		if (aNode==null)
			throw new AssertionError("Can't insert null nodes");

		//case 1
		if (chain == null)
			return aNode;

		//case 3
		if (aNode.getDuration()> chain.getDuration()){
			//this will be our new node.
			aNode.next = chain;
			chain = aNode;
			chain.loseLast(MAX_LENGTH);
		}else{
			//case 4 & 5
			if (chain.insertIfLongerAndWithinLimit(MAX_LENGTH, aNode))
				chain.loseLast(MAX_LENGTH);
		}

		return chain;
		
	}

	/**
	 * Inserts the given node at the proper sorted position.
	 * Return true if the chain was modified.
	 * @param limit
	 * @param aNode
	 * @return
	 */
	private boolean insertIfLongerAndWithinLimit(int limit,PastMeasurementChainNode aNode ){
		//System.out.println("Inserting at limit "+limit);
		//if we out of limit, just skip.
		if (limit == 0)
			return false;
		if (next == null) {
			next = aNode;
			return true;
		}

		if (next.getDuration() < aNode.getDuration()){
			aNode.next = next;
			next = aNode;
			return true;
		}else{
			return next.insertIfLongerAndWithinLimit(limit -1, aNode);
		}
		
	}

	/**
	 * Checks if the chain is longer then given length and cuts all excessive elements.
	 * Usually this method is called directly after a modification, so there should be only one node to cut.
	 * @param currentLength
	 */
	private void loseLast(int currentLength){
		if (currentLength>1){
			if (next==null)
				return;
			next.loseLast(currentLength-1);
		}else{
			next = null;
		}
	}

	public long getStarttime() {
		return starttime;
	}

	public long getEndtime() {
		return endtime;
	}

	public long getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	public String getProducerId() {
		return producerId;
	}

	void unitTestSetEndTime(long endtimeValue){
		endtime = endtimeValue;
		duration = endtime-starttime;

	}

	public PastMeasurementChainNode getNthElement(int i) {
		return i == 0 ?
				this : next.getNthElement(i-1);
	}

	public List<PastMeasurement> getMeasurements(){
		LinkedList<PastMeasurement> ret = new LinkedList<>();
		return addSelfToList(ret);
	}

	private List<PastMeasurement> addSelfToList(List<PastMeasurement> list){
		list.add(new PastMeasurement(this));
		return next == null ? list : next.addSelfToList(list);
	}

	@Override
	public String toString() {
		return "PastMeasurementChainNode{" +
				"duration=" + duration +
				", description='" + description + '\'' +
				", super "+super.toString()+
				'}';
	}
}
