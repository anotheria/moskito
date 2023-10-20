package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;
import net.anotheria.moskito.webui.threshold.api.ThresholdPOHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates an existing threshold.
 *
 * @author lrosenberg
 */
public class UpdateThresholdAction extends BaseThresholdsAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String thresholdId = req.getParameter(PARAM_ID);
		ThresholdPO po = ThresholdPOHelper.fromHttpServletRequest(req);

		getThresholdAPI().updateThreshold(thresholdId, po);

		return mapping.redirect();
	}
}

