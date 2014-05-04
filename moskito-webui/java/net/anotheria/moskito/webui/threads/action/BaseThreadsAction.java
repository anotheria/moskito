package net.anotheria.moskito.webui.threads.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;

/**
 * Base threads action.
 */
public abstract class BaseThreadsAction extends BaseMoskitoUIAction {
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.THREADS;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}


	@Override
	protected String getSubTitle() {
		return "Threads";
	}

	protected ThreadAPI getThreadAPI(){
		return APILookupUtility.getThreadAPI();
	}
}
