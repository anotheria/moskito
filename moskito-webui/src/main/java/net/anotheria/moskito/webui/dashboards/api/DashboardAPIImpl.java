package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.ChartPattern;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation of the DashboardAPI.
 *
 * @author lrosenberg
 * @since 08.04.15 12:46
 */
public class DashboardAPIImpl extends AbstractMoskitoAPIImpl implements DashboardAPI {

	/**
	 * GaugeAPI.
	 */
	private GaugeAPI gaugeAPI;
	/**
	 * ThresholdAPI.
	 */
	private ThresholdAPI thresholdAPI;
	/**
	 * AccumulatorAPI.
	 */
	private AccumulatorAPI accumulatorAPI;


	@Override
	public void init() throws APIInitException {
		super.init();

		gaugeAPI = APIFinder.findAPI(GaugeAPI.class);
		thresholdAPI = APIFinder.findAPI(ThresholdAPI.class);
		accumulatorAPI = APIFinder.findAPI(AccumulatorAPI.class);
	}

	@Override
	public String getDefaultDashboardName() {
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null || dashboardsConfig.getDashboards().length == 0)
			return null;
		return dashboardsConfig.getDashboards()[0].getName();
	}

	@Override
	public DashboardConfig getDashboardConfig(String name) throws APIException {

		if (name == null)
			return null;

		DashboardsConfig dashboardsConfig = getConfiguration().getDashboardsConfig();
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null || dashboardsConfig.getDashboards().length == 0) {
			return null;
		}

		for (DashboardConfig dc : dashboardsConfig.getDashboards()) {
			if (dc.getName() != null && dc.getName().equals(name)) {
				return dc;
			}
		}

		return null;
	}

	@Override
	public void createDashboard(String dashboardName) throws APIException {
		if (StringUtils.isEmpty(dashboardName))
			return;

		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		DashboardConfig[] dashboards = dashboardsConfig.getDashboards();
		List<DashboardConfig> newConfigList = new ArrayList<>();
		if (dashboards != null) {
			newConfigList.addAll(Arrays.asList(dashboards));
		}
		DashboardConfig dashboardConfig = new DashboardConfig();
		dashboardConfig.setName(dashboardName);
		newConfigList.add(dashboardConfig);
		dashboardsConfig.setDashboards(newConfigList.toArray(new DashboardConfig[0]));
	}

	@Override
	public void removeDashboard(String dashboardName) throws APIException {
		if (StringUtils.isEmpty(dashboardName))
			return;

		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		DashboardConfig[] dashboards = dashboardsConfig.getDashboards();
		List<DashboardConfig> newConfigList = new ArrayList<>();
		if (dashboards == null)
			throw new APIException("Dashboard " + dashboardName + " not found.");

		for (DashboardConfig dashboard : dashboards) {
			if (!dashboardName.equals(dashboard.getName())) {
				newConfigList.add(dashboard);
			}
		}

		if (newConfigList.size() == dashboards.length)
			throw new APIException("Dashboard " + dashboardName + " not found.");

		dashboardsConfig.setDashboards(newConfigList.toArray(new DashboardConfig[0]));
	}

	@Override
	public void addGaugeToDashboard(String dashboardName, String gaugeName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (StringUtils.isEmpty(gaugeName))
			return;

		String[] cc_array = config.getGauges();
		int newSize = cc_array == null ? 1 : cc_array.length + 1;
		String[] new_cc_array = new String[newSize];
		if (cc_array != null)
			System.arraycopy(cc_array, 0, new_cc_array, 0, cc_array.length);
		new_cc_array[new_cc_array.length - 1] = gaugeName;

		config.setGauges(new_cc_array);
	}

	@Override
	public void removeGaugeFromDashboard(String dashboardName, String gaugeName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (StringUtils.isEmpty(gaugeName))
			return;

		String[] cc_array = config.getGauges();
		int gaugeIndex = -1;
		int count = 0;
		for (String name : cc_array) {
			if (name.equals(gaugeName)) {
				gaugeIndex = count;
				break;
			}
			count++;
		}
		if (gaugeIndex == -1) {
			return;
		}
		if (cc_array == null || cc_array.length < gaugeIndex + 1)
			return;
		String[] new_cc_array = new String[cc_array.length - 1];
		if (cc_array.length == 1) {
			//source had only one element
			config.setGauges(new_cc_array);
			return;
		}

		removeElementFromArray(cc_array, new_cc_array, gaugeIndex);
		config.setGauges(new_cc_array);
	}

	@Override
	public void addThresholdToDashboard(String dashboardName, String thresholdName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (StringUtils.isEmpty(thresholdName))
			return;

		String[] cc_array = config.getThresholds();
		int newSize = cc_array == null ? 1 : cc_array.length + 1;
		String[] new_cc_array = new String[newSize];
		if (cc_array != null)
			System.arraycopy(cc_array, 0, new_cc_array, 0, cc_array.length);
		new_cc_array[new_cc_array.length - 1] = thresholdName;

		config.setThresholds(new_cc_array);
	}

	@Override
	public void removeThresholdFromDashboard(String dashboardName, String thresholdName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (StringUtils.isEmpty(thresholdName))
			return;

		String[] cc_array = config.getThresholds();
		int index = -1;
		int count = 0;
		for (String name : cc_array) {
			if (name.equals(thresholdName)) {
				index = count;
				break;
			}
			count++;
		}
		if (index == -1) {
			return;
		}
		if (cc_array == null || cc_array.length < index + 1)
			return;
		String[] new_cc_array = new String[cc_array.length - 1];
		if (cc_array.length == 1) {
			//source had only one element
			config.setThresholds(new_cc_array);
			return;
		}

		removeElementFromArray(cc_array, new_cc_array, index);
		config.setThresholds(new_cc_array);
	}

	@Override
	public void addChartToDashboard(String dashboardName, String caption, String[] accNames) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (accNames == null || accNames.length == 0)
			return;

		ChartConfig[] cc_array = config.getCharts();
		int newSize = cc_array == null ? 1 : cc_array.length + 1;
		ChartConfig[] new_cc_array = new ChartConfig[newSize];
		if (cc_array != null)
			System.arraycopy(cc_array, 0, new_cc_array, 0, cc_array.length);

		ChartConfig newChartConfig = new ChartConfig();
		newChartConfig.setAccumulators(accNames);
		newChartConfig.setCaption(caption);
		new_cc_array[new_cc_array.length - 1] = newChartConfig;

		config.setCharts(new_cc_array);
	}

	@Override
	public void removeChartFromDashboard(String dashboardName, String[] accNames) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (accNames == null || accNames.length == 0)
			return;

		ChartConfig inputConfig = new ChartConfig();
		inputConfig.setAccumulators(accNames);
		String accCaption = inputConfig.buildCaption();
		ChartConfig[] cc_array = config.getCharts();
		int index = -1;
		int count = 0;
		for (ChartConfig cc : cc_array) {
			String caption = cc.buildCaption();
			if (caption.equals(accCaption)) {
				index = count;
				break;
			}
			count++;
		}
		if (index == -1) {
			return;
		}
		if (cc_array == null || cc_array.length < index + 1)
			return;
		ChartConfig[] new_cc_array = new ChartConfig[cc_array.length - 1];
		if (cc_array.length == 1) {
			//source had only one element
			config.setCharts(new_cc_array);
			return;
		}

		removeElementFromArray(cc_array, new_cc_array, index);
		config.setCharts(new_cc_array);
	}

	@Override
	public void addProducerToDashboard(String dashboardName, String producerName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;

		if (StringUtils.isEmpty(producerName))
			return;

		String[] cc_array = config.getProducers();
		int newSize = cc_array == null ? 1 : cc_array.length + 1;
		String[] new_cc_array = new String[newSize];

		if (cc_array != null) {
			System.arraycopy(cc_array, 0, new_cc_array, 0, cc_array.length);
		}

		new_cc_array[new_cc_array.length - 1] = producerName;
		config.setProducers(new_cc_array);
	}

	@Override
	public void removeProducerFromDashboard(String dashboardName, String producerName) throws APIException {
		DashboardConfig config = getDashboardConfig(dashboardName);
		if (config == null)
			return;
		if (StringUtils.isEmpty(producerName))
			return;

		String[] cc_array = config.getProducers();
		int index = -1;
		int count = 0;
		for (String producer : cc_array) {
			if (producerName.equals(producer)) {
				index = count;
				break;
			}
			count++;
		}
		if (index == -1) {
			return;
		}
		if (cc_array == null || cc_array.length < index + 1)
			return;
		String[] new_cc_array = new String[cc_array.length - 1];
		if (cc_array.length == 1) {
			//source had only one element
			config.setProducers(new_cc_array);
			return;
		}

		removeElementFromArray(cc_array, new_cc_array, index);
		config.setProducers(new_cc_array);
	}

	private static <T> T removeElementFromArray(T sourceArray[], T destArray, int index) {
		//last element
		if (sourceArray.length == index + 1) {
			System.arraycopy(sourceArray, 0, destArray, 0, sourceArray.length - 1);
			return destArray;
		}
		if (index == 0) {
			System.arraycopy(sourceArray, 1, destArray, 0, sourceArray.length - 1);
			return destArray;

		}
		System.arraycopy(sourceArray, 0, destArray, 0, index + 1);
		System.arraycopy(sourceArray, index + 1, destArray, index, sourceArray.length - index - 1);
		return destArray;
	}

	@Override
	public List<DashboardDefinitionAO> getDashboardDefinitions() throws APIException {
		MoskitoConfiguration config = getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null || dashboardsConfig.getDashboards().length == 0)
			return Collections.emptyList();
		LinkedList<DashboardDefinitionAO> ret = new LinkedList<DashboardDefinitionAO>();
		for (DashboardConfig dashboardConfig : dashboardsConfig.getDashboards()) {
			DashboardDefinitionAO ao = new DashboardDefinitionAO();
			ao.setName(dashboardConfig.getName());

			if (dashboardConfig.getGauges() != null)
				ao.setGauges(Arrays.asList(dashboardConfig.getGauges()));
			if (dashboardConfig.getThresholds() != null)
				ao.setThresholds(Arrays.asList(dashboardConfig.getThresholds()));
			if (dashboardConfig.getCharts() != null) {
				for (ChartConfig dcc : dashboardConfig.getCharts()) {
					DashboardChartDefinitionAO dccAO = new DashboardChartDefinitionAO();
					dccAO.setCaption(dcc.getCaption());
					if (dcc.getAccumulators() != null)
						dccAO.setAccumulatorNames(Arrays.asList(dcc.getAccumulators()));
					ao.addChart(dccAO);
				}
			}
			ret.add(ao);
		}
		return ret;
	}

	@Override
	public DashboardAO getDashboard(String name) throws APIException {

		DashboardsConfig dashboardsConfig = getConfiguration().getDashboardsConfig();
		if (dashboardsConfig == null)
			throw new APIException("Dashboards are not configured");
		if (dashboardsConfig.getDashboards() == null || dashboardsConfig.getDashboards().length == 0)
			throw new APIException("There are no dashboards");
		DashboardConfig config = null;
		for (DashboardConfig aConfig : dashboardsConfig.getDashboards()) {
			if (aConfig.getName().equals(name)) {
				config = aConfig;
				break;
			}

		}

		if (config == null)
			throw new APIException("Dashboard " + name + " not found.");


		DashboardAO ret = new DashboardAO();
		ret.setName(config.getName());
		if (config.getGauges() != null && config.getGauges().length > 0) {
			ret.setGauges(gaugeAPI.getGauges(config.getGauges()));
		}

		if (config.getThresholds() != null && config.getThresholds().length > 0) {
			ret.setThresholds(thresholdAPI.getThresholdStatuses(config.getThresholds()));
		}


		List<String> producers = new LinkedList<String>();

		if (config.getProducers() != null && config.getProducers().length > 0) {
			producers.addAll(Arrays.asList(config.getProducers()));
		}

		if (config.getProducerNamePatterns() != null && config.getProducerNamePatterns().length > 0) {
			List<IStatsProducer> IStatsProducers = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getAllProducers();
			for (Pattern pattern : config.getPatterns()) {
				for (IStatsProducer isp : IStatsProducers) {
					if (pattern.matcher(isp.getProducerId()).matches()) {
						producers.add(isp.getProducerId());
					}
				}
			}
		}

		if (!producers.isEmpty()) {
			ret.setProducers(Arrays.asList(producers.toArray(new String[producers.size()])));
		}

		//// CHARTS //////

		List<ChartConfig> charts = new LinkedList<>();
		if (config.getCharts() != null && config.getCharts().length > 0) {
			charts.addAll(Arrays.asList(config.getCharts()));
		}

		if (config.getChartPatterns() != null && config.getChartPatterns().length > 0) {
			List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();

			for (ChartPattern chartPattern : config.getChartPatterns()) {
				List<String> matchedAccumulators = new LinkedList<>();

				if (chartPattern.getPatterns() != null) {
					for (Pattern pattern : chartPattern.getPatterns()) {
						for (Accumulator accumulator : accumulators) {
							if (pattern.matcher(accumulator.getName()).matches()) {
								matchedAccumulators.add(accumulator.getName());
							}
						}
					}
				} else {
					log.warn("Probable misconfiguration in dashboard " + name + ", chartPattern '" + chartPattern.getCaption() + "' has no actual accumulator patterns, " + chartPattern.toString());
				}

				if (matchedAccumulators.size() > 0) {
					if (chartPattern.getMode() == null) {
						chartPattern.setMode(AccumulatorSetMode.COMBINED);
					}

					switch (chartPattern.getMode()) {
						case COMBINED:
						default:
							ChartConfig chartConfigCombined = new ChartConfig();
							chartConfigCombined.setAccumulators(matchedAccumulators.toArray(new String[matchedAccumulators.size()]));
							if (chartPattern.getCaption() != null && !chartPattern.getCaption().isEmpty()) {
								chartConfigCombined.setCaption(chartPattern.getCaption());
							} else {
								chartConfigCombined.setCaption(StringUtils.trimString(chartConfigCombined.buildCaption(), "", 50));
							}
							charts.add(chartConfigCombined);

							break;
						case MULTIPLE:
							for (String accumulator : matchedAccumulators) {
								ChartConfig chartConfig = new ChartConfig();
								chartConfig.setAccumulators(new String[]{accumulator});
								chartConfig.setCaption(StringUtils.trimString(chartConfig.buildCaption(), "", 50));
								charts.add(chartConfig);
							}

							break;
					}
				}
			}
		}

		if (!charts.isEmpty()) {
			LinkedList<DashboardChartAO> chartBeans = new LinkedList<DashboardChartAO>();
			for (ChartConfig cc : charts) {

				DashboardChartAO bean = new DashboardChartAO();
				if (cc.getCaption() != null) {
					bean.setCaption(cc.getCaption());
				} else {
					bean.setCaption(StringUtils.trimString(cc.buildCaption(), "", 50));
				}

				LinkedList<String> chartIds = new LinkedList<String>();
				for (String cName : cc.getAccumulators()) {
					try {
						AccumulatorAO accumulatorAO = accumulatorAPI.getAccumulatorByName(cName);
						if (accumulatorAO != null)
							chartIds.add(accumulatorAO.getId());
					} catch (IllegalArgumentException e) {
						//this exception is thrown if there is no accumulator with this name registered (yet).
						//we just skip it here, because we don't want the whole screen to crash.
						if (log.isDebugEnabled())
							log.debug("attempted to access non existing accumulator with name " + cName);
					}

				}

				if (chartIds.size() == 0) {
					log.warn("Couldn't retrieve any chart ids for chart " + cc.toString() + ", skipping this bean");
				} else {
					MultilineChartAO chartAO = accumulatorAPI.getCombinedAccumulatorGraphData(chartIds);
					bean.setChart(chartAO);
					chartBeans.add(bean);
				}

			}
			ret.setCharts(chartBeans);
		}

		return ret;
	}

	@Override
	public List<String> getDashboardNames() throws APIException {
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		DashboardConfig[] dashboards = dashboardsConfig.getDashboards();

		if (dashboards == null || dashboards.length == 0)
			return Collections.emptyList();

		List<String> dashboardNames = new ArrayList<>(dashboards.length);
		for (DashboardConfig dc : dashboards) {
			dashboardNames.add(dc.getName());
		}
		return dashboardNames;
	}

	@Override
	public String getDashboardNamesWhichDoNotIncludeThisGauge(String gaugeName) throws APIException {
		StringBuilder ret = new StringBuilder();
		for (DashboardConfig dashboardConfig : getConfiguredDashboards()) {
			if (!dashboardConfig.containsGauge(gaugeName)) {
				if (ret.length() > 0)
					ret.append(',');
				ret.append(dashboardConfig.getName());
			}
		}
		return ret.toString();
	}

	@Override
	public String getDashboardNamesWhichDoNotIncludeThisThreshold(String thresholdName) throws APIException {
		StringBuilder ret = new StringBuilder();
		for (DashboardConfig dashboardConfig : getConfiguredDashboards()) {
			if (!dashboardConfig.containsThreshold(thresholdName)) {
				if (ret.length() > 0)
					ret.append(',');
				ret.append(dashboardConfig.getName());
			}
		}
		return ret.toString();
	}

	@Override
	public String getDashboardNamesWhichDoNotIncludeThisChart(String chartName) throws APIException {
		StringBuilder ret = new StringBuilder();
		for (DashboardConfig dashboardConfig : getConfiguredDashboards()) {
			if (!dashboardConfig.containsChart(chartName)) {
				if (ret.length() > 0)
					ret.append(',');
				ret.append(dashboardConfig.getName());
			}
		}
		return ret.toString();
	}

	private DashboardConfig[] getConfiguredDashboards(){
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		DashboardConfig[] dashboards = dashboardsConfig.getDashboards();
		return dashboards;
	}
}