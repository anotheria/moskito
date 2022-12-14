package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new threshold.
 *
 * @author lrosenberg
 */
public class CreateThresholdAction extends BaseThresholdsAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ThresholdPO po = ThresholdPO.fromHttpServletRequest(req);
		getThresholdAPI().createThreshold(po);

		return mapping.redirect().addParameter("newThreshold",po.getName()).addParameter("pProducerId", po.getProducerId());
	}
}

