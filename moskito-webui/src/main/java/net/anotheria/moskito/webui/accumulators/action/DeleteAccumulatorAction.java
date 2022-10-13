package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a single accumulator.
 */
public class DeleteAccumulatorAction extends BaseAccumulatorsAction{

    @Override
    public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
        getAccumulatorAPI().removeAccumulator(req.getParameter(PARAM_ID));
        return mapping.redirect();
    }
}
