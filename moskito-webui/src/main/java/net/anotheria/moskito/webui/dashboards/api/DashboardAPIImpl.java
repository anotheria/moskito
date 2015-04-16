package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.04.15 12:46
 */
public class DashboardAPIImpl extends AbstractMoskitoAPIImpl implements DashboardAPI{

	private static final String VALUE_PLACEHOLDER = "XXX";

	private static final DummySortType SORT_TYPE = new DummySortType();

	private GaugeAPI gaugeAPI;
	private ThresholdAPI thresholdAPI;
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
				}else{
					String caption = "";
					for (String acc : cc.getAccumulators()){
						if (caption.length()!=0)
							caption += " ";
						caption += acc;
					}
					bean.setCaption(caption);
				}

				//now check the data.
				if (cc.getAccumulators().length == 1){
					//this means we have a single accumulator chart
					AccumulatorAO accumulator = accumulatorAPI.getAccumulatorByName(cc.getAccumulators()[0]);
					AccumulatedSingleGraphAO graphAO = accumulatorAPI.getAccumulatorGraphData(accumulator.getId());
					bean.setChartData(graphAO);
					String[] lineNames = new String[1];
					lineNames[0] = graphAO.getName();
					bean.setLineNames(lineNames);

				}

				if (cc.getAccumulators().length>1){
					// this means we have a chart with multiple data.
					//first get single charts
					Map<Long, ArrayList<String>> chartValues = new HashMap<Long, ArrayList<String>>();
					List<AccumulatedSingleGraphAO> singleCharts = new LinkedList<AccumulatedSingleGraphAO>();
					for (String accName : cc.getAccumulators()){
						AccumulatorAO acc = accumulatorAPI.getAccumulatorByName(accName);
						AccumulatedSingleGraphAO graphAO = accumulatorAPI.getAccumulatorGraphData(acc.getId());
						singleCharts.add(graphAO);
					}

					//ok, now lets prepare map with final values.
					//for that we iterate over all single charts and create an entry in the final map for each
					for (AccumulatedSingleGraphAO singleChart : singleCharts){
						List<AccumulatedValueAO> valueList = singleChart.getData();
						for (AccumulatedValueAO value : valueList){
							long timestamp = value.getNumericTimestamp();
							timestamp = timestamp / 1000 * 1000; //remove some unneeded details.
							if (!chartValues.containsKey(timestamp)){
								//otherwise a previous list already created some values.
								//create a new entry with so many placeholders as there are charts.
								ArrayList<String> futureValueList = new ArrayList<String>(singleCharts.size());
								for (int i=0; i<singleCharts.size();i++)
									futureValueList.add(VALUE_PLACEHOLDER);
								chartValues.put(timestamp, futureValueList);

							}
						}
					}
					//after the above loop we now have a map with all timestamps and placeholders for each values in this list.

					//now next step - set the proper values.
					// we separate the two very similar iterations, to be sure that the amount of values is properly set in the second iteration and
					//all daa objects are in place.
					for (int i =0; i<singleCharts.size(); i++){
						AccumulatedSingleGraphAO singleChart = singleCharts.get(i);
						List<AccumulatedValueAO> valueList = singleChart.getData();
						for (AccumulatedValueAO value : valueList){
							long timestamp = value.getNumericTimestamp();
							timestamp = timestamp / 1000 * 1000; //remove some unneeded details.
							ArrayList<String> futureValueList = chartValues.get(timestamp);
							futureValueList.set(i, value.getFirstValue());

						}
					}
					//after the above loop we now have a map with all timestamps and values when we have them. Now we only have to remove remaining placeholder.

					//now remove PLACEHOLDER - for now we only replace them with 0.
					//in control we replace them with nearest value (left or right) but that might actually be misleading here.
					for (List<String> someValues : chartValues.values()) {
						for (int i=0; i<someValues.size(); i++){
							if (someValues.get(i).equals(VALUE_PLACEHOLDER))
								someValues.set(i, "0");
						}
					}


					//finally set the data.
					String[] lineNames = new String[singleCharts.size()];
					for (int i=0; i<singleCharts.size(); i++){
						lineNames[i] = singleCharts.get(i).getName();
					}
					bean.setLineNames(lineNames);


					AccumulatedSingleGraphAO finalChart = new AccumulatedSingleGraphAO("-");
					List<AccumulatedValueAO> finalChartData = new ArrayList<AccumulatedValueAO>();
					for (Long l : chartValues.keySet()){
						AccumulatedValueAO ao = new AccumulatedValueAO(""+l);
						ao.setNumericTimestamp(l);
						ao.addValues(chartValues.get(l));
						finalChartData.add(ao);
					}

					//now sort.
					finalChartData = StaticQuickSorter.sort(finalChartData, SORT_TYPE);

					finalChart.setData(finalChartData);
					bean.setChartData(finalChart);
					chartBeans.add(bean);

				}


				ret.setCharts(chartBeans);
			}
		}
		return ret;
	}
}