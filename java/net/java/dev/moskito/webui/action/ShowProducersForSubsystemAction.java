/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.webui.bean.UnitCountBean;

/**
 * Renders all producers for a specific subsystem.
 * @author lrosenberg
 *
 */
public class ShowProducersForSubsystemAction extends BaseShowProducersAction{
	
	public static final String PARAM_SUBSYSTEM = "pSubsystem";
	
	private String getSubsystemParameter(HttpServletRequest req){
		String param = req.getParameter(PARAM_SUBSYSTEM);
		return param == null ? "none" : param;
	}

	@Override
	protected List<IStatsProducer> getProducers(HttpServletRequest req) {
		return getAPI().getAllProducersBySubsystem(getSubsystemParameter(req));
	}

	@Override public String getPageTitle(HttpServletRequest req){
		return "in subsystem "+getSubsystemParameter(req);
	}

	@Override public String getLinkToCurrentPage(HttpServletRequest req){
		return "mskShowProducersBySubsystem?"+PARAM_SUBSYSTEM+"="+getSubsystemParameter(req);
	}

	@Override public void doCustomProcessing(HttpServletRequest req, HttpServletResponse res){
		List<String> subsystems = getAPI().getSubsystems();		
		//System.out.println("Subsystems: "+subsystems);
		List<UnitCountBean> beans = new ArrayList<UnitCountBean>(subsystems.size());
		for (String subName : subsystems){
			beans.add(new UnitCountBean(subName, getAPI().getAllProducersBySubsystem(subName).size()));
		}
		req.setAttribute("subsystems", beans);
 	}

	@Override
	protected String getActiveMenuCaption(HttpServletRequest req) {
		return MenuItem.SUBSYSTEMS.getCaption();
	}

	
}
