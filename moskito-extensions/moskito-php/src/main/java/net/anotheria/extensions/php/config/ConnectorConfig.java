package net.anotheria.extensions.php.config;

import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.SetIf;

import java.util.HashMap;
import java.util.Map;

/**
 * Connector configuration.
 * Defines connector class that be loaded by plugin on it initialization
 * and connector properties that be passed to connector instance.
 *
 * Connector class must extend {@link net.anotheria.extensions.php.connectors.Connector}
 * and have default public constructor
 *
 * Connector properties must be defined with 'connector.' prefix
 */
@ConfigureMe(allfields = true)
public class ConnectorConfig {

    private static final String PROPERTY_PREFIX = "connector.";

    /**
     * Connector class canonical name
     */
    private String connectorClass;
    /**
     * Connector configuration properties
     */
    private Map<String, String> connectorProperties = new HashMap<>();

    public String getConnectorClass() {
        return connectorClass;
    }

    public void setConnectorClass(String connectorClass) {
        this.connectorClass = connectorClass;
    }

    public  Map<String, String> getConnectorProperties() {
        return connectorProperties;
    }

    @SetIf(condition = SetIf.SetIfCondition.startsWith, value = PROPERTY_PREFIX)
    public void putConnectorProperty(String name, String value) {
        connectorProperties.put(name, value);
    }

}
