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
import net.anotheria.moskito.webui.producers.api.ProducerAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Renders all producers for a specific subsystem.
 * @author lrosenberg
 *
 */
public class ShowProducersForSubsystemAction extends BaseShowProducersAction{
	
	private String getSubsystemParameter(HttpServletRequest req){
		String param = req.getParameter(PARAM_SUBSYSTEM);
		return param == null ? "none" : param;
	}

	@Override
	protected List<ProducerAO> getProducers(HttpServletRequest req)  throws APIException {
		String currentSubsystem = getSubsystemParameter(req);
		req.setAttribute("currentSubsystem", currentSubsystem);
		return getProducerAPI().getAllProducersBySubsystem(currentSubsystem, getCurrentInterval(req), getCurrentUnit(req).getUnit());
	}

	@Override public String getPageTitle(HttpServletRequest req){
		return "in subsystem "+getSubsystemParameter(req);
	}

	@Override public String getLinkToCurrentPage(HttpServletRequest req){
		return "mskShowProducersBySubsystem?"+PARAM_SUBSYSTEM+"="+getSubsystemParameter(req);
	}

}
