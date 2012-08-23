package net.java.dev.moskito.webui.action.thresholds;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.core.treshold.ThresholdRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @created 23.08.12 09:52
 */
public class DeleteThresholdAction extends BaseThresholdsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		ThresholdRepository.getInstance().removeById(req.getParameter(PARAM_ID));
		return mapping.redirect();
	}

}
