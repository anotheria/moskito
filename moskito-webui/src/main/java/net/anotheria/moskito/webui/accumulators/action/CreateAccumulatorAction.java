package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 * @author lrosenberg
 */
public class CreateAccumulatorAction extends BaseAccumulatorsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws APIException{
		AccumulatorPO po = AccumulatorPO.fromHttpServletRequest(req);
		getAccumulatorAPI().createAccumulator(po);
		return mapping.redirect().addParameter("newAccumulator", po.getName()).addParameter("pProducerId", po.getProducerId());
	}
}
