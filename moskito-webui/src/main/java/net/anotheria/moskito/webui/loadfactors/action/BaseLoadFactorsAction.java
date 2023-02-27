package net.anotheria.moskito.webui.loadfactors.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import jakarta.servlet.http.HttpServletRequest;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:26
 */
public abstract class BaseLoadFactorsAction extends BaseMoskitoUIAction {

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}


	@Override
	protected String getSubTitle() {
		return "LoadFactors";
	}
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_LOADFACTORS;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

}