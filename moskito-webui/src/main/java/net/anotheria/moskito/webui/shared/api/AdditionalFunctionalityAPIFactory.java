package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.03.14 22:53
 */
public class AdditionalFunctionalityAPIFactory implements APIFactory<AdditionalFunctionalityAPI>, ServiceFactory<AdditionalFunctionalityAPI> {
	@Override
	public AdditionalFunctionalityAPI createAPI() {
		return new AdditionalFunctionalityAPIImpl();
	}

	@Override
	public AdditionalFunctionalityAPI create() {
		APIFinder.addAPIFactory(AdditionalFunctionalityAPI.class, this);
		return APIFinder.findAPI(AdditionalFunctionalityAPI.class);
	}
}
