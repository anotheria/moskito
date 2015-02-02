package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.13 10:00
 */
public class ThresholdAPIFactory implements APIFactory<ThresholdAPI>, ServiceFactory<ThresholdAPI>{
	@Override
	public ThresholdAPI createAPI() {
		return new ThresholdAPIImpl();
	}

	@Override
	public ThresholdAPI create() {
		APIFinder.addAPIFactory(ThresholdAPI.class, this);
		return APIFinder.findAPI(ThresholdAPI.class);
	}
}
