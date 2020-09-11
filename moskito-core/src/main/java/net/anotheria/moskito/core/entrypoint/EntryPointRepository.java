package net.anotheria.moskito.core.entrypoint;

import net.anotheria.moskito.core.producers.IStatsProducer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 15:57
 */
public class EntryPointRepository {


	private static EntryPointRepository INSTANCE = new EntryPointRepository();

	private ConcurrentMap<String, EntryPoint> entryPoints;

	private EntryPointRepository(){
		entryPoints = new ConcurrentHashMap<>();
	}

	public static EntryPointRepository getInstance(){
		return INSTANCE;
	}

	public ActiveMeasurement measurementStarted(IStatsProducer producer){
		System.out.println("Measurement started in "+producer);
		String producerId = producer.getProducerId();

		EntryPoint newEntryPoint = new EntryPoint(producerId);
		EntryPoint oldEntryPoint = entryPoints.putIfAbsent(producerId, newEntryPoint);
		EntryPoint entryPoint;
		if (oldEntryPoint == null){
			entryPoint = newEntryPoint;
		}else{
			entryPoint = oldEntryPoint;
		}

		entryPoint.requestStarted();
		System.out.println("EP "+entryPoint);

		ActiveMeasurement ret = new ActiveMeasurement(producerId);

		return ret;
	}

	public void measurementFinished(ActiveMeasurement measurement){

		EntryPoint entryPoint = entryPoints.get(measurement.getProducerId());
		//we assume that entry point can't be null! After all it was just recently created.


		System.out.println("Measurement ended in "+measurement);
		entryPoint.requestFinished();

		System.out.println("EP "+entryPoint);
	}

	public List<EntryPoint> getEntryPoints() {
		LinkedList<EntryPoint> ret = new LinkedList<>();
		ret.addAll(entryPoints.values());
		return ret;
	}
}
