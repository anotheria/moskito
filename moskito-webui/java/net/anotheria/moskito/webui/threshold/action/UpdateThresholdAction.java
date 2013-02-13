package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;

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
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String thresholdId = req.getParameter(PARAM_ID);
		String tName = req.getParameter(PARAM_NAME);

		Threshold oldThreshold = ThresholdRepository.getInstance().getById(thresholdId);

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

		ThresholdDefinition td = oldThreshold.getDefinition();
		td.setName(tName);

		//remove old
		ThresholdRepository.getInstance().removeById(thresholdId);

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

	protected boolean hasDots(String ... params){
		if (params==null)
			return false;
		for (String p : params){
			if (p!=null && p.indexOf('.')>0)
				return true;
		}
		return false;
	}

	protected GuardedDirection string2direction(String param){
		if (param.equalsIgnoreCase("below"))
			return GuardedDirection.DOWN;
		if (param.equalsIgnoreCase("above"))
			return GuardedDirection.UP;
		throw new IllegalArgumentException("Unknown parameter value for direction "+param+", expected below or above.");
	}



}

