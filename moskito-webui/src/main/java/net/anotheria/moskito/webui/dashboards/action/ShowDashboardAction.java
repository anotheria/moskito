package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 14:02
 */
public class ShowDashboardAction extends BaseDashboardAction {

	private static final String VALUE_PLACEHOLDER = "XXX";

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dashboardName = getSelectedDashboard(request);
		Boolean gaugesPresent = false;
		Boolean chartsPresent = false;
		Boolean thresholdsPresent = false;



		//set default values, allow to exit previously.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent));

		MoskitoConfiguration mskConfig = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = mskConfig.getDashboardsConfig();
		if (dashboardsConfig==null || dashboardsConfig.getDashboards()==null || dashboardsConfig.getDashboards().length ==0){
			return actionMapping.success();
		}

		DashboardConfig selectedDashboard = null;

		for (DashboardConfig dc : dashboardsConfig.getDashboards()){
			if (dc.getName()!=null && dc.getName().equals(dashboardName)){
				selectedDashboard = dc;
			}
		}

		if (selectedDashboard == null){
			return actionMapping.success();
		}

		//now we definitely have a selected dashboard.
		//prepare thresholds
		if (selectedDashboard.getThresholds()!=null && selectedDashboard.getThresholds().length>0){
			List<ThresholdStatusAO> thresholds = getThresholdAPI().getThresholdStatuses(selectedDashboard.getThresholds());
			request.setAttribute("thresholds", thresholds);
			thresholdsPresent = true;
		}

		//prepare charts
		if (selectedDashboard.getCharts()!=null && selectedDashboard.getCharts().length>0){
			LinkedList<DashboardChartBean> chartBeans = new LinkedList<DashboardChartBean>();
			for (ChartConfig cc : selectedDashboard.getCharts()){
				DashboardChartBean bean = new DashboardChartBean();
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
					AccumulatorAO accumulator = getAccumulatorAPI().getAccumulatorByName(cc.getAccumulators()[0]);
					AccumulatedSingleGraphAO graphAO = getAccumulatorAPI().getAccumulatorGraphData(accumulator.getId());
					bean.setChartData(graphAO);
					String[] lineNames = new String[1];
					lineNames[0] = graphAO.getName();
					bean.setLineNames(lineNames);

				}

				if (cc.getAccumulators().length>1){
					// this means we have a chart with multiple data.
					System.out.println("Building charts for "+Arrays.toString(cc.getAccumulators()));
					//first get single charts
					Map<Long, ArrayList<String>> chartValues = new HashMap<Long, ArrayList<String>>();
					List<AccumulatedSingleGraphAO> singleCharts = new LinkedList<AccumulatedSingleGraphAO>();
					for (String accName : cc.getAccumulators()){
						AccumulatorAO acc = getAccumulatorAPI().getAccumulatorByName(accName);
						AccumulatedSingleGraphAO graphAO = getAccumulatorAPI().getAccumulatorGraphData(acc.getId());
						singleCharts.add(graphAO);
					}
					System.out.println("Source charts: "+singleCharts);

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
					System.out.println("Intermediate data: "+chartValues);

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
					System.out.println("Intermediate data 2: "+chartValues);

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
						System.out.println("Examine "+l);
						AccumulatedValueAO ao = new AccumulatedValueAO(""+l);
						ao.setNumericTimestamp(l);
						ao.addValues(chartValues.get(l));
						finalChartData.add(ao);
					}
					finalChart.setData(finalChartData);
					bean.setChartData(finalChart);
					System.out.println("Final chart: "+finalChart);
				}


				chartBeans.add(bean);
			}

			if (chartBeans.size()>0) {
				request.setAttribute("charts", chartBeans);
				chartsPresent = true;

			}
		}





		//maybe the value has changed.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent));


		return actionMapping.success();
	}

	@Override
	protected String getPageName() {
		return "dashboard";
	}

}
