package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
/**
 * Displays a single accumulator.
 * @author lrosenberg
 */
public class ShowAccumulatorAction extends BaseAccumulatorsAction {

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String accumulatorId = req.getParameter(PARAM_ID);
		req.setAttribute("accumulatorInfo", getAccumulatorAPI().getAccumulatorDefinition(accumulatorId));
		req.setAttribute("accumulatorData", getAccumulatorAPI().getAccumulatorGraphData(accumulatorId));
		if (getForward(req).equalsIgnoreCase("csv")){
			res.setHeader("Content-Disposition", "attachment; filename=\""+getAccumulatorAPI().getAccumulatorDefinition(accumulatorId).getName()+".csv\"");
		}

		return mapping.findCommand(getForward(req));
	}
	

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskAccumulator?ts="+System.currentTimeMillis()+"&pId="+req.getParameter("pId");
	}

	@Override
	protected String getPageName() {
		return "accumulator";
	}
}
