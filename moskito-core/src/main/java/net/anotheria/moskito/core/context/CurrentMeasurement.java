package net.anotheria.moskito.core.context;

import net.anotheria.moskito.core.entrypoint.ActiveMeasurement;
import net.anotheria.moskito.core.entrypoint.EntryPointRepository;
import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 15:52
 */
public class CurrentMeasurement {

	private boolean first;
	private IStatsProducer producer;

	private ActiveMeasurement entryPointMeasurement = null;

	CurrentMeasurement(IStatsProducer producer, boolean first){
		this.first = first;
		this.producer = producer;
		if (first){
			entryPointMeasurement = EntryPointRepository.getInstance().measurementStarted(producer);
		}
	}

	public boolean isFirst(){
		return first;
	}

	public void notifyProducerFinished() {
		if (first)
			EntryPointRepository.getInstance().measurementFinished(entryPointMeasurement);
	}

	public void setCallDescription(String description) {
		entryPointMeasurement.setCallDescription(description);
	}
}
