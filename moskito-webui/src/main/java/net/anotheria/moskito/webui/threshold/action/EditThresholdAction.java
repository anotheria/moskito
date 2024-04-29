package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.guard.BarrierPassGuard;
import net.anotheria.moskito.webui.threshold.api.ThresholdDefinitionAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Displays the threshold edit dialog.
 *
 * @author lrosenberg
 * @since 19.10.12 23:26
 */
public class EditThresholdAction extends BaseThresholdsAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {

		String thresholdId = request.getParameter(PARAM_ID);
		ThresholdDefinitionAO definition = getThresholdAPI().getThresholdDefinition(thresholdId);

		request.setAttribute("target", "Threshold");
		request.setAttribute("definition", definition);

		request.setAttribute("thresholdId", thresholdId);

		List<ThresholdConditionGuard> guards =  getThresholdAPI().getGuardsForThreshold(thresholdId);
		Map<ThresholdStatus, String> guardValues = new EnumMap<>(ThresholdStatus.class);
		for (ThresholdConditionGuard g : guards){
			//we only support barrier guards for now.
			if (g instanceof BarrierPassGuard){
				BarrierPassGuard bpg = (BarrierPassGuard) g;
				guardValues.put(bpg.getTargetStatus(), bpg.getValueAsString());
			}
		}

		for (Map.Entry<ThresholdStatus, String> entry : guardValues.entrySet()){
			request.setAttribute(entry.getKey().name(), entry.getValue());
		}


		return actionMapping.success();
	}
}
