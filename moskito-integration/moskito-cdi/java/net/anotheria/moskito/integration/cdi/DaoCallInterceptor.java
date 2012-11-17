package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;

import javax.inject.Singleton;
import javax.interceptor.Interceptor;

import static net.anotheria.moskito.integration.cdi.DaoCallInterceptor.DAO_CATEGORY;

/**
 * Data access layer call interceptor.
 * Intercepts DAO methods.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(DAO_CATEGORY)
public class DaoCallInterceptor extends CallInterceptor {

    public static final String DAO_CATEGORY = "dao";

    /**
     * Default constructor.
     *
     * @throws net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException
     *          producer creation failed
     */
    public DaoCallInterceptor() throws OnDemandStatsProducerException {
        super();
    }

    @Override
    public String getCategory() {
        return DAO_CATEGORY;
    }
}
