package net.anotheria.moskito.extensions;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.extensions.diskspacemonitoring.DiscSpaceMonitoringConfig;
import net.anotheria.moskito.extensions.diskspacemonitoring.DiscSpaceProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Disk space monitoring plugin.
 */
public class DiskSpaceMonitoringPlugin extends AbstractMoskitoPlugin {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(DiskSpaceMonitoringPlugin.class);

    @Override
    public void initialize() {
        DiscSpaceMonitoringConfig config = DiscSpaceMonitoringConfig.getInstance();
        String[] disks = config.getDisks();
        for (Path root : FileSystems.getDefault().getRootDirectories()) {
            for(String disk : disks) {
                if (disk.equals(root.toString())) {
                    log.info("" + ": ");
                    ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(new DiscSpaceProducer(root));
                } else {
                    log.info("Found unknown disk in config: " + disk);
                }
            }
        }
    }

    @Override
    public void deInitialize() {
        super.deInitialize();
    }
}
