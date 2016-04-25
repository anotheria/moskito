package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.webui.shared.api.filter.Matcher;
import net.anotheria.moskito.webui.shared.api.filter.Matchers;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 12:30
 */
public abstract class ProducerWithMatcherFilter implements ProducerFilter{

	private Matcher matcher;

	@Override
	public void customize(String parameter) {
		matcher = Matchers.createMatcher(parameter);
	}

	@Override
	public boolean mayPass(IStatsProducer<?> statsProducer) {
		return !matcher.doesMatch(getTargetStringFromProducer(statsProducer));
	}

	/**
	 * The overriding producer should
	 * @param statsProducer
	 * @return
	 */
	protected abstract String getTargetStringFromProducer(IStatsProducer<?> statsProducer);
}
