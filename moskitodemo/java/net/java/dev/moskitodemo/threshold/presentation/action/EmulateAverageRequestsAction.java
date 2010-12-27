package net.java.dev.moskitodemo.threshold.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmulateAverageRequestsAction extends AbstractEmulateAction{
	
	
	@Override
	public ActionForward moskitoExecute(ActionMapping mapping, ActionForm af,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int sleep = 1;
		try{
			sleep = Integer.parseInt(req.getParameter("sleep"));
		}catch(Exception ignored){
			
		}
		
		getGuardedService().guardedAverageMethod(sleep);
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		return mapping.findForward("success");
	}
}

