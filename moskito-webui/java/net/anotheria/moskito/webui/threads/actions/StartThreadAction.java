package net.anotheria.moskito.webui.threads.actions;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryUtility;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Starts a new thread for testing purposes of the thread history.
 */
public class StartThreadAction extends ThreadsHistoryAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		final Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					//ensure thread lives long enough to be noticed.
					Thread.sleep(ThreadHistoryUtility.INSTANCE.getUpdateInterval()+100);
				}catch(InterruptedException e){};
			}
		});
		t.setName("TestThread_at_"+NumberUtils.makeISO8601TimestampString());
		t.start();
		System.out.println("Started TEST thread: "+t.getId()+", "+t.getName());
		
		
		
		return super.execute(mapping, formBean, req, res);
	}

}
