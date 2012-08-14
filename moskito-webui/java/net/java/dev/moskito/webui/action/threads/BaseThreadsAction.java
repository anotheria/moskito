package net.java.dev.moskito.webui.action.threads;

import javax.servlet.http.HttpServletRequest;

import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;
import net.java.dev.moskito.webui.bean.NaviItem;

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
