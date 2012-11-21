package net.anotheria.moskito.integration.cdi;

import javax.inject.Singleton;
import javax.interceptor.Interceptor;

/**
 * Web layer calls interceptors.
 * Intercepts managed bean model calls (actions).
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(MonitoringCategorySelector.WEB)
public class WebCallInterceptor extends CallInterceptor {

    @Override
    public String getCategory() {
        return MonitoringCategorySelector.WEB;
    }
}
