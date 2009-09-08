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
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.QuickSorter;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.Sorter;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.IntervalInfo;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.usecase.recorder.IUseCaseRecorder;
import net.java.dev.moskito.core.usecase.recorder.UseCaseRecorderFactory;
import net.java.dev.moskito.webui.bean.IntervalBean;
import net.java.dev.moskito.webui.bean.MenuItemBean;
import net.java.dev.moskito.webui.bean.UnitBean;
import net.java.dev.moskito.webui.decorators.DecoratorRegistryFactory;
import net.java.dev.moskito.webui.decorators.IDecoratorRegistry;


public abstract class BaseMoskitoUIAction implements MoskitoUIAction{
	
	public static final String PARAM_FORWARD = "pForward";
	public static final String DEFAULT_FORWARD = "html";

	public static final String BEAN_INTERVAL = "moskito.CurrentInterval";
	public static final String PARAM_INTERVAL = "pInterval";
	public static final String DEFAULT_INTERVAL = "default";
	
	public static final String BEAN_UNIT = "moskito.CurrentUnit";
	public static final String PARAM_UNIT = "pUnit";
	public static final String DEFAULT_UNIT = "millis";
	
	public static final UnitBean[] AVAILABLE_UNITS = {
		new UnitBean(TimeUnit.SECONDS),
		new UnitBean(TimeUnit.MILLISECONDS),
		new UnitBean(TimeUnit.MICROSECONDS),
		new UnitBean(TimeUnit.NANOSECONDS),
	};
	
	public static final UnitBean DEFAULT_UNIT_BEAN = AVAILABLE_UNITS[1]; //millis
	
	public static final List<UnitBean> AVAILABLE_UNITS_LIST = Arrays.asList(AVAILABLE_UNITS);

	
	public static final String BEAN_SORT_TYPE_PREFIX = "moskito.SortType";
	public static final String BEAN_SORT_TYPE_SINGLE_PRODUCER_PREFIX = BEAN_SORT_TYPE_PREFIX+".single";
	
	private static IProducerRegistryAPI api;
	private static IDecoratorRegistry decoratorRegistry;
	private static IUseCaseRecorder useCaseRecorder;
	
	public static final String MENU_ITEM_ALL_PRODUCERS = "All Producers";
	public static final String MENU_ITEM_PRODUCERS = "Producers";
	public static final String MENU_ITEM_CATEGORIES = "Categories";
	public static final String MENU_ITEM_SUBSYSTEMS = "Subsystems";
	public static final String MENU_ITEM_USE_CASES = "Use Cases";
	public static final String MENU_ITEM_MONITORING_SESSIONS = "Monitoring Sessions";

	public static final String[] CAPTIONS = {
		MENU_ITEM_ALL_PRODUCERS,
		MENU_ITEM_CATEGORIES,
		MENU_ITEM_SUBSYSTEMS,
		MENU_ITEM_USE_CASES,
		MENU_ITEM_MONITORING_SESSIONS
	};
	
	public static final String [] LINKS = {
		"mskShowAllProducers",
		"mskShowProducersByCategory",
		"mskShowProducersBySubsystem",
		"mskShowUseCases",
		"mskShowMonitoringSessions"
	};
	
	private String myProducerId;
	private Sorter<IntervalBean> sorter;
	private SortType dummySortType;
	
	static{
		api = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		decoratorRegistry = DecoratorRegistryFactory.getDecoratorRegistry();
		useCaseRecorder = UseCaseRecorderFactory.getUseCaseRecorder();
	}	
	
	protected BaseMoskitoUIAction(){
		super();
		sorter = new QuickSorter<IntervalBean>();
		dummySortType = new DummySortType();
	}
	
	protected IProducerRegistryAPI getAPI(){
		return api;
	}
	
	protected IUseCaseRecorder getUseCaseRecorder(){
		return useCaseRecorder;
	}
	
	public String getSubsystem(){
		return "moskitoUI";
	}
	
	public String getProducerId(){
		if (myProducerId==null)
			myProducerId = "moskito."+getClass().getName().substring(getClass().getName().lastIndexOf('.')+1);
		return myProducerId;
	}
	
