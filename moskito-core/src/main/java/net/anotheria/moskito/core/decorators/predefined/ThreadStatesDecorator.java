package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.ThreadStateStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * The decorator for the ThreadState BuiltinProducer.
 *
 * @author lrosenberg
 * @since 26.11.12 00:57
 */
public class ThreadStatesDecorator extends AbstractDecorator<ThreadStateStats> {
	/**
	 * Captions for the values.
	 */
	private static final String CAPTIONS[] = {
			"Min",
			"Current",
			"Max",
	};

	/**
	 * Short explanations of the values.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
			"Min number of threads in this state",
			"Number of currently running threads in this state",
			"Max number of threads in this state",
	};


	/**
	 * Explanations of the values.
	 */
	private static final String EXPLANATIONS[] = {
			"Min number of threads in this state",
			"Number of currently running threads in this state",
			"Max number of threads in this state",
	};


	/**
	 * Creates a new ThreadCountDecorator.
	 */
	public ThreadStatesDecorator(){
		super("ThreadStates", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}


	@Override public List<StatValueAO> getValues(ThreadStateStats statsObject, String interval, TimeUnit unit) {
		List<StatValueAO> ret = new ArrayList<>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueAO(CAPTIONS[i++], statsObject.getMin(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], statsObject.getCurrent(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], statsObject.getMax(interval)));
		return ret;
	}
}
