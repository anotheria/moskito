package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.15 12:07
 */
public class GaugeAPIFactory implements APIFactory<GaugeAPI>, ServiceFactory<GaugeAPI> {
	@Override
	public GaugeAPI createAPI() {
		return new GaugeAPIImpl();
	}

	@Override
	public GaugeAPI create() {
		APIFinder.addAPIFactory(GaugeAPI.class, this);
		return APIFinder.findAPI(GaugeAPI.class);
	}
}
