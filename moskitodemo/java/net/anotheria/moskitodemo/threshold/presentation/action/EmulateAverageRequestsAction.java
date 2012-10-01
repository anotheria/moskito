package net.anotheria.moskitodemo.threshold.presentation.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmulateAverageRequestsAction extends AbstractEmulateAction{
	
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int sleep = 1;
		try{
			sleep = Integer.parseInt(req.getParameter("sleep"));
		}catch(Exception ignored){
			
		}
		
		getGuardedService().guardedAverageMethod(sleep);
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		return mapping.success();
	}
}

