package net.anotheria.moskito.integration.springboot;

import net.anotheria.moskito.web.filters.*;
import net.anotheria.moskito.web.session.SessionByTldListener;
import net.anotheria.moskito.web.session.SessionCountProducer;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import net.anotheria.moskito.webui.util.SetupPreconfiguredAccumulators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Moskito auto configuration for sprinboot instances.
 *
 * @author andriiskrypnyk
 */
@Configuration
public class MoskitoAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoskitoAutoConfiguration.class);

    @PostConstruct
    private void init() {
        startMoskitoBackend();
        initPreconfiguredAccumulators();
    }

    private void startMoskitoBackend() {
        try {
            LOGGER.info("Starting Moskito backend for remote");
            StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend();
        } catch (MoSKitoInspectStartException e) {
            LOGGER.error("Error while starting Moskito inspect backend", e);
        }
    }

    private void initPreconfiguredAccumulators() {
        LOGGER.info("Setup preconfigured Moskito accumulators");
        SetupPreconfiguredAccumulators.setupUrlAccumulators();
        SetupPreconfiguredAccumulators.setupSessionCountAccumulators();
    }

    @Bean
    public FilterRegistrationBean journeyStarterFilter() {
        LOGGER.info("Registering JourneyStarterFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JourneyStarterFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean asyncSourceTldFilter() {
        LOGGER.info("Registering AsyncSourceTldFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AsyncSourceTldFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean debugRequestFilter() {
        LOGGER.info("Registering DebugRequestFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DebugRequestFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean genericMonitoringFilter() {
        LOGGER.info("Registering GenericMonitoringFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new GenericMonitoringFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean journeyFilter() {
        LOGGER.info("Registering JourneyFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JourneyFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean jSTalkBackFilter() {
        LOGGER.info("Registering JSTalkBackFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JSTalkBackFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean moskitoCommandFilter() {
        LOGGER.info("Registering MoskitoCommandFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MoskitoCommandFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean sourceIpSegmentFilter() {
        LOGGER.info("Registering SourceIpSegmentFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SourceIpSegmentFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean sourceTldFilter() {
        LOGGER.info("Registering SourceTldFilter");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SourceTldFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public SessionByTldListener sessionByTldListener() {
        LOGGER.info("Registering SessionByTldListener");
        return new SessionByTldListener();
    }

    @Bean
    public SessionCountProducer sessionCountProducer() {
        LOGGER.info("Registering SessionCountProducer");
        return new SessionCountProducer();
    }

}
