package net.anotheria.moskito.extensions.outofmemorywatcher;

import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.Thresholds;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import org.distributeme.core.RMIRegistryUtil;
import org.distributeme.core.RegistryUtil;
import org.moskito.controlagent.endpoints.rmi.RMIEndpoint;
import org.moskito.controlagent.endpoints.rmi.RMIEndpointException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Out of memory watcher main class.
 *
 * @author asamoilich.
 */
public class OutOfMemoryWatcher {
    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(OutOfMemoryWatcher.class);

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            System.out.println("Please enter watch directory and file pattern");
            return;
        }
        String watchDirectory = args[0];
        String filePattern = args[1];

        try {
            StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend();
        } catch (MoSKitoInspectStartException e) {
            System.out.println("Can't start MoSKito inspect backend" + e.getMessage());
            return;
        }
        registerProducer(watchDirectory, filePattern);
    }

    private static void registerProducer(String watchDirectory, String filePattern) {
        File[] files = new File(watchDirectory).listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Not found any files in: " + watchDirectory);
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                registerProducer(f.getAbsolutePath(), filePattern);
                continue;
            }
            if (f.getName().contains(filePattern)) {
                try {
                    OutOfMemoryWatcherProducer watcherProducer = new OutOfMemoryWatcherProducer(f);
                    ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(watcherProducer);
                    ThresholdConditionGuard[] guards = new ThresholdConditionGuard[]{
                            new DoubleBarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.DOWN),
                            new DoubleBarrierPassGuard(ThresholdStatus.RED, 1, GuardedDirection.UP),
                    };
                    Thresholds.addThreshold(watcherProducer.getProducerId() + "-1m", watcherProducer.getProducerId(), watcherProducer.getProducerId(), "CURRENT", "1m", guards);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
