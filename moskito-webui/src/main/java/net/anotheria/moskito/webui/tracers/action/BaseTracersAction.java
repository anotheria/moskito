package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:15
 */
public abstract class BaseTracersAction extends BaseMoskitoUIAction {
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_TRACERS;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

}
