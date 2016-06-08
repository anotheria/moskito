package net.anotheria.moskito.integration.springboot;

import net.anotheria.moskito.web.filters.JourneyStarterFilter;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import net.anotheria.moskito.webui.util.SetupPreconfiguredAccumulators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

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
            StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend();
        } catch (MoSKitoInspectStartException e) {
            LOGGER.error("Error while starting Moskito inspect backend", e);
        }
    }

    private void initPreconfiguredAccumulators() {
        SetupPreconfiguredAccumulators.setupCPUAccumulators();
        SetupPreconfiguredAccumulators.setupMemoryAccumulators();
        SetupPreconfiguredAccumulators.setupUrlAccumulators();
        SetupPreconfiguredAccumulators.setupThreadAccumulators();
        SetupPreconfiguredAccumulators.setupSessionCountAccumulators();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        Filter journeyFilter = new JourneyStarterFilter();
        registrationBean.setFilter(journeyFilter);
        registrationBean.addUrlPatterns("/");
        return registrationBean;
    }


}
