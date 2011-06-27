/*
 * $Id: BaseShowProducersAction.java 4402 2010-05-09 17:42:52Z another $
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
 * Base action for charts presentation actions.
 * 
 * @author abolbat.
 */
public class ShowChartsAction extends BaseMoskitoUIAction {

	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.CHARTS;
	}

	@Override
	public String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowCharts?ts=" + System.currentTimeMillis();
	}

	public String getPageTitle(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) {
		req.setAttribute("pageTitle", getPageTitle(req));
		return mapping.findCommand("html");
	}

}
