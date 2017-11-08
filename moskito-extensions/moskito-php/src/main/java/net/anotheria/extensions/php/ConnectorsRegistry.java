package net.anotheria.extensions.php;

import net.anotheria.extensions.php.connectors.Connector;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Manages connectors initialization and deinitialization
 * lifecycle.
 */
public class ConnectorsRegistry {

    /**
     * Holds registered connectors.
     * Stores connectors by it canonical class name key
     */
    private Map<String, ConnectorEntry> connectors = new HashMap<>();

    /**
     * Registers new connector
     * @param connector connector to register
     */
    public void addConnector(Connector connector) {
        connectors.put(connector.getClass().getCanonicalName(), new ConnectorEntry(connector));
    }

    /**
     * Checks is connector with given class name
     * is registered
     * @param connectorClass canonical name of connector class
     * @return true  - connector with given class name is registered in this registry
     *         false - no
     */
    public boolean connectorExists(String connectorClass) {
        return connectors.containsKey(connectorClass);
    }

    /**
     * Makes enable connector with given class name if
     * it registered and not already enabled.
     * Non-existing or already enabled connectors be ignored
     *
     * @param connectorClass connector class canonical name
     * @param connectorInitProperties initialization properties of connector
     * @throws ConnectorInitException
     */
    public void enableConnector(String connectorClass, Properties connectorInitProperties) throws ConnectorInitException {

        ConnectorEntry connector = connectors.get(connectorClass);

        if(connector != null)
            connectors.get(connectorClass).enableConnector(connectorInitProperties);

    }

    /**
     * Denitializes connector with given class name.
     *
     * Non-existing or not enabled connectors be ignored
     *
     * @param connectorClass connector class canonical name
     */
    public void disableConnector(String connectorClass) {

        ConnectorEntry connector = connectors.get(connectorClass);

        if(connector != null)
            connectors.get(connectorClass).disableConnector();

    }

    /**
     * Deinitializes all registered connectors
     */
    public void disableAllConnectors() {
        for (ConnectorEntry connectorEntry : connectors.values())
            connectorEntry.disableConnector();
    }

    /**
     *
     */
    private class ConnectorEntry {

        private Connector connector;
        private boolean isEnabled = false;

        private ConnectorEntry(Connector connector) {
            this.connector = connector;
        }

        private void enableConnector(Properties initProperties) throws ConnectorInitException {
            if(!isEnabled) {

                connector.init(initProperties);
                isEnabled = true;

            }
        }

        private void disableConnector() {
            if(isEnabled) {

                connector.deinit();
                isEnabled = false;

            }
        }

    }

}
