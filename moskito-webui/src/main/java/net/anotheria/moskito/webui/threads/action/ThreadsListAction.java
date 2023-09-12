package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.threads.api.ThreadInfoAO;
import net.anotheria.moskito.webui.threads.bean.ThreadStateInfoBean;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Shows a list of threads and cumulated state's stats.
 */
public class ThreadsListAction extends BaseThreadsAction{

	/**
	 * Constant for memory saving.
	 */
	private static final DummySortType DST = new DummySortType();

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<ThreadInfoAO> infos = getThreadAPI().getThreadInfos();

		HashMap<String, ThreadStateInfoBean> states = new HashMap<String, ThreadStateInfoBean>(Thread.State.values().length);

		for (ThreadInfoAO info : infos){
			Thread.State state = info.getThreadState();
			ThreadStateInfoBean stateBean = states.get(state.name());
			if (stateBean == null){
				stateBean = new ThreadStateInfoBean(state.name());
				states.put(state.name(), stateBean);
			}
			stateBean.increaseCount();
		}
		
		req.setAttribute("infos", infos);
		req.setAttribute("infosCount", infos.size());

		req.setAttribute("states", StaticQuickSorter.sort(states.values(), DST));

		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "threads";
	}
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.THREADS_LIST;
	}


}
