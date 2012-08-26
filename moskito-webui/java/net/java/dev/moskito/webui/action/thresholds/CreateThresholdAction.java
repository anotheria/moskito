package net.java.dev.moskito.webui.action.thresholds;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.accumulation.AccumulatorDefinition;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.treshold.ThresholdDefinition;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.webui.action.accumulators.BaseAccumulatorsAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @created 26.08.12 09:44
 */
public class CreateThresholdAction extends BaseThresholdsAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String producerId = req.getParameter(PARAM_PRODUCER_ID);
		String valueName = req.getParameter(PARAM_VALUE_NAME);
		String statName = req.getParameter(PARAM_STAT_NAME);
		String intervalName = req.getParameter(PARAM_INTERVAL);
		String unitName = req.getParameter(PARAM_UNIT);
		String accName = req.getParameter("pName");

		ThresholdDefinition td = new ThresholdDefinition();
		td.setProducerName(producerId);
		td.setStatName(statName);
		td.setValueName(valueName);
		td.setIntervalName(intervalName);
		td.setTimeUnit(TimeUnit.fromString(unitName));
		td.setName(accName);
		System.out.println(td);
		ThresholdRepository.getInstance().createThreshold(td);

		return mapping.redirect();
	}
}

