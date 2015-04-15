package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.04.15 12:46
 */
public class DashboardAPIImpl extends AbstractMoskitoAPIImpl implements DashboardAPI{
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

		MoskitoConfiguration mskConfig = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = mskConfig.getDashboardsConfig();
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
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		if (dashboardsConfig == null || dashboardsConfig.getDashboards() == null ||dashboardsConfig.getDashboards().length == 0)
			return Collections.EMPTY_LIST;
		LinkedList<DashboardDefinitionAO> ret = new LinkedList<DashboardDefinitionAO>();
		for (DashboardConfig dashboardConfig : dashboardsConfig.getDashboards()){
			DashboardDefinitionAO ao = new DashboardDefinitionAO();

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
}