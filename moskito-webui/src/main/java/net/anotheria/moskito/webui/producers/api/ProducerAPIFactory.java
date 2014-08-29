package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:48
 */
public class ProducerAPIFactory implements APIFactory<ProducerAPI>, ServiceFactory<ProducerAPI> {
	@Override
	public ProducerAPI createAPI() {
		return new ProducerAPIImpl();
	}

	@Override
	public ProducerAPI create() {
		APIFinder.addAPIFactory(ProducerAPI.class, this);
		return APIFinder.findAPI(ProducerAPI.class);
	}
}
