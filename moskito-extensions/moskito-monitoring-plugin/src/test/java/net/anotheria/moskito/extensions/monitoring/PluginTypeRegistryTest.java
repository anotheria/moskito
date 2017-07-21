package net.anotheria.moskito.extensions.monitoring;

import net.anotheria.moskito.extensions.monitoring.stats.ApacheStats;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Some test for PluginTypeRegistry.
 *
 * @author dzhmud
 */
public class PluginTypeRegistryTest {

    @Test
    public void testAddingDuplicate() {
        PluginTypeRegistry.registerPluginType("test", null,null,null);
        assertTrue(PluginTypeRegistry.isRegistered("test"));

        PluginTypeRegistry.PluginTypeConfiguration conf = PluginTypeRegistry.getConfiguration("test");
        PluginTypeRegistry.registerPluginType("test", new ApacheStats.ApacheStatsFactory(),null,null);
        assertTrue(conf == PluginTypeRegistry.getConfiguration("test"));
        assertNull(conf.getStatsFactory());
    }
}
