package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays configured thresholds and their statuses.
 * @author lrosenberg
 *
 */
public class ShowThresholdsAction extends BaseThresholdsAction {

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		req.setAttribute("thresholds", getThresholdBeans(getThresholdAPI().getThresholdStatuses()));
		req.setAttribute("infos", getThresholdAPI().getThresholdDefinitions());
		req.setAttribute("alerts", getThresholdAPI().getAlerts());

		if(req.getParameter("newThreshold")!=null){
			req.setAttribute("newThresholdAdded","true");
			req.setAttribute("newThresholdName",req.getParameter("newThreshold"));
		}
		
		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskThresholds?ts="+System.currentTimeMillis();
	}


	@Override
	protected String getPageName() {
		return "thresholds";
	}

	private List<ThresholdStatusBean> getThresholdBeans(List<ThresholdStatusAO> thresholdStatusAOList) throws APIException {
		List<ThresholdStatusBean> ret = new ArrayList<>();
		if (thresholdStatusAOList == null || thresholdStatusAOList.size() == 0)
			return ret;

		List<DashboardAO> dashboardAOList = new ArrayList<>();
		for(String name : getDashboardAPI().getDashboardNames()) {
			dashboardAOList.add(getDashboardAPI().getDashboard(name));
		}
		for (ThresholdStatusAO thresholdStatusAO : thresholdStatusAOList) {
			String dashboardNames = "";
			for(DashboardAO dashboardAO: dashboardAOList) {
				if (dashboardAO.getThresholds() == null || !dashboardAO.getThresholds().contains(thresholdStatusAO)) {
					dashboardNames += dashboardAO.getName()+",";
				}
			}
			if (dashboardNames.length() > 0)
				dashboardNames = dashboardNames.substring(0, dashboardNames.length()-1);
			ret.add(new ThresholdStatusBean(thresholdStatusAO, dashboardNames));
		}

		return ret;
	}

}
