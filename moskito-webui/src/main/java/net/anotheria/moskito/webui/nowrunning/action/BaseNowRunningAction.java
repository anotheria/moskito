package net.anotheria.moskito.webui.nowrunning.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20
 */
public abstract class BaseNowRunningAction extends BaseMoskitoUIAction {

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}


	@Override
	protected String getSubTitle() {
		return "Now Running";
	}
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_NOWRUNNING;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

}