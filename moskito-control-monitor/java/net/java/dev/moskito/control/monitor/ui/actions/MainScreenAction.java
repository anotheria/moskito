package net.java.dev.moskito.control.monitor.ui.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.control.monitor.core.cluster.Cluster;
import net.java.dev.moskito.control.monitor.core.cluster.InstanceGroup;

public class MainScreenAction implements Action {

	private List<InstanceGroup> groups = Cluster.INSTANCE.getMonitoredGroups();
	
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
	}

	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		req.setAttribute("groups", groups);
		return mapping.findForward("success");
	}

	public void postProcess(ActionMapping mapping, HttpServletRequest req,	HttpServletResponse res) throws Exception {
		
	}
}
