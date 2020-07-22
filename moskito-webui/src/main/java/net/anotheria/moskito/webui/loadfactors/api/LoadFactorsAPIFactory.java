package net.anotheria.moskito.webui.loadfactors.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:48
 */
public class LoadFactorsAPIFactory implements APIFactory<LoadFactorsAPI>, ServiceFactory<LoadFactorsAPI> {
	@Override
	public LoadFactorsAPI createAPI() {
		return new LoadFactorsAPIImpl();
	}

	@Override
	public LoadFactorsAPI create() {
		APIFinder.addAPIFactory(LoadFactorsAPI.class, this);
		return APIFinder.findAPI(LoadFactorsAPI.class);
	}
}
