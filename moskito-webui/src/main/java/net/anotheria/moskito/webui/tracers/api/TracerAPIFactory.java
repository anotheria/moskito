package net.anotheria.moskito.webui.tracers.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for Tracer API.
 *
 * @author lrosenberg
 * @since 05.05.15 12:36
 */
public class TracerAPIFactory implements APIFactory<TracerAPI>, ServiceFactory<TracerAPI> {
	@Override
	public TracerAPI createAPI() {
		return new TracerAPIImpl();
	}

	@Override
	public TracerAPI create() {
		APIFinder.addAPIFactory(TracerAPI.class, this);
		return APIFinder.findAPI(TracerAPI.class);
	}
}
