package net.anotheria.moskito.core.decorators.counter;

import net.anotheria.moskito.core.counter.GenericCounterStats;
import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

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
	private final List<String> valueNames;

	/**
	 * Array-based main constructor.
	 * Specify captions/shortExplanations/explanations as {@link String[]}.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 * @param captions captions of the values.
	 * @param shortExplanations short explanations of the captions.
	 * @param explanations explanations of the captions.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject, String[] captions, String[] shortExplanations, String[] explanations){
		// Super call
		super(patternObject.describeForWebUI(), captions, shortExplanations, explanations);

		// Keep a pointer to value names
		valueNames = patternObject.getAvailableValueNames();
	}

	/**
	 * List-based main constructor.
	 * Specify captions/shortExplanations/explanations as {@link List}.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 * @param captions captions of the values.
	 * @param shortExplanations short explanations of the captions.
	 * @param explanations explanations of the captions.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject, List<String> captions, List<String> shortExplanations, List<String> explanations) {
		this(patternObject, captions.toArray(new String[captions.size()]), shortExplanations.toArray(new String[shortExplanations.size()]), explanations.toArray(new String[explanations.size()]));
	}

	/**
	 * Explanations-only array-based constructor.
	 * Specify shortExplanations/explanations as {@link String[]}.
	 * We assume that captions are same as {@link GenericCounterStats#getAvailableValueNames()}.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 * @param shortExplanations short explanations of the captions.
	 * @param explanations explanations of the captions.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject, String[] shortExplanations, String[] explanations) {
		this(patternObject, patternObject.getAvailableValueNames().toArray(new String[patternObject.getAvailableValueNames().size()]), shortExplanations, explanations);
	}

	/**
	 * Explanations-only list-based constructor.
	 * Specify shortExplanations/explanations as {@link List}.
	 * We assume that captions are same as {@link GenericCounterStats#getAvailableValueNames()}.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 * @param shortExplanations short explanations of the captions.
	 * @param explanations explanations of the captions.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject, List<String> shortExplanations, List<String> explanations) {
		this(patternObject, patternObject.getAvailableValueNames(), shortExplanations, explanations);
	}

	/**
	 * Minimal constructor.
	 * It needs only the pattern object.
	 * We assume that captions/shortExplanations/explanations are same as {@link GenericCounterStats#getAvailableValueNames()}.
	 * @param patternObject pattern for this concrete decorator which is a subclass of genericcounterstats.
	 */
	public GenericCounterDecorator(GenericCounterStats patternObject) {
		this(patternObject, patternObject.getAvailableValueNames(), patternObject.getAvailableValueNames(), patternObject.getAvailableValueNames());
	}

	@Override
	public List<StatValueAO> getValues(GenericCounterStats stats, String interval, TimeUnit unit) {
		// Create new list of stat values
		List<StatValueAO> statValues = new ArrayList<StatValueAO>(valueNames.size());

		// For each available value name
		for (int i = 0; i < valueNames.size(); i++){
			// Extract name, caption and actual value
			String name = valueNames.get(i);
			String caption = getCaptions().get(i).getCaption();
			long value = stats.get(name, interval);

			// Store new stat value
			statValues.add(new LongValueAO(caption, value));
		}

		// Return resulting list
		return statValues;
	}
}