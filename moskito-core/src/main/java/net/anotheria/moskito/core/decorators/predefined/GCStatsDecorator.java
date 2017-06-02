package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.GCStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for the Garbage Collector stats.
 * @author esmakula
 *
 */
public class GCStatsDecorator extends AbstractDecorator {

	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
			"CurrentCollectionCount",
			"TotalCollectionCount",
			"CurrentCollectionTime",
			"TotalCollectionTime"
	};
	/**
	 * Short explanations (mouse-over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
			"Current collection count",
			"Total collection count",
			"Current collection time",
			"Total collection time",
	};

	/**
	 * Detailed explanations.
	 */
	private static final String EXPLANATIONS[] = {
			"Total number of collections that have occurred for interval",
			"Total number of collections that have occurred from start",
			"Approximate accumulated collection elapsed time in milliseconds for interval",
			"Approximate accumulated collection elapsed time in milliseconds from start"
	};

	public GCStatsDecorator(){
		this("GC");
	}

	public GCStatsDecorator(String aName){
		super(aName, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
		GCStats stats = (GCStats) statsObject;

		List<StatValueAO> ret = new ArrayList<>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getCurrentCollectionCount(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getTotalCollectionCount(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getCurrentCollectionTime(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getTotalCollectionTime(interval)));

		return ret;
	}

}
