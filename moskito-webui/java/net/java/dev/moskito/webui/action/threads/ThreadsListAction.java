package net.java.dev.moskito.webui.action.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

public class ThreadsListAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long ids[] = mxBean.getAllThreadIds();
		ArrayList<ThreadInfo> infos = new ArrayList<ThreadInfo>();
		for (long id : ids){
			infos.add(mxBean.getThreadInfo(id));
		}
		
		req.setAttribute("infos", infos);
		req.setAttribute("infosCount", infos.size());

		return mapping.success();
	}

	
}
