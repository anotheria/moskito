package net.anotheria.moskito.webui.nowrunning.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.accumulators.action.BaseAccumulatorsAction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Deletes a single accumulator.
 */
public class DeleteNowRunningAction extends BaseAccumulatorsAction {

    @Override
    public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
        getNowRunningAPI().removePastMeasurement(req.getParameter(PARAM_PRODUCER_ID), req.getParameter(PARAM_ID));
        return mapping.redirect();
    }
}
