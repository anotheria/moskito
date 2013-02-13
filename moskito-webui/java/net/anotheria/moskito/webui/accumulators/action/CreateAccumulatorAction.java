package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.bean.annotations.Form;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 * @author lrosenberg
 */
public class CreateAccumulatorAction extends BaseAccumulatorsAction{
	@Override
	public ActionCommand execute(ActionMapping mapping, @Form(AccumulatorPO.class)FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws APIException{
		getAccumulatorAPI().createAccumulator((AccumulatorPO)formBean);
		return mapping.redirect();
	}
}
