package net.anotheria.moskito.webui.decorators.counter;

import net.anotheria.moskito.core.counter.GenericCounterStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 12:10
 */
public abstract class GenericCounterDecorator extends AbstractDecorator<GenericCounterStats> {

	private final Set<String> valueNames;

	private String[] captions;

	public GenericCounterDecorator(GenericCounterStats patternObject, String[] captions, String[] shortExplanations, String[] explanations){
		super( patternObject.describeForWebUI() , captions, shortExplanations, explanations);
		valueNames = patternObject.getPossibleNames();
		this.captions = captions;
	}

	@Override public List<StatValueBean> getValues(GenericCounterStats stats, String interval, TimeUnit unit) {
		List<StatValueBean> ret = new ArrayList<StatValueBean>(valueNames.size());
		int i=0;
		for (String name : valueNames){
			ret.add(new LongValueBean(captions[i++], stats.get(name, interval)));
		}
		return ret;
	}
}
