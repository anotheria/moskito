package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.13 10:00
 */
public class ThresholdAPIFactory implements APIFactory<ThresholdAPI>{
	@Override
	public ThresholdAPI createAPI() {
		return new ThresholdAPIImpl();
	}
}
