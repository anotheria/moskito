package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a threshold.
 *
 * @author lrosenberg
 * @created 23.08.12 09:52
 */
public class DeleteThresholdAction extends BaseThresholdsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		getThresholdAPI().removeThreshold(req.getParameter(PARAM_ID));
		return mapping.redirect();
	}

}
