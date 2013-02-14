package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.bean.annotations.Form;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new threshold.
 *
 * @author lrosenberg
 * @created 26.08.12 09:44
 */
public class CreateThresholdAction extends BaseThresholdsAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, @Form(ThresholdPO.class)FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ThresholdPO po = (ThresholdPO)formBean;
		Threshold t = getThresholdAPI().createThreshold(po);

		return mapping.redirect();
	}
}

