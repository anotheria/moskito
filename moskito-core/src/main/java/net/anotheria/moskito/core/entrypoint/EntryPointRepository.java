package net.anotheria.moskito.core.entrypoint;

import net.anotheria.moskito.core.producers.IStatsProducer;

import java.util.Collection;
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
		
		ActiveMeasurement ret = new ActiveMeasurement(producerId);

		entryPoint.addCurrentMeasurements(ret);

		return ret;
	}

	public void measurementFinished(ActiveMeasurement measurement){

		EntryPoint entryPoint = entryPoints.get(measurement.getProducerId());
		//we assume that entry point can't be null! After all it was just recently created.
		entryPoint.requestFinished(measurement);
	}

	public List<EntryPoint> getEntryPoints() {
		LinkedList<EntryPoint> ret = new LinkedList<>();
		ret.addAll(entryPoints.values());
		return ret;
	}

	public int getNowRunningCount(){
		Collection<EntryPoint> entryPointsCollection = entryPoints.values();
		int ret = 0;
		if (entryPointsCollection.size()>0){
			for (EntryPoint ep : entryPointsCollection){
				ret += ep.getCurrentRequestCount();
			}
		}
		return ret;
	}
}
