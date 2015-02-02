package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.bean.annotations.Form;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @created 26.08.12 09:44
 */
public class UpdateThresholdAction extends BaseThresholdsAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, @Form(ThresholdPO.class)FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String thresholdId = req.getParameter(PARAM_ID);
		ThresholdPO po = (ThresholdPO)formBean;

		getThresholdAPI().updateThreshold(thresholdId, po);

		return mapping.redirect();
	}
}

