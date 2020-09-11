package net.anotheria.moskito.webui.nowrunning.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:23
 */
public class NowRunningAPIFactory implements APIFactory<NowRunningAPI>, ServiceFactory<NowRunningAPI> {
	@Override
	public NowRunningAPI createAPI() {
		return new NowRunningAPIImpl();
	}

	@Override
	public NowRunningAPI create() {
		APIFinder.addAPIFactory(NowRunningAPI.class, this);
		return APIFinder.findAPI(NowRunningAPI.class);
	}
}
