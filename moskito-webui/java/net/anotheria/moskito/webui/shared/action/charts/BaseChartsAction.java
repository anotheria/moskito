package net.anotheria.moskito.webui.shared.action.charts;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base action for dynamic charts.
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
