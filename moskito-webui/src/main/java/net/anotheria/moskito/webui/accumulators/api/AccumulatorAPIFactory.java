package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * AccumulatorAPI Factory.
 *
 * @author lrosenberg
 * @since 13.02.13 18:14
 */
public class AccumulatorAPIFactory implements APIFactory<AccumulatorAPI> , ServiceFactory<AccumulatorAPI> {
	@Override
	public AccumulatorAPI createAPI() {
		return new AccumulatorAPIImpl();
	}

	@Override
	public AccumulatorAPI create() {
		APIFinder.addAPIFactory(AccumulatorAPI.class, this);
		return APIFinder.findAPI(AccumulatorAPI.class);
	}

}

