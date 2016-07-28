package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryUtility;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadAPIImpl.class);

	@Override
	public List<ThreadInfoAO> getThreadInfos() throws APIException {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		long ids[] = mxBean.getAllThreadIds();
		List<ThreadInfoAO> infos = new ArrayList<>(ids.length);
		for (long id : ids){
			infos.add(new ThreadInfoAO(mxBean.getThreadInfo(id)));
		}
		return infos;
	}

	@Override
	public List<ThreadInfoAO> getThreadDump() throws APIException{
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = mxBean.dumpAllThreads(true, true);
		LinkedList<ThreadInfoAO> ret = new LinkedList<>();
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
		LOGGER.info("Started TEST thread: "+t.getId()+", "+t.getName());

	}

	@Override
	public void activateHistory() throws APIException {
		ThreadHistoryUtility.INSTANCE.activate();
	}

	@Override
	public void deactivateHistory() throws APIException {
		ThreadHistoryUtility.INSTANCE.deactivate();
	}

	@Override
	public ActiveThreadHistoryAO getActiveThreadHistory() throws APIException {
		ActiveThreadHistoryAO ret = new ActiveThreadHistoryAO();

		ret.setActive(ThreadHistoryUtility.INSTANCE.isActive());
		ret.setListSize(ThreadHistoryUtility.INSTANCE.getMaxEventsSize());
		if (ret.isActive())
			ret.setEvents(ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents());

		return ret;
	}

	@Override
	public ThreadsInfoAO getThreadsInfo() throws APIException {

		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadsInfoAO infoAO = new ThreadsInfoAO();


		infoAO.setThreadCount(mxBean.getThreadCount());
		infoAO.setDaemonThreadCount(mxBean.getDaemonThreadCount());
		infoAO.setPeakThreadCount(mxBean.getPeakThreadCount());
		infoAO.setTotalStarted(mxBean.getTotalStartedThreadCount());

		infoAO.setCurrentThreadCpuTimeSupported(mxBean.isCurrentThreadCpuTimeSupported());
		infoAO.setThreadContentionMonitoringEnabled(mxBean.isThreadContentionMonitoringEnabled());
		infoAO.setThreadContentionMonitoringSupported(mxBean.isThreadContentionMonitoringSupported());
		infoAO.setThreadCpuTimeEnabled(mxBean.isThreadCpuTimeEnabled());
		infoAO.setThreadCpuTimeSupported(mxBean.isThreadCpuTimeSupported());
		return infoAO;
	}
}