	protected String getForward(HttpServletRequest req){
		String forward = req.getParameter(PARAM_FORWARD);
		if (forward==null)
			forward = DEFAULT_FORWARD;
		return forward;
	}
	
	protected String getCurrentInterval(HttpServletRequest req){
		String intervalParameter = req.getParameter(PARAM_INTERVAL);
		String interval = intervalParameter;
		if (interval==null){
			interval = (String)req.getSession().getAttribute(BEAN_INTERVAL);
			if (interval == null)
				interval = DEFAULT_INTERVAL;
		}
		if (intervalParameter!=null)
			req.getSession().setAttribute(BEAN_INTERVAL, interval);
		return interval;
	}
	
	protected UnitBean getCurrentUnit(HttpServletRequest req){
		String unitParameter = req.getParameter(PARAM_UNIT);
		if (unitParameter==null){
			UnitBean ret = (UnitBean)req.getSession().getAttribute(BEAN_UNIT);
			if (ret==null){
				ret = DEFAULT_UNIT_BEAN;
				//ensure a unit bean is always in the session.
				req.getSession().setAttribute(BEAN_UNIT, ret);
			}
			return ret;
		}
		
		int index = -1;
		for (int i = 0; i<AVAILABLE_UNITS_LIST.size(); i++){
			if (AVAILABLE_UNITS_LIST.get(i).getUnitName().equalsIgnoreCase(unitParameter)){
				index = i;
				break;
			}
		}
		UnitBean ret = index == -1 ? DEFAULT_UNIT_BEAN : AVAILABLE_UNITS[index];
		req.getSession().setAttribute(BEAN_UNIT, ret);
		return ret;
	}
	
	
	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		//prepare menu
		List<MenuItemBean> menu = new ArrayList<MenuItemBean>();
		String activeMenuCaption = getActiveMenuCaption(req);
		for (int i=0; i<CAPTIONS.length; i++){
			String c = CAPTIONS[i];
			menu.add(new MenuItemBean(c, LINKS[i], c.equals(activeMenuCaption)));
		}
		req.setAttribute("menu",menu);
		
		///////////// prepare intervals
		List<IntervalInfo> intervalInfos = getAPI().getPresentIntervals();
		List<IntervalBean> intervalBeans = new ArrayList<IntervalBean>(intervalInfos.size());
		for (IntervalInfo info : intervalInfos)
			intervalBeans.add(new IntervalBean(info.getIntervalName(), NumberUtils.makeISO8601TimestampString(info.getLastUpdateTimestamp()), info.getLength()));
		req.setAttribute("intervals", sorter.sort(intervalBeans, dummySortType));
		req.setAttribute("currentInterval", getCurrentInterval(req));
		
		////////////// prepare units
		req.setAttribute("units", AVAILABLE_UNITS_LIST);
		//ensure current unit is properly set.
		getCurrentUnit(req);
		
		//Link to current page
		req.setAttribute("linkToCurrentPage", getLinkToCurrentPage(req));
		req.setAttribute("linkToCurrentPageAsXml", maskAsXML(getLinkToCurrentPage(req)));
		req.setAttribute("linkToCurrentPageAsCsv", maskAsCSV(getLinkToCurrentPage(req)));
		
	}
	
	
	
	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		long timestamp = System.currentTimeMillis();
		String timestampAsDate = NumberUtils.makeISO8601TimestampString(timestamp);
		req.setAttribute("timestamp", timestamp);
		req.setAttribute("timestampAsDate", timestampAsDate);
	}

	protected abstract String getLinkToCurrentPage(HttpServletRequest req);
	
	protected String getActiveMenuCaption(HttpServletRequest req){
		return "";
	}

	protected static IDecoratorRegistry getDecoratorRegistry(){
		return decoratorRegistry;
	}
	
	private String maskAsXML(String link){
		return maskAsExtension(link, ".xml");
	}
	
	private String maskAsCSV(String link){
		return maskAsExtension(link, ".csv");
	}

	private String maskAsExtension(String link, String extension){
		if (link==null || link.length()==0)
			return link;
		int indexOfQ = link.indexOf('?');
		if (indexOfQ==-1)
			return link + extension;
		return link.substring(0,indexOfQ)+extension+link.substring(indexOfQ);
	}
}
