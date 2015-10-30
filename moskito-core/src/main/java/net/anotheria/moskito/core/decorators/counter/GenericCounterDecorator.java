package net.anotheria.moskito.core.decorators.counter;

import net.anotheria.moskito.core.counter.GenericCounterStats;
import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Generic decorator for counter stats based decorators.
 *
 * @author lrosenberg
 * @since 19.11.12 12:10
 */
public abstract class GenericCounterDecorator extends AbstractDecorator<GenericCounterStats> {

	/**
	 * Cached value names. Used to dynamically create value beans.
	 */
	private final Set<String> valueNames;

	/**
	 * Captions that this decorator support.
	 */
	private String[] captions;

	/**
	 * Creates a new generic decorator.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 * @param captions captions of the values.
	 * @param shortExplanations short explanations of the captions.
	 * @param explanations explanations of the captions.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject, String[] captions, String[] shortExplanations, String[] explanations){
		super( patternObject.describeForWebUI() , captions, shortExplanations, explanations);
		valueNames = patternObject.getPossibleNames();
		this.captions = captions;
	}

	@Override public List<StatValueAO> getValues(GenericCounterStats stats, String interval, TimeUnit unit) {
		List<StatValueAO> ret = new ArrayList<StatValueAO>(valueNames.size());
		int i=0;
		for (String name : valueNames){
			ret.add(new LongValueAO(captions[i++], stats.get(name, interval)));
		}
		return ret;
	}
}
