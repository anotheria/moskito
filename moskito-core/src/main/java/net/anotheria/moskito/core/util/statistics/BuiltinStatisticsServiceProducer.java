package net.anotheria.moskito.core.util.statistics;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.gauges.GaugeConfig;
import net.anotheria.moskito.core.config.gauges.GaugeValueConfig;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.AbstractBuiltInProducer;
import net.anotheria.moskito.core.util.BuiltInProducer;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This builtin producer collects all stats from the reqistry about all producers which supports service stats.
 * The intention is to get a feeling whats happening in the system. 
 *
 * @author lrosenberg
 * @since 31.08.21 16:09
 */
public class BuiltinStatisticsServiceProducer extends AbstractBuiltInProducer<ServiceStats> implements IStatsProducer<ServiceStats>, BuiltInProducer {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(BuiltinStatisticsServiceProducer.class);
	/**
	 * The id of the producers. Usually its the name of the pool.
	 */
	private String producerId;


	private StatisticStats cumulatedStats;

	/**
	 * Stats container
	 */
	private List<ServiceStats> statsList;

	/**
	 * Reference of the registry.
	 */
	private IProducerRegistry registry;

	//storing Gauges to be able to adjust them after the calculation.
	/**
	 * Current value of the request count gauge.
	 */
	GaugeValueConfig gaugeValueRequestCount;
	/**
	 * Max value of the request count gauge.
	 */
	GaugeValueConfig gaugeMaxRequestCount;

	/**
	 * Current value of the request time gauge.
	 */
	GaugeValueConfig gaugeValueRequestTime;
	/**
	 * Max value of the request time gauge.
	 */
	GaugeValueConfig gaugeMaxRequestTime;

	/**
	 * Current value of the error gauge.
	 */
	GaugeValueConfig gaugeValueErrorCount;
	/**
	 * Max value of the error gauge.
	 */
	GaugeValueConfig gaugeMaxErrorCount;

	/**
	 * Current value of the error rate gauge.
	 */
	GaugeValueConfig gaugeValueErrorRate;

	/**
	 * Current value of the concurrent requests gauge.
	 */
	GaugeValueConfig gaugeValueCurrentRequests;
	/**
	 * Max value of the concurrent requests gauge.
	 */
	GaugeValueConfig gaugeMaxCurrentRequests;

	/**
	 * Stored max request count to calculate max gauge value.
	 */
	private long maxRequestCount = 0;
	/**
	 * Stored max request time to calculate max gauge value.
	 */
	private long maxRequestTime  = 0;
	/**
	 * Stored max error count to calculate max gauge value.
	 */
	private long maxErrorCount   = 0;
	/**
	 * Stored max concurrent request count to calculate max gauge value.
	 */
	private long maxCurrentRequests = 0;

