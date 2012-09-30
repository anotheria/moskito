package net.anotheria.moskito.core.snapshot;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 30.09.12 15:28
 */
public class SnapshotCreator {
	public static ProducerSnapshot createSnapshot(IStatsProducer producer, String intervalName){
		ProducerSnapshot ret = new ProducerSnapshot();
		ret.setCategory(producer.getCategory());
		ret.setSubsystem(producer.getSubsystem());
		ret.setProducerId(producer.getProducerId());
		ret.setIntervalName(intervalName);


		List<IStats> stats = producer.getStats();

		//optimization
		if (stats==null || stats.size()==0)
			return ret;
		List<String> cachedValueNames = stats.get(0).getAvailableValueNames();


		for (IStats stat : stats){
			ret.addSnapshot(createStatSnapshot(stat, intervalName, cachedValueNames));
		}

		return ret;
	}

	private static StatSnapshot createStatSnapshot(IStats stat, String intervalName, List<String> valueNames){
		StatSnapshot snapshot = new StatSnapshot(stat.getName());

		for (String valueName : valueNames){
			snapshot.setValue(valueName, stat.getValueByNameAsString(valueName, intervalName, TimeUnit.NANOSECONDS));
		}


		return snapshot;


	}
}
