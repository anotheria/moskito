package net.java.dev.moskitodemo.threshold.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.stats.impl.IntervalRegistry;
import net.java.dev.moskito.web.MoskitoAction;
import net.java.dev.moskitodemo.threshold.business.GuardedService;
import net.java.dev.moskitodemo.threshold.business.GuardedServiceFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmulateRequestsAction extends MoskitoAction{
	
	public static GuardedService service = new GuardedServiceFactory().create();
	
	@Override
	public ActionForward moskitoExecute(ActionMapping mapping, ActionForm af,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		int count = 1;
		try{
			count = Integer.parseInt(req.getParameter("count"));
		}catch(Exception ignored){
			
		}
		
		//IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		for (int i=0; i<count;i++)
			service.guardedMethod();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		return mapping.findForward("success");
	}
}

