package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;

import javax.inject.Singleton;
import javax.interceptor.Interceptor;

import static net.anotheria.moskito.integration.cdi.WebCallInterceptor.WEB_CATEGORY;

/**
 * Web layer calls interceptors.
 * Intercepts managed bean model calls (actions).
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(WEB_CATEGORY)
public class WebCallInterceptor extends CallInterceptor {

    public static final String WEB_CATEGORY = "web";

    /**
     * Default constructor.
     *
     * @throws net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException
     *          producer creation failed
     */
    public WebCallInterceptor() throws OnDemandStatsProducerException {
        super();
    }


    @Override
    public String getCategory() {
        return WEB_CATEGORY;
    }
}
