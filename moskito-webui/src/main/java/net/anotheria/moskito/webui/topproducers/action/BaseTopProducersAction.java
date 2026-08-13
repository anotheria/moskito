package net.anotheria.moskito.webui.topproducers.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Base action for the top-producers view.
 *
 * @author lrosenberg
 */
public abstract class BaseTopProducersAction extends BaseMoskitoUIAction {

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	protected String getSubTitle() {
		return "Top Producers";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_TOPPRODUCERS;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}
}
