package net.anotheria.moskito.extensions.outofmemorywatcher;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
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
    private static final Logger log = LoggerFactory.getLogger(OutOfMemoryWatcher.class);

    private static String directory = "";
    private static String pattern = "";
    private static final Set<String> watchingLogs = new HashSet<>();

    private static String[] thresholdNames = new String[0];
	private static ChartConfig[] accumulators = new ChartConfig[0];

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 2) {
            System.out.println("Usage java -jar <path-to-jar-file> -directory=<directory> -pattern=<pattern> [-port=<port>]");
            System.out.println("If port is omitted, default port 9451 is used");
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

        int portAsInt = -1;
        try{
			portAsInt = Integer.valueOf(port);
		}catch(NumberFormatException e){
        	System.out.println("Port must be numeric "+port);
        	return;
		}

		MoskitoConfiguration moskitoConfiguration = MoskitoConfigurationHolder.getConfiguration();
        moskitoConfiguration.getBuiltinProducersConfig().disableAll();
        //create a dashboard.
		DashboardConfig dashboard = new DashboardConfig();
		dashboard.setName("WatchedFiles");
		DashboardConfig[] array = new DashboardConfig[]{
				dashboard
		};
		moskitoConfiguration.getDashboardsConfig().setDashboards(array);


        

		try {
			StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend(portAsInt);
		} catch (MoSKitoInspectStartException e) {
			System.out.println("Can't start MoSKito inspect backend" + e.getMessage());
			return;
		}

        try {
            RMIEndpoint.startRMIEndpoint();
        } catch (RMIEndpointException e) {
            System.out.println("Can't start MoSKito Control RMI Endpoint" + e.getMessage());
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
            log.debug("Not found any files in: " + watchDirectory);
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

                    //add thresholds and accumulator to the dashboard.
					String[] thresholdNames = getThresholdNames(watcherProducer.getProducerId());
					ChartConfig[] chartConfigs = getAccumulators(watcherProducer.getProducerId());

					MoskitoConfigurationHolder.getConfiguration().getDashboardsConfig().getDashboards()[0].setThresholds(thresholdNames);
					MoskitoConfigurationHolder.getConfiguration().getDashboardsConfig().getDashboards()[0].setCharts(chartConfigs);

					Accumulators.createAccumulator(watcherProducer.getProducerId(),
							watcherProducer.getProducerId(),
							watcherProducer.getProducerId(),
							"CURRENT",
							"1m"
							);
					
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private static final String[] getThresholdNames(String name){
    	String[] newThresholdNames = new String[thresholdNames.length+1];
    	if (thresholdNames.length>0){
    		System.arraycopy(thresholdNames, 0, newThresholdNames, 0, thresholdNames.length);
		}
		newThresholdNames[newThresholdNames.length-1] = name;

    	thresholdNames = newThresholdNames;
    	return thresholdNames;
	}

	private static final ChartConfig[] getAccumulators(String name){
		ChartConfig[] newAccumulators = new ChartConfig[accumulators.length+1];
		if (accumulators.length>0){
			System.arraycopy(accumulators, 0, newAccumulators, 0, accumulators.length);
		}
		ChartConfig chart = new ChartConfig();
		chart.setCaption(name);
		chart.setAccumulators(new String[]{name});
		newAccumulators[newAccumulators.length-1] = chart;

		accumulators = newAccumulators;
		return accumulators;
	}

}
