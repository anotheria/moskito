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

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;
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

/**
 * BaseAction providing some common functionality for all moskitouiactions.
 * @author another
 *
 */
public abstract class BaseMoskitoUIAction implements Action{
	
	/**
	 * Constant for the forward parameter.
	 */
	public static final String PARAM_FORWARD = "pForward";
	/**
	 * Default forward parameter if no forward has been specified.
	 */
	public static final String DEFAULT_FORWARD = "html";

	/**
	 * Bean name for currently selected interval.
	 */
	public static final String BEAN_INTERVAL = "moskito.CurrentInterval";
	/**
	 * Parameter name for the interval.
	 */
	public static final String PARAM_INTERVAL = "pInterval";
	/**
	 * Default interval name.
	 */
	public static final String DEFAULT_INTERVAL = "default";
	/**
	 * Bean name for currently selected unit.
	 */
	public static final String BEAN_UNIT = "moskito.CurrentUnit";
	/**
	 * Param for unit.
	 */
	public static final String PARAM_UNIT = "pUnit";
	/**
	 * Default unit.
	 */
	public static final String DEFAULT_UNIT = "millis";

	/**
	 * Parameter producer id.
	 */
	public static final String PARAM_PRODUCER_ID = "pProducerId";

	
	/**
	 * Available units.
	 */
	public static final UnitBean[] AVAILABLE_UNITS = {
		new UnitBean(TimeUnit.SECONDS),
		new UnitBean(TimeUnit.MILLISECONDS),
		new UnitBean(TimeUnit.MICROSECONDS),
		new UnitBean(TimeUnit.NANOSECONDS),
	};
	
	public static final UnitBean DEFAULT_UNIT_BEAN = AVAILABLE_UNITS[1]; //millis
	/**
	 * List of the available units.
	 */
	public static final List<UnitBean> AVAILABLE_UNITS_LIST = Arrays.asList(AVAILABLE_UNITS);

	/**
	 * Stored sort type bean prefix name.
	 */
	public static final String BEAN_SORT_TYPE_PREFIX = "moskito.SortType";
	public static final String BEAN_SORT_TYPE_SINGLE_PRODUCER_PREFIX = BEAN_SORT_TYPE_PREFIX+".single";
	
	/**
	 * Instance of the producer registry api.
	 */
	private static IProducerRegistryAPI api;
	/**
	 * Instance of the decorator registry.
	 */
	private static IDecoratorRegistry decoratorRegistry;
	/**
	 * Link to the use case recorder instance.
	 */
	private static IUseCaseRecorder useCaseRecorder;
	

	
	static enum MenuItem{
		/**
		 * Menu item for all producers.
		 */
		ALLPRODUCERS("All Producers", "mskShowAllProducers"),
		/**
		 * Menu item for categories.
		 */
		CATEGORIES("Categories", "mskShowProducersByCategory"),
		/**
		 * Menu item for subsystems.
		 */
		SUBSYSTEMS("Subsystems", "mskShowProducersBySubsystem"),
		/**
		 * Menu item for use cases.
		 */
		USECASES("Use Cases", "mskShowUseCases"),
		/**
		 * Menu item for monitoring sessions.
		 */
		MONITORINGSESSIONS("Monitoring Sessions", "mskShowMonitoringSessions");
		
		/**
		 * Menu item caption.
		 */
		private String caption;
		/**
		 * Menu item link.
		 */
		private String link;
		
		private MenuItem(String aCaption, String aLink){
			caption = aCaption;
			link = aLink;
		}
		
		public String getCaption(){
			return caption;
		}
	}
	/**
	 * ProducerId for moskito.
	 */
	private String myProducerId;
	/**
	 * Sort type.
	 */
	private SortType dummySortType;
	
	static{
		api = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		decoratorRegistry = DecoratorRegistryFactory.getDecoratorRegistry();
		useCaseRecorder = UseCaseRecorderFactory.getUseCaseRecorder();
	}	

	/**
	 * Creates a new action.
	 */
	protected BaseMoskitoUIAction(){
		super();
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
		MenuItem[] items = MenuItem.values(); 
		for (int i=0; i<items.length; i++){
			String c = items[i].caption;
			menu.add(new MenuItemBean(c, items[i].link, c.equals(activeMenuCaption)));
		}
		req.setAttribute("menu",menu);
		
		///////////// prepare intervals
		List<IntervalInfo> intervalInfos = getAPI().getPresentIntervals();
		List<IntervalBean> intervalBeans = new ArrayList<IntervalBean>(intervalInfos.size());
		for (IntervalInfo info : intervalInfos)
			intervalBeans.add(new IntervalBean(info.getIntervalName(), NumberUtils.makeISO8601TimestampString(info.getLastUpdateTimestamp()), info.getLength()));
		req.setAttribute("intervals", StaticQuickSorter.sort(intervalBeans, dummySortType));
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
