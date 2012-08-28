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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.webui.bean.GraphDataBean;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.UnitCountBean;

/**
 * Base action for producers presentation actions.
 * @author lrosenberg.
 */
public abstract class BaseShowProducersAction extends BaseMoskitoUIAction{
	
	/**
	 * Returns the list of producers for presentation.
	 * @param req
	 * @return
	 */
	protected abstract List<IStatsProducer> getProducers(HttpServletRequest req);
	/**
	 * Returns the page title. 
	 * @param req
	 * @return
	 */
	public abstract String getPageTitle(HttpServletRequest req);
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		Map<String, GraphDataBean> graphData = new HashMap<String, GraphDataBean>();

		req.setAttribute("decorators", getDecoratedProducers(req, getProducers(req), graphData));
		req.setAttribute("graphDatas", graphData.values());

		doCustomProcessing(req, res);
		
		req.setAttribute("pageTitle", getPageTitle(req));

		if (getForward(req).equalsIgnoreCase("csv")){
			res.setHeader("Content-Disposition", "attachment; filename=\"producers.csv\"");
		}

		return mapping.findCommand( getForward(req) );
	}
	
	protected void doCustomProcessing(HttpServletRequest req, HttpServletResponse res){
		List<String> categories = getAPI().getCategories();
		List<UnitCountBean> categoriesBeans = new ArrayList<UnitCountBean>(categories.size());
		categoriesBeans.add(UnitCountBean.NONE);
		for (String catName : categories){
			categoriesBeans.add(new UnitCountBean(catName, getAPI().getAllProducersByCategory(catName).size()));
		}
		req.setAttribute("categories", categoriesBeans);

		List<String> subsystems = getAPI().getSubsystems();		
		List<UnitCountBean> subsystemsBeans = new ArrayList<UnitCountBean>(subsystems.size());
		subsystemsBeans.add(UnitCountBean.NONE);
		for (String subName : subsystems){
			subsystemsBeans.add(new UnitCountBean(subName, getAPI().getAllProducersBySubsystem(subName).size()));
		}
		req.setAttribute("subsystems", subsystemsBeans);
	}

	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.PRODUCERS;
	}

}
