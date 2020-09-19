package net.anotheria.moskito.core.entrypoint;

/**
 * This class is used inside the PastMeasurementChainNode to be able to return a list of values without compromising the structure of the chain.
 *
 * @author lrosenberg
 * @since 18.09.20 23:29
 */
public class PastMeasurement extends ActiveMeasurement{

	private long endtime;
	private long duration;


	PastMeasurement(PastMeasurementChainNode node){
		super(node.getProducerId());

		setCallDescription(node.getDescription());
		setStarttime(node.getStarttime());

		endtime = node.getEndtime();
		duration = endtime - node.getStarttime();
		
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public long getDuration() {
		return duration;
	}

	
}
