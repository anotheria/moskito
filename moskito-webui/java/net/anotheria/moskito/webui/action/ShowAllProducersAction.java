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
package net.anotheria.moskito.webui.action;

import net.anotheria.moskito.core.producers.IStatsProducer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This action renders the presentation of all producers.
 * @author lrosenberg
 *
 */
public class ShowAllProducersAction extends BaseShowProducersAction{

	@Override
	protected List<IStatsProducer> getProducers(HttpServletRequest req) {
		String nameFilter = req.getParameter("pNameFilter");
		if (nameFilter==null){
			nameFilter="";
		}
		req.setAttribute("nameFilter", nameFilter);
		List<IStatsProducer> all = getAPI().getAllProducers();
		if (nameFilter.length()==0)
			return all;
		nameFilter = nameFilter.toLowerCase();
		ArrayList<IStatsProducer> filtered = new ArrayList<IStatsProducer>();
		for (IStatsProducer p : all){
			if (p.getProducerId().toLowerCase().startsWith(nameFilter)){
				filtered.add(p);
			}
		}
		return filtered;
	}
	
	@Override public String getPageTitle(HttpServletRequest req){
		return ": All known producers";
	}
	
	@Override public String getLinkToCurrentPage(HttpServletRequest req){
		//adding dummy parameter to ensure that aditional parameters can be added with &
		return "mskShowAllProducers?ts="+System.currentTimeMillis();
	}
}
