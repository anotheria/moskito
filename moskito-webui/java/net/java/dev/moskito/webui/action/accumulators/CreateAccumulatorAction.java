package net.java.dev.moskito.webui.action.accumulators;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.accumulation.AccumulatorDefinition;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.core.stats.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 *
 * @author lrosenberg
 * @created 26.08.12 00:27
 */
public class CreateAccumulatorAction extends BaseAccumulatorsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String producerId = req.getParameter(PARAM_PRODUCER_ID);
		String valueName = req.getParameter(PARAM_VALUE_NAME);
		String statName = req.getParameter(PARAM_STAT_NAME);
		String intervalName = req.getParameter(PARAM_INTERVAL);
		String unitName = req.getParameter(PARAM_UNIT);
		String accName = req.getParameter(PARAM_NAME);

		AccumulatorDefinition ad = new AccumulatorDefinition();
		ad.setName(accName);
		ad.setProducerName(producerId);
		ad.setStatName(statName);
		ad.setValueName(valueName);
		ad.setIntervalName(intervalName);
		ad.setTimeUnit(TimeUnit.fromString(unitName));
		System.out.println(ad);
		AccumulatorRepository.getInstance().createAccumulator(ad);

		return mapping.redirect();
	}
}
