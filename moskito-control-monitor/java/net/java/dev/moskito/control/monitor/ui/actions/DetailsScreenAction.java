package net.java.dev.moskito.control.monitor.ui.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.control.monitor.core.cluster.Instance;
import net.java.dev.moskito.control.monitor.core.cluster.InstanceGroup;

public class DetailsScreenAction implements Action {

	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
	}

	public ActionCommand execute(ActionMapping mapping, FormBean formBean,	HttpServletRequest req, HttpServletResponse res) throws Exception {

//		List<InstanceGroup> cluster = new  ArrayList<InstanceGroup>();
//		
//		InstanceGroup anotheria = new InstanceGroup("anotheria");
//		InstanceGroup other = new InstanceGroup("other");
//		
//		Instance hudson1 = new Instance("hudson1", anotheria);
//		Instance hudson2 = new Instance("hudson2", anotheria);
//		
//		Instance jira1 = new Instance("jira1", other);
//		Instance jira2 = new Instance("jira2", other);
//		Instance jira3 = new Instance("jira3", other);
//		
//		
//		anotheria.adInstanceToGroup(hudson1);
//		anotheria.adInstanceToGroup(hudson2);
//		
//		other.adInstanceToGroup(jira1);
//		other.adInstanceToGroup(jira2);
//		other.adInstanceToGroup(jira3);
//		
//		cluster.add(other);
//		cluster.add(anotheria);
//		
//		
//		req.setAttribute("cluster", cluster);
		return mapping.findForward("success");
	}

	public void postProcess(ActionMapping mapping, HttpServletRequest req,	HttpServletResponse res) throws Exception {
		
	}

}
