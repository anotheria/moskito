package net.java.dev.moskitodemo.threshold.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

public class EmulateRequestsAction extends AbstractEmulateAction{
	
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int count = 1;
		try{
			count = Integer.parseInt(req.getParameter("count"));
		}catch(Exception ignored){
			
		}
		
		//IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		for (int i=0; i<count;i++)
			getGuardedService().guardedMethod();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		return mapping.success();
	}
}

