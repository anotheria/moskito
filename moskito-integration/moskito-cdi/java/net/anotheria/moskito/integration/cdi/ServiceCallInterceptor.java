package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;

import javax.inject.Singleton;
import javax.interceptor.Interceptor;

import static net.anotheria.moskito.integration.cdi.ServiceCallInterceptor.SERVICE_CATEGORY;

/**
 * Service layer call interceptor.
 * Intercepts business logic methods.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(SERVICE_CATEGORY)
public class ServiceCallInterceptor extends CallInterceptor {

    public static final String SERVICE_CATEGORY = "service";

    /**
     * Default constructor.
     *
     * @throws net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException
     *          producer creation failed
     */
    public ServiceCallInterceptor() throws OnDemandStatsProducerException {
        super();
    }

    @Override
    public String getCategory() {
        return SERVICE_CATEGORY;
    }
}
