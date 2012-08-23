package net.java.dev.moskito.webui.action.accumulators;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Deletes a single accumulator.
 */
public class DeleteAccumulatorAction extends BaseAccumulatorsAction{

    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
        AccumulatorRepository.getInstance().removeById(req.getParameter(PARAM_ID));
        return mapping.redirect();
    }
}
