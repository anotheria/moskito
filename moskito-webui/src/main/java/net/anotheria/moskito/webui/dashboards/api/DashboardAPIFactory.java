package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.APIFactory;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.04.15 12:46
 */
public class DashboardAPIFactory implements APIFactory<DashboardAPI>, ServiceFactory<DashboardAPI> {
	@Override
	public DashboardAPI createAPI() {
		return new DashboardAPIImpl();
	}

	@Override
	public DashboardAPI create() {
		APIFinder.addAPIFactory(DashboardAPI.class, this);
		return APIFinder.findAPI(DashboardAPI.class);
	}
}