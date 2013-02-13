package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 13.02.13 18:14
 */
public class AccumulatorAPIFactory implements APIFactory<AccumulatorAPI> {
	@Override
	public AccumulatorAPI createAPI() {
		return new AccumulatorAPIImpl();
	}
}
