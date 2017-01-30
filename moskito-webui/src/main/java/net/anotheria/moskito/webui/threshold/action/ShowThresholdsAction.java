package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.anotheria.moskito.webui.threshold.util.ThresholdStatusBeanUtility.getThresholdBeans;

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


}
