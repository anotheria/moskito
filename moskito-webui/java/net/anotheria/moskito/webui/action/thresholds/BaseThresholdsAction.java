package net.anotheria.moskito.webui.action.thresholds;

import net.anotheria.moskito.webui.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base class for thresholds.
 *
 * @author lrosenberg
 * @created 23.08.12 09:51
 */
public abstract class BaseThresholdsAction extends BaseMoskitoUIAction {
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.THRESHOLDS;
	}
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

}
