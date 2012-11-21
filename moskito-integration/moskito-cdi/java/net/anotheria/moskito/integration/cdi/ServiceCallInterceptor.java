package net.anotheria.moskito.integration.cdi;

import javax.inject.Singleton;
import javax.interceptor.Interceptor;

/**
 * Service layer call interceptor.
 * Intercepts business logic methods.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(MonitoringCategorySelector.SERVICE)
public class ServiceCallInterceptor extends CallInterceptor {

	@Override
    public String getCategory() {
        return MonitoringCategorySelector.SERVICE;
    }
}
