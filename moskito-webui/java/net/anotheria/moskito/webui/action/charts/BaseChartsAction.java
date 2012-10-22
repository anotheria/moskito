package net.anotheria.moskito.webui.action.charts;

import net.anotheria.moskito.webui.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base actions for dynamic charts.
 *
 * @author lrosenberg
 * @since 07.10.12 18:40
 */
public abstract class BaseChartsAction extends BaseMoskitoUIAction{
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.CHARTS;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}


}
