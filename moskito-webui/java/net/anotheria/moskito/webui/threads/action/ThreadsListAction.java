package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.threads.bean.ThreadStateInfoBean;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Shows a list of threads and cumulated state's stats.
 */
public class ThreadsListAction extends BaseThreadsAction{

	/**
	 * Constant for memory saving.
	 */
	private static final DummySortType DST = new DummySortType();

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long ids[] = mxBean.getAllThreadIds();
		ArrayList<ThreadInfo> infos = new ArrayList<ThreadInfo>();
		for (long id : ids){
			infos.add(mxBean.getThreadInfo(id));
		}

		HashMap<String, ThreadStateInfoBean> states = new HashMap<String, ThreadStateInfoBean>(Thread.State.values().length);

		for (ThreadInfo info : infos){
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

	
}
