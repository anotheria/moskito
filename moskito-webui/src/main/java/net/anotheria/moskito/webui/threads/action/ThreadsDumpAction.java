package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.threads.api.ThreadInfoAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Creates a thread dump and presents it to the users.
 */
public class ThreadsDumpAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<ThreadInfoAO> infos = getThreadAPI().getThreadDump();
		req.setAttribute("infos", infos);
		req.setAttribute("infosCount", infos.size());
		return mapping.success();
	}
	@Override
	protected String getPageName() {
		return "threads_dump";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.THREADS_DUMP;
	}
}
