package net.anotheria.moskito.webui.loadfactors.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.loadfactors.api.LoadFactorAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:27
 */
public class ShowLoadFactorsAction extends BaseLoadFactorsAction {

	@Override
	public ActionCommand execute(ActionMapping mapping,
								 HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<LoadFactorAO> factors = getLoadFactorsAPI().getLoadFactors();
		req.setAttribute("loadfactors", factors);
		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskLoadFactors?ts="+System.currentTimeMillis();
	}


	@Override
	protected String getPageName() {
		return "loadfactors";
	}


}
