package net.anotheria.moskito.core.config.producers;

import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * Config fot tomcat GlobalRequestProcessor mbean.
 * @author esmakula
 */
public class TomcatRequestProcessorProducerConfig implements Serializable {
    /**
     * SerialVersionUID.
     */
    private static final long serialVersionUID = -4447284790197950659L;

    /**
     * Indicates that ajp processor should be monitored.
     */
    @Configure
    private boolean ajp = true;

    /**
     * Indicates that http processor should be monitored.
     */
    @Configure
    private boolean http = true;

    public boolean isRegister() {
        return ajp || http;
    }

    public boolean isAjp() {
        return ajp;
    }

    public void setAjp(boolean ajp) {
        this.ajp = ajp;
    }

    public boolean isHttp() {
        return http;
    }

    public void setHttp(boolean http) {
        this.http = http;
    }

    @Override
    public String toString() {
        return "TomcatRequestProcessorProducerConfig{" +
                ", ajp=" + ajp +
                ", http=" + http +
                '}';
    }
}
