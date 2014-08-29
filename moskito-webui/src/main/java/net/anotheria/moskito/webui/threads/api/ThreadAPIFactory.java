package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:46
 */
public class ThreadAPIFactory implements APIFactory<ThreadAPI>, ServiceFactory<ThreadAPI> {
	@Override
	public ThreadAPI createAPI() {
		return new ThreadAPIImpl();
	}

	@Override
	public ThreadAPI create() {
		APIFinder.addAPIFactory(ThreadAPI.class, this);
		return APIFinder.findAPI(ThreadAPI.class);
	}
}