	/**
	 * Creates a new producers object.
	 */
	public BuiltinStatisticsServiceProducer(){
		producerId = "ServiceStatistics";
		statsList = new CopyOnWriteArrayList<ServiceStats>();
		cumulatedStats = new StatisticStatsFactory().createStatsObject("cumulated");
		statsList.add(cumulatedStats);
		registry = ProducerRegistryFactory.getProducerRegistryInstance();
		registry.registerProducer(this);

		//setup my own dashboard
		DashboardConfig statisticDashboard = new DashboardConfig();
		statisticDashboard.setName("Statistics");
		statisticDashboard.setProducers(new String[]{producerId});
		statisticDashboard.setGauges(new String[]{"STAT REQ", "STAT ERR", "STAT CR", "STAT ERATE"});

		createAccumulator("REQ", "req", statisticDashboard);
		createAccumulator("TIME", "totaltime", statisticDashboard);
		createAccumulator("ERR", "err", statisticDashboard);
		createAccumulator("CR", "cr", statisticDashboard);
		createAccumulator("MCR", "mcr", statisticDashboard);
		createAccumulator("MAX", "max", statisticDashboard);
		createAccumulator("AVG", "avg", statisticDashboard);
		createAccumulator("ERATE", "erate", statisticDashboard);


		MoskitoConfiguration configuration = MoskitoConfigurationHolder.getConfiguration();

		gaugeValueRequestCount = new GaugeValueConfig(); gaugeValueRequestCount.setConstant(1);
		gaugeMaxRequestCount = new GaugeValueConfig(); gaugeMaxRequestCount.setConstant(1000);
		configuration.getGaugesConfig().addGauge(createGaugeConfig(
			"STAT REQ", "Statistic request", gaugeValueRequestCount, gaugeMaxRequestCount
		));

		gaugeValueRequestTime = new GaugeValueConfig(); gaugeValueRequestTime.setConstant(1);
		gaugeMaxRequestTime = new GaugeValueConfig(); gaugeMaxRequestTime.setConstant(1000);
		configuration.getGaugesConfig().addGauge(createGaugeConfig(
				"STAT TIME", "Statistic time", gaugeValueRequestTime, gaugeMaxRequestTime
		));

		gaugeValueErrorCount = new GaugeValueConfig(); gaugeValueErrorCount.setConstant(1);
		gaugeMaxErrorCount = new GaugeValueConfig(); gaugeMaxErrorCount.setConstant(1000);
		configuration.getGaugesConfig().addGauge(createGaugeConfig(
				"STAT ERR", "Statistic errors", gaugeValueErrorCount, gaugeMaxErrorCount
		));

		gaugeValueCurrentRequests = new GaugeValueConfig(); gaugeValueCurrentRequests.setConstant(1);
		gaugeMaxCurrentRequests = new GaugeValueConfig(); gaugeMaxCurrentRequests.setConstant(1000);
		configuration.getGaugesConfig().addGauge(createGaugeConfig(
				"STAT CR", "Statistic current requests", gaugeValueCurrentRequests, gaugeMaxCurrentRequests
		));

		gaugeValueErrorRate = new GaugeValueConfig(); gaugeValueErrorRate.setConstant(1);
		GaugeValueConfig gaugeMaxErrorRate = new GaugeValueConfig(); gaugeMaxErrorRate.setConstant(100);
		configuration.getGaugesConfig().addGauge(createGaugeConfig(
				"STAT ERATE", "Statistic error rate percent", gaugeValueErrorRate, gaugeMaxErrorRate
		));




		if (!configuration.getDashboardsConfig().containsDashboard(statisticDashboard.getName())){
			//Only set own dashboard if there is not already a dashboard by this name, otherwise we would overwrite it.
			//This also helps if the user saves the dashboard config with us in it.
			configuration.getDashboardsConfig().addDashboard(statisticDashboard);
		}

		//add asynch task to create accumulators and gauges.
		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readStatistics();
			}
		});


	}

	/**
	 * Creates a new gauge config.
	 * @param name name of the config.
	 * @param caption caption of the config.
	 * @param valueConfig value config for the gauge config.
	 * @param maxConfig max config for the gauge config.
	 * @return
	 */
	private GaugeConfig createGaugeConfig(String name, String caption, GaugeValueConfig valueConfig, GaugeValueConfig maxConfig){
		GaugeConfig gaugeConfig = new GaugeConfig();
		gaugeConfig.setName(name);
		gaugeConfig.setCaption(caption);
		GaugeValueConfig min = new GaugeValueConfig(); min.setConstant(0);
		gaugeConfig.setMinValue(min);
		gaugeConfig.setMaxValue(maxConfig);
		gaugeConfig.setCurrentValue(valueConfig);
		return gaugeConfig;

	}

	/**
	 * Creates a set of accumulators and adds the 1m accumulator to the DashboardConfig.
	 * @param nameExtension
	 * @param valueName
	 * @param addToThisConfig
	 */
	private void createAccumulator(String nameExtension, String valueName, DashboardConfig addToThisConfig){
		Accumulators.createAccumulator(
				"Statistics."+nameExtension+".1m", producerId, "cumulated", valueName, "default"
		);
		Accumulators.createAccumulator(
				"Statistics."+nameExtension+".5m", producerId, "cumulated", valueName, "5m"
		);
		Accumulators.createAccumulator(
				"Statistics."+nameExtension+".1h", producerId, "cumulated", valueName, "1h"
		);

		ChartConfig toAdd = new ChartConfig();
		toAdd.setCaption("Statistics "+nameExtension+" 1m");
		toAdd.setAccumulators(new String[]{"Statistics."+nameExtension+".1m"});
		addToThisConfig.addChartConfig(toAdd);
	}

	@Override
	public String getCategory() {
		return "statistics";
	}

	@Override
	public String getProducerId() {
		return producerId;
	}

	@Override
	public List<ServiceStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}

	/**
	 * Read and update the statistics.
	 */
	private void readStatistics() {
		Collection<IStatsProducer> producers = registry.getProducers();

		long requestCount1m = 0;
		long requestTime1m = 0;
		long errorCount1m = 0;
		long currentRequestCount1m = 0;
		long maxCurrentRequestCount1m = 0;
		long maxRequestTime1m = 0;


		for (IStatsProducer p : producers){

			//skip myself.
			if (p.getProducerId().equals(producerId))
				continue;

			//TODO we should filter out our own requests i.e. APIs in net.anotheria.moskito.webui.producers.api.filters.InternalProducerFilter
			//but we don't have access to it from this project, so for now our own traffic remains.
			//But maybe its even better to see if something in MoSKito interacts with other parts of the application - 2021-09-24

			List<IStats> stats = p.getStats();
			if (stats==null || stats.size()==0){
				continue;
			}
			IStats s = stats.get(0);
			if (!(s instanceof ServiceStats)){
				continue;
			}
			log.debug("Counting "+s+" of "+p.getProducerId());
			ServiceStats serviceStats = (ServiceStats)s;
			requestCount1m += serviceStats.getTotalRequests("1m");
			long time = serviceStats.getTotalTime("1m");
			requestTime1m  += time;
			errorCount1m += serviceStats.getErrors("1m");
			currentRequestCount1m += serviceStats.getCurrentRequests();
			maxCurrentRequestCount1m += serviceStats.getMaxCurrentRequests("1m");
			if (time>maxRequestTime1m)
				maxRequestTime1m = time;

		}

		if (maxRequestCount<requestCount1m)
			maxRequestCount = requestCount1m;

		if (maxRequestTime<requestTime1m)
			maxRequestTime = requestTime1m;

		if (maxErrorCount<errorCount1m)
			maxErrorCount = errorCount1m;

		if (maxCurrentRequests<currentRequestCount1m)
			maxCurrentRequests = currentRequestCount1m;


		cumulatedStats.update(
				requestCount1m,
				requestTime1m,
				errorCount1m,
				currentRequestCount1m,
				maxCurrentRequestCount1m,
				maxRequestTime1m
		);
		log.debug("RC: "+requestCount1m+", MRT: "+maxRequestCount);
		log.debug("RT: "+requestTime1m+", MRT: "+maxRequestTime);
		log.debug("CR: "+currentRequestCount1m+", MCR: "+maxCurrentRequestCount1m);
		log.debug("ERR: "+errorCount1m+", MCR: "+maxErrorCount);
		log.debug("ERR RATE: "+(int)cumulatedStats.getErrorRate("1m"));
		log.debug("MCR: "+maxCurrentRequestCount1m);


		gaugeValueRequestCount.setConstant((int)requestCount1m);
		gaugeMaxRequestCount.setConstant((int)maxRequestCount);

		gaugeValueRequestTime.setConstant((int)requestTime1m);
		gaugeMaxRequestTime.setConstant((int)maxRequestTime);

		gaugeValueErrorCount.setConstant((int)errorCount1m);
		gaugeMaxErrorCount.setConstant((int)maxErrorCount);

		gaugeValueErrorRate.setConstant((int)cumulatedStats.getErrorRate(null));

		gaugeValueCurrentRequests.setConstant((int)currentRequestCount1m);
		gaugeMaxCurrentRequests.setConstant((int)maxCurrentRequestCount1m);

	}
}
