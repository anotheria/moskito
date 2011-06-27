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

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.inspection.Inspectable;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.webui.bean.NaviItem;
/**
 * Inspects the given producer and forwards to the inspection page.
 * @author lrosenberg
 */
public class InspectProducerAction extends BaseMoskitoUIAction{
		
	@Override public ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) {

		IStatsProducer producer = getAPI().getProducer(req.getParameter(PARAM_PRODUCER_ID));
		if (! (producer instanceof Inspectable))
			throw new IllegalArgumentException("Producer not inspectable.");
		
		Inspectable inspectable = (Inspectable)producer;
		CreationInfo cInfo = inspectable.getCreationInfo();
		req.setAttribute("creationTimestamp", cInfo.getTimestamp());
		req.setAttribute("creationTime", NumberUtils.makeISO8601TimestampString(cInfo.getTimestamp()));
		List<String> stackTraceList = new ArrayList<String>(cInfo.getStackTrace().length);
		for (StackTraceElement elem : cInfo.getStackTrace())
			stackTraceList.add(elem.toString());
		req.setAttribute("creationTrace", stackTraceList);
		req.setAttribute("producerId", producer.getProducerId());
		
		return mapping.findCommand( getForward(req) );
	}
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return req.getRequestURI()+"?"+PARAM_PRODUCER_ID+"="+req.getParameter(PARAM_PRODUCER_ID);
	}
	
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.NONE;
	}

}
