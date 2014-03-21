package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:45
 */
public class ThreadAPIImpl extends AbstractMoskitoAPIImpl implements ThreadAPI {
	@Override
	public List<ThreadInfoAO> getThreadInfos() throws APIException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long ids[] = mxBean.getAllThreadIds();
		ArrayList<ThreadInfoAO> infos = new ArrayList<ThreadInfoAO>();
		for (long id : ids){
			infos.add(new ThreadInfoAO(mxBean.getThreadInfo(id)));
		}
		return infos;
	}
}
