package net.anotheria.moskito.webui.topproducers.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * Factory for the {@link TopProducersAPI}.
 *
 * @author lrosenberg
 */
public class TopProducersAPIFactory implements APIFactory<TopProducersAPI>, ServiceFactory<TopProducersAPI> {
	@Override
	public TopProducersAPI createAPI() {
		return new TopProducersAPIImpl();
	}

	@Override
	public TopProducersAPI create() {
		APIFinder.addAPIFactory(TopProducersAPI.class, this);
		return APIFinder.findAPI(TopProducersAPI.class);
	}
}
