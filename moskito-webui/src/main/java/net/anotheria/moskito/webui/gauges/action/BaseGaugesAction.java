package net.anotheria.moskito.webui.gauges.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Base class for gauge handling.
 *
 * @author lrosenberg
 * @since 23.03.15 21:37
 */
abstract class BaseGaugesAction extends BaseMoskitoUIAction {
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
