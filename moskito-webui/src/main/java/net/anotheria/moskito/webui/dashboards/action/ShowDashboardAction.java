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
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 14:02
 */
public class ShowDashboardAction extends BaseDashboardAction {
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
					System.out.println("Getting accumulator for "+ Arrays.toString(cc.getAccumulators())+" - "+accumulator);
					AccumulatedSingleGraphAO graphAO = getAccumulatorAPI().getAccumulatorGraphData(accumulator.getId());
					System.out.println("GraphAO: "+graphAO);
					bean.setChartData(graphAO);
					chartBeans.add(bean);

				}

				//TODO we disable charts with multiple accumulators for now.
				//chartBeans.add(bean);
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
}
