package net.java.dev.moskito.webui.action.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

public class ThreadsDumpAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = mxBean.dumpAllThreads(true, true);
		req.setAttribute("infos", Arrays.asList(infos));
		req.setAttribute("infosCount", infos.length);

		return mapping.success();
	}

	
}
