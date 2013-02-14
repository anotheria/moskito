package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:01
 */
public class JourneyAPIFactory implements APIFactory<JourneyAPI> {
	@Override
	public JourneyAPI createAPI() {
		return new JourneyAPIImpl();
	}
}
