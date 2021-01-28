package net.anotheria.moskito.webui.nowrunning.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.accumulators.action.BaseAccumulatorsAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a single accumulator.
 */
public class DeleteNowRunningAction extends BaseAccumulatorsAction {

    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
        getNowRunningAPI().removePastMeasurement(req.getParameter(PARAM_PRODUCER_ID), req.getParameter(PARAM_ID));
        return mapping.redirect();
    }
}
