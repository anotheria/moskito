package net.java.dev.moskito.webui.action.threads;

import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;
import net.java.dev.moskito.webui.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;

/**
 * Base threads action.
 */
public abstract class BaseThreadsAction extends BaseMoskitoUIAction{
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.THREADS;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}
}
