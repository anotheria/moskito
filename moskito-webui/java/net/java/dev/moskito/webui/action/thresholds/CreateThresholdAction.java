package net.java.dev.moskito.webui.action.thresholds;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.accumulation.AccumulatorDefinition;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdDefinition;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.core.treshold.ThresholdStatus;
import net.java.dev.moskito.core.treshold.guard.DoubleBarrierPassGuard;
import net.java.dev.moskito.core.treshold.guard.GuardedDirection;
import net.java.dev.moskito.core.treshold.guard.LongBarrierPassGuard;
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
		String accName = req.getParameter(PARAM_NAME);

		//now parse guards
		GuardedDirection greenDir = string2direction(req.getParameter("pGreenDir"));
		GuardedDirection yellowDir = string2direction(req.getParameter("pYellowDir"));
		GuardedDirection orangeDir = string2direction(req.getParameter("pOrangeDir"));
		GuardedDirection redDir = string2direction(req.getParameter("pRedDir"));
		GuardedDirection purpleDir = string2direction(req.getParameter("pPurpleDir"));

		String greenValue = req.getParameter("pGreenValue");
		String yellowValue = req.getParameter("pYellowValue");
		String orangeValue = req.getParameter("pOrangeValue");
		String redValue = req.getParameter("pRedValue");
		String purpleValue = req.getParameter("pPurpleValue");

		//determine if we have to use double
		boolean hasDots = hasDots(greenValue, yellowValue, orangeValue, redValue, purpleValue);

		ThresholdDefinition td = new ThresholdDefinition();
		td.setProducerName(producerId);
		td.setStatName(statName);
		td.setValueName(valueName);
		td.setIntervalName(intervalName);
		td.setTimeUnit(TimeUnit.fromString(unitName));
		td.setName(accName);

		Threshold newThreshold = ThresholdRepository.getInstance().createThreshold(td);
		newThreshold.addGuard(hasDots ?
			new DoubleBarrierPassGuard(ThresholdStatus.GREEN, Double.parseDouble(greenValue), greenDir):
			new LongBarrierPassGuard(ThresholdStatus.GREEN, Long.parseLong(greenValue), greenDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.YELLOW, Double.parseDouble(yellowValue), yellowDir):
				new LongBarrierPassGuard(ThresholdStatus.YELLOW, Long.parseLong(yellowValue), yellowDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.ORANGE, Double.parseDouble(orangeValue), orangeDir):
				new LongBarrierPassGuard(ThresholdStatus.ORANGE, Long.parseLong(orangeValue), orangeDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.RED, Double.parseDouble(redValue), redDir):
				new LongBarrierPassGuard(ThresholdStatus.RED, Long.parseLong(redValue), redDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.PURPLE, Double.parseDouble(purpleValue), purpleDir):
				new LongBarrierPassGuard(ThresholdStatus.PURPLE, Long.parseLong(purpleValue), purpleDir)
		);

		return mapping.redirect();
	}

	private boolean hasDots(String ... params){
		if (params==null)
			return false;
		for (String p : params){
			if (p!=null && p.indexOf('.')>0)
				return true;
		}
		return false;
	}

	private GuardedDirection string2direction(String param){
		System.out.println("HELLO ."+param+".");
		if (param.equalsIgnoreCase("below"))
			return GuardedDirection.DOWN;
		if (param.equalsIgnoreCase("above"))
			return GuardedDirection.UP;
		throw new IllegalArgumentException("Unknown parameter value for direction "+param+", expected below or above.");
	}
}

