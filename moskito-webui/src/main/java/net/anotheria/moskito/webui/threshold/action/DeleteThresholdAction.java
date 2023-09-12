package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a threshold.
 *
 * @author lrosenberg
 */
public class DeleteThresholdAction extends BaseThresholdsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		getThresholdAPI().removeThreshold(req.getParameter(PARAM_ID));
		return mapping.redirect();
	}

}
