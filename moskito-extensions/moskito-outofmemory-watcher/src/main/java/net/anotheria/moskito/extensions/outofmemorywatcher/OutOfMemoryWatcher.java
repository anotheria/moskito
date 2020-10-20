package net.anotheria.moskito.extensions.outofmemorywatcher;

import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.Thresholds;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import net.anotheria.moskito.webui.embedded.MoSKitoInspectStartException;
import net.anotheria.moskito.webui.embedded.StartMoSKitoInspectBackendForRemote;
import org.moskito.controlagent.endpoints.rmi.RMIEndpoint;
import org.moskito.controlagent.endpoints.rmi.RMIEndpointException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;

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

    private static String directory = "";
    private static String pattern = "";
    private static Set<String> watchingLogs = new HashSet<>();

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            System.out.println("Please enter required parameters: watch directory and file pattern(-directory=<path>, -pattern=<filename>). And optional parameter '-port'.");
            return;
        }
        String port = "";
        for (String param : args) {
            if (param.startsWith("-directory")) {
                directory = param.substring(param.indexOf("=") + 1);
            }
            if (param.startsWith("-pattern")) {
                pattern = param.substring(param.indexOf("=") + 1);
            }
            if (param.startsWith("-port")) {
                port = param.substring(param.indexOf("=") + 1);
            }
        }
        if (directory.isEmpty()) {
            System.out.println("Please enter directory parameter(-directory=<path>)");
            return;
        }
        if (pattern.isEmpty()) {
            System.out.println("Please enter pattern parameter(-pattern=<filename>)");
            return;
        }
        if (port.isEmpty()) {
            port = "9451";
            System.out.println("Port not specified. Set default value 9451");
        }

        try {
            RMIEndpoint.startRMIEndpoint();
        } catch (RMIEndpointException e) {
            System.out.println("Can't start MoSKito Control RMI Endpoint" + e.getMessage());
            return;
        }

        try {
            StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend(Integer.parseInt(port));
        } catch (MoSKitoInspectStartException e) {
            System.out.println("Can't start MoSKito inspect backend" + e.getMessage());
            return;
        }
        BuiltinUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                registerProducer(directory, pattern);
            }
        });
        System.out.println("Start watching directory: " + directory + "; pattern: " + pattern + "; port: " + port);
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
            if (!watchingLogs.contains(f.getAbsolutePath()) && f.getName().contains(filePattern)) {
                try {
                    OutOfMemoryWatcherProducer watcherProducer = new OutOfMemoryWatcherProducer(f);
                    ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(watcherProducer);
                    ThresholdConditionGuard[] guards = new ThresholdConditionGuard[]{
                            new DoubleBarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.DOWN),
                            new DoubleBarrierPassGuard(ThresholdStatus.RED, 1, GuardedDirection.UP),
                    };
                    Thresholds.addThreshold(watcherProducer.getProducerId(), watcherProducer.getProducerId(), watcherProducer.getProducerId(), "CURRENT", "1m", guards);
                    watchingLogs.add(f.getAbsolutePath());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
