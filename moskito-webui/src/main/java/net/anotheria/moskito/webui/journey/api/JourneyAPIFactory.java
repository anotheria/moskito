package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:01
 */
public class JourneyAPIFactory implements APIFactory<JourneyAPI>, ServiceFactory<JourneyAPI> {
	@Override
	public JourneyAPI createAPI() {
		return new JourneyAPIImpl();
	}

	@Override
	public JourneyAPI create() {
		APIFinder.addAPIFactory(JourneyAPI.class, this);
		return APIFinder.findAPI(JourneyAPI.class);
	}
}
