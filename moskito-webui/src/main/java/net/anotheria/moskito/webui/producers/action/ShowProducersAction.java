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
package net.anotheria.moskito.webui.producers.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.filters.CategoryFilter;
import net.anotheria.moskito.core.registry.filters.SubsystemFilter;
import net.anotheria.moskito.webui.producers.api.ProducerAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowProducersAction extends BaseShowProducersAction{

	/**
	 * Constant for current category attribute name.
 	 */
	public static final String ATTR_CURRENT_CATEGORY = "currentCategory";
	/**
	 * Constant for current subsystem attribute name.
	 */
	public static final String ATTR_CURRENT_SUBSYSTEM = "currentSubsystem";
	
	private String getCategoryParameter(HttpServletRequest req){
		String param = req.getParameter(PARAM_CATEGORY);
		req.setAttribute(ATTR_CURRENT_CATEGORY, param);
		return param;
	}
	
	private String getSubsystemParameter(HttpServletRequest req){
		String param = req.getParameter(PARAM_SUBSYSTEM);
		req.setAttribute(ATTR_CURRENT_SUBSYSTEM, param);
		return param;
	} 
	
	@Override
	protected List<ProducerAO> getProducers(HttpServletRequest req)  throws APIException {
		List<IProducerFilter> filters = new ArrayList<IProducerFilter>();
		String category = getCategoryParameter(req);
		if(category != null && category.length() > 0)
			filters.add(new CategoryFilter(category));
		String subsystem = getSubsystemParameter(req);
		if(subsystem!= null && subsystem.length() > 0)
			filters.add(new SubsystemFilter(subsystem));
		return getProducerAPI().getProducers(filters.toArray(new IProducerFilter[filters.size()]), getCurrentInterval(req), getCurrentUnit(req).getUnit());
	}
	
	@Override public String getPageTitle(HttpServletRequest req){
		return "Producers";
	}
	
	@Override
	public String getLinkToCurrentPage(HttpServletRequest req){
		//adding dummy parameter to ensure that aditional parameters can be added with &
		return "mskShowProducers?ts="+System.currentTimeMillis();
	}

	@Override public void doCustomProcessing(HttpServletRequest req, HttpServletResponse res) {
		try{
			req.setAttribute("categories", getProducerAPI().getCategories());
			req.setAttribute("subsystems", getProducerAPI().getSubsystems());
		}catch(APIException e){
			//TODO handle exception maybe?
			throw new RuntimeException("Can't retrieve categories or subsystems", e);
		}
 	}

}
