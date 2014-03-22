package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryUtility;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.util.NumberUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.LinkedList;
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

	@Override
	public List<ThreadInfoAO> getThreadDump() throws APIException{
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = mxBean.dumpAllThreads(true, true);
		LinkedList<ThreadInfoAO> ret = new LinkedList<ThreadInfoAO>();
		for (ThreadInfo info : infos){
			ThreadInfoAO ao = new ThreadInfoAO(info);
			ao.setStackTrace(info.getStackTrace());
			ret.add(ao);
		}

		return ret;

	}

	@Override
	public void startTestThread() throws APIException {
		final Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					//ensure thread lives long enough to be noticed.
					Thread.sleep(ThreadHistoryUtility.INSTANCE.getUpdateInterval()+100);
				}catch(InterruptedException e){}
			}
		});
		t.setName("TestThread_at_"+ NumberUtils.makeISO8601TimestampString());
		t.start();
		System.out.println("Started TEST thread: "+t.getId()+", "+t.getName());

	}
}
