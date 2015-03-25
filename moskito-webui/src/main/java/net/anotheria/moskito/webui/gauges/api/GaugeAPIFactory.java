package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.15 12:07
 */
public class GaugeAPIFactory implements APIFactory<GaugeAPI>{
	@Override
	public GaugeAPI createAPI() {
		return new GaugeAPIImpl();
	}
}
