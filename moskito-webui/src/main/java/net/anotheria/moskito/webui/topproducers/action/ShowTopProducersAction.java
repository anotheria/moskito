package net.anotheria.moskito.webui.topproducers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.topproducers.api.CategoryTopProducersAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Shows the top-producers ranking for every category.
 *
 * @author lrosenberg
 */
public class ShowTopProducersAction extends BaseTopProducersAction {

	/**
	 * Number of producers shown per category.
	 */
	private static final int LIMIT = 25;

	@Override
	public ActionCommand execute(ActionMapping mapping,
								 HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<CategoryTopProducersAO> categories = getTopProducersAPI().getTopProducersByAllCategories(LIMIT);
		req.setAttribute("categories", categories);
		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskTopProducers?ts=" + System.currentTimeMillis();
	}

	@Override
	protected String getPageName() {
		return "topproducers";
	}
}
