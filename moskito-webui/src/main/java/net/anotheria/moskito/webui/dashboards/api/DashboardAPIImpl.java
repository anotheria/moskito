package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.util.sorter.DummySortType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the DashboardAPI.
 *
 * @author lrosenberg
 * @since 08.04.15 12:46
 */
public class DashboardAPIImpl extends AbstractMoskitoAPIImpl implements DashboardAPI{

	/**
	 * Placeholder for chart values.
	 */
	private static final String VALUE_PLACEHOLDER = "XXX";

	private static final DummySortType SORT_TYPE = new DummySortType();

	/**
	 * Used to bring similar x values (time seconds) to same x point on the axis.
	 */
	public static final long X_AXIS_RESOLUTION = 60000;

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
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null ||dashboardsConfig.getDashboards().length == 0)
			return null;
		return dashboardsConfig.getDashboards()[0].getName();
	}

	@Override
	public DashboardConfig getDashboardConfig(String name) throws APIException {

		if (name == null)
			return null;

		DashboardsConfig dashboardsConfig = getConfiguration().getDashboardsConfig();
		if (dashboardsConfig==null || dashboardsConfig.getDashboards()==null || dashboardsConfig.getDashboards().length ==0){
			return null;
		}

		for (DashboardConfig dc : dashboardsConfig.getDashboards()){
			if (dc.getName()!=null && dc.getName().equals(name)){
				return dc;
			}
		}

		return null;
	}

	@Override
	public List<DashboardDefinitionAO> getDashboardDefinitions() throws APIException {
		MoskitoConfiguration config = getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null ||dashboardsConfig.getDashboards().length == 0)
			return Collections.EMPTY_LIST;
		LinkedList<DashboardDefinitionAO> ret = new LinkedList<DashboardDefinitionAO>();
		for (DashboardConfig dashboardConfig : dashboardsConfig.getDashboards()){
			DashboardDefinitionAO ao = new DashboardDefinitionAO();
			ao.setName(dashboardConfig.getName());

			if (dashboardConfig.getGauges()!=null)
				ao.setGauges(Arrays.asList(dashboardConfig.getGauges()));
			if (dashboardConfig.getThresholds()!=null)
				ao.setThresholds(Arrays.asList(dashboardConfig.getThresholds()));
			if (dashboardConfig.getCharts()!=null){
				for (ChartConfig dcc : dashboardConfig.getCharts()){
					DashboardChartDefinitionAO dccAO = new DashboardChartDefinitionAO();
					dccAO.setCaption(dcc.getCaption());
					if (dcc.getAccumulators()!=null)
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
		if (dashboardsConfig.getDashboards()==null || dashboardsConfig.getDashboards().length==0)
			throw new APIException("There are no dashboards");
		DashboardConfig config = null;
		for (DashboardConfig aConfig : dashboardsConfig.getDashboards()){
			if (aConfig.getName().equals(name)){
				config = aConfig;
				break;
			}

		}

		if (config == null)
			throw new APIException("Dashboard "+name+" not found.");


		DashboardAO ret = new DashboardAO();
		if (config.getGauges()!=null && config.getGauges().length>0){
			ret.setGauges(gaugeAPI.getGauges(config.getGauges()));
		}

		if (config.getThresholds()!=null && config.getThresholds().length>0){
			ret.setThresholds(thresholdAPI.getThresholdStatuses(config.getThresholds()));
		}


		//// CHARTS //////
		if (config.getCharts()!=null && config.getCharts().length>0){
			LinkedList<DashboardChartAO> chartBeans = new LinkedList<DashboardChartAO>();
			for (ChartConfig cc : config.getCharts()){

				DashboardChartAO bean = new DashboardChartAO();
				if (cc.getCaption()!=null){
					bean.setCaption(cc.getCaption());
				} else{
					bean.setCaption(cc.buildCaption());
				}

				LinkedList<String> chartIds = new LinkedList<String>();
				for (String cName : cc.getAccumulators()){
					try{
						AccumulatorAO accumulatorAO = accumulatorAPI.getAccumulatorByName(cName);
						if (accumulatorAO!=null)
							chartIds.add(accumulatorAO.getId());
					}catch(IllegalArgumentException e){
						//this exception is thrown if there is no accumulator with this name registered (yet).
						//we just skip it here, because we don't want the whole screen to crash.
						if (log.isDebugEnabled())
							log.debug("attempted to access non existing accumulator with name "+cName);
					}

				}

				MultilineChartAO chartAO = accumulatorAPI.getCombinedAccumulatorGraphData(chartIds);

				bean.setChart(chartAO);
				chartBeans.add(bean);
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
		if (dashboards != null){
			List<String> dashboardNames = new ArrayList<>(dashboards.length);
			for (DashboardConfig dc : dashboards){
				dashboardNames.add(dc.getName());
			}
			return dashboardNames;
		}
		return new ArrayList<>(0);
	}
}