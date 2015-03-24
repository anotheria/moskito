package net.anotheria.moskito.webui.gauges.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.15 21:37
 */
abstract class BaseGaugesAction extends BaseMoskitoUIAction {
	//return null sofar
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	//return null sofar
	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}

}
