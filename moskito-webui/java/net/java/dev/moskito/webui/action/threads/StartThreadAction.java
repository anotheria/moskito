package net.java.dev.moskito.webui.action.threads;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.util.threadhistory.ThreadHistoryUtility;

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
