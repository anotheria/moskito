/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006-2010 The MoSKito Project Team.
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
package net.anotheria.moskito.webui.shared.action;

import net.anotheria.anoplass.api.APICallContext;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.session.APISession;
import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.config.KillSwitchConfiguration;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecoratorRegistry;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.webui.Features;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.loadfactors.api.LoadFactorsAPI;
import net.anotheria.moskito.webui.nowrunning.api.NowRunningAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.annotations.BetaAction;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.shared.api.SystemStatusAO;
import net.anotheria.moskito.webui.shared.bean.AlertBean;
import net.anotheria.moskito.webui.shared.bean.AlertType;
import net.anotheria.moskito.webui.shared.bean.LabelValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.shared.bean.UnitBean;
import net.anotheria.moskito.webui.tags.api.TagAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.ConnectivityMode;
import net.anotheria.moskito.webui.util.DeepLinkUtil;
import net.anotheria.moskito.webui.util.RemoteInstance;
import net.anotheria.moskito.webui.util.WebUIConfig;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
     * General id parameter, for example for accumulators or thresholds.
     */
    public static final String PARAM_ID = "pId";

	/**
	 * Parameter producer id.
	 */
	public static final String PARAM_PRODUCER_ID = "pProducerId";

	/**
	 * Parameter stat name as in a name of a concrete stat within one producer.
	 */
	public static final String PARAM_STAT_NAME = "pStatName";

	/**
	 * Parameter for producer category.
	 */
	public static final String PARAM_CATEGORY = "pCategory";
	/**
	 * Parameter for producer subsystem.
	 */
	public static final String PARAM_SUBSYSTEM = "pSubsystem";

	/**
	 * Parameter for producer's decorator.
	 */
	public static final String PARAM_DECORATOR = "pDecorator";

	/**
	 * SortBy usually accompanied by an integer that defines the sort method.
	 */
	public static final String PARAM_SORT_BY = "pSortBy";

	/**
	 * Sort order, ASC or DESC.
	 */
	public static final String PARAM_SORT_ORDER = "pSortOrder";

	/**
	 * If set (to true) the zero request values will be removed from producer presentation.
	 */
	public static final String PARAM_FILTER_ZERO = "pFilterZero";

	/**
	 * Reload interval.
	 */
	public static final String PARAM_RELOAD_INTERVAL = "pReloadInterval";

	/**
	 * Currently set autoreload interval. Session attribute.
	 */
	public static final String BEAN_AUTORELOAD = "autoreloadInterval";

	/**
	 * Attribute name for indication if menu navigation is collapsed or not.
	 */
	public static final String ATTR_IS_NAV_MENU_COLLAPSED = "isNavMenuCollapsed";

	/**
	 * Default title string for the web page &lt;title$gt; element.
	 */
	public static final String DEFAULT_TITLE = "MoSKito Inspect";

	/**
	 * If set to off the configured filtering of producer names will be switched off.
	 */
	public static final String PARAM_FILTERING = "pFiltering";

	/**
	 * Value that switches filtering feature off.
	 */
	public static final String PARAM_VALUE_FILTER_OFF = "off";

	/**
	 * Cumulated caption value.
	 */
	public static final String CUMULATED_STAT_NAME_VALUE = "cumulated";

	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(BaseMoskitoUIAction.class);

	/**
	 * Available units.
	 */
	static final UnitBean[] AVAILABLE_UNITS = {
		new UnitBean(TimeUnit.SECONDS),
		new UnitBean(TimeUnit.MILLISECONDS),
		new UnitBean(TimeUnit.MICROSECONDS),
		new UnitBean(TimeUnit.NANOSECONDS),
	};

	/**
	 * Default unit.
	 */
	public static final UnitBean DEFAULT_UNIT_BEAN = AVAILABLE_UNITS[1]; //millis
	/**
	 * List of the available units.
	 */
	public static final List<UnitBean> AVAILABLE_UNITS_LIST = Arrays.asList(AVAILABLE_UNITS);

	/**
	 * Stored sort type bean prefix name.
	 */
	public static final String BEAN_SORT_TYPE_PREFIX = "moskito.SortType";

	/**
	 * Prefix for producer sort type session storage.
	 */
	public static final String BEAN_SORT_TYPE_SINGLE_PRODUCER_PREFIX = BEAN_SORT_TYPE_PREFIX+".single";

	/**
	 * Instance of the decorator registry.
	 */
	private static final IDecoratorRegistry decoratorRegistry;

	/**
	 * Chararcters that are considered whitespaces.
	 */
	public static final char[] WHITESPACES = {
			' ', '\r','\n','\t','\'','"','<','>'
	};

	/**
	 * ProducerId for moskito.
	 */
	private String myProducerId;

	/**
	 * Holds alerts between the calls.
	 * Warning/TODO: this is actually not threadsafe at all, it should never be stored in a local variable, instead it should be saved in CallContext or somewhere else.
	 */
	private List<AlertBean> alertBeans = new ArrayList<>();

	static{
		decoratorRegistry = DecoratorRegistryFactory.getDecoratorRegistry();
	}

	/**
	 * Creates a new action.
	 */
	protected BaseMoskitoUIAction(){
		super();
	}

	public String getSubsystem(){
		return "moskitoUI";
	}

	public String getProducerId(){
		if (myProducerId==null)
			myProducerId = "moskito."+getClass().getName().substring(getClass().getName().lastIndexOf('.')+1);
		return myProducerId;
	}

	/**
	 * Returns the specified forward.
	 * @param req http servlet request.
	 * @return
	 */
	protected String getForward(HttpServletRequest req){
		String forward = req.getParameter(PARAM_FORWARD);
		if (forward==null)
			forward = DEFAULT_FORWARD;
		return forward;
	}

	/**
	 * Returns the currently selected interval, either as parameter or from session.
	 * @param req
	 * @return
	 */
	protected String getCurrentInterval(HttpServletRequest req){
		return getCurrentInterval(req, true);
	}

	/**
	 * Returns the currently selected interval, either as parameter or from session.
	 * @param req
	 * @param saveToSession - if true the value from request will be stored to session.
	 * @return name of the interval.
	 */
	protected String getCurrentInterval(HttpServletRequest req, boolean saveToSession){
		String intervalParameter = req.getParameter(PARAM_INTERVAL);
		String interval = intervalParameter;
		if (interval==null){
			interval = (String)req.getSession().getAttribute(BEAN_INTERVAL);
			if (interval == null)
				interval = DEFAULT_INTERVAL;
		}
		if (intervalParameter!=null && saveToSession)
			req.getSession().setAttribute(BEAN_INTERVAL, interval);
		return interval;
	}

	/**
	 * Returns the currently selected unit either from request or session.
	 * @param req
	 * @return currently selected unit bean.
	 */
	protected UnitBean getCurrentUnit(HttpServletRequest req){
		return getCurrentUnit(req, true);
	}

	/**
	 * Returns the currently selected unit either from request or session.
	 * @param req
	 * @param saveToSession - if true the request parameter  will be saved into session.
	 * @return
	 */
	protected UnitBean getCurrentUnit(HttpServletRequest req, boolean saveToSession){
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
		if (saveToSession)
			req.getSession().setAttribute(BEAN_UNIT, ret);
		return ret;
	}

	protected void prepareBasics(HttpServletRequest req){
		String currentIntervalName = getCurrentInterval(req);

		MoSKitoWebUIContext context = MoSKitoWebUIContext.getCallContextAndReset();
		context.setCurrentIntervalName(currentIntervalName);
		context.setCurrentSession(req.getSession());

		String paramFilteringOff = req.getParameter(PARAM_FILTERING);
		if (paramFilteringOff!=null && paramFilteringOff.equals(PARAM_VALUE_FILTER_OFF))
			context.addAttribute(Features.PRODUCER_FILTERING.name(), Boolean.FALSE);


		//we need to set navi/subnavi item to none, in order to prevent exceptions in rendering of the menu.
		NaviItem currentNaviItem = getCurrentNaviItem();
		if (currentNaviItem==null)
			currentNaviItem = NaviItem.NONE;
		req.setAttribute("currentNaviItem", currentNaviItem);
		NaviItem currentSubNaviItem = getCurrentSubNaviItem();
		if (currentSubNaviItem==null)
			currentSubNaviItem = NaviItem.NONE;
		req.setAttribute("currentSubNaviItem", currentSubNaviItem);

		////////////// prepare units
		req.setAttribute("units", AVAILABLE_UNITS_LIST);
		//ensure current unit is properly set.
		context.setCurrentTimeUnit(getCurrentUnit(req).getUnit());

		//Link to current page
		req.setAttribute("linkToCurrentPage", DeepLinkUtil.makeDeepLink(getLinkToCurrentPage(req)));
		req.setAttribute("linkToCurrentPageAsXml", maskAsXML(getLinkToCurrentPage(req)));
		req.setAttribute("linkToCurrentPageAsCsv", maskAsCSV(getLinkToCurrentPage(req)));
		req.setAttribute("linkToCurrentPageAsJson", maskAsJSON(getLinkToCurrentPage(req)));
		// Link to remote monitoring connection
		req.setAttribute("remoteLink", DeepLinkUtil.getCurrentRemoteConnectionLink());
		req.setAttribute("betaMode", WebUIConfig.getInstance().isBetaMode());
		req.setAttribute("displayIphoneIcons", WebUIConfig.getInstance().isDisplayIphoneIcons());

	}

	/**
	 * Acquire remote connection url from request parameter.
	 * If current user is not connected to application
	 * from url - moskito will be connected to this application.
	 * This made up for possibility to share links on moskito-inspect pages.
	 *
	 * This method expects, that request will contain `remoteConnection` parameter
	 * with value contains host and port separated by column. Example : `localhost:9401`.
	 * If request have no such parameter - current connection will not be changed.
	 *
	 * @param req current request object
	 */
	private void fetchRemoteConnectionFromUrl(HttpServletRequest req){

		String connection = req.getParameter("remoteConnection");

		if(connection != null) try {
			connection = URLDecoder.decode(connection, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		else
			return;

		String[] remoteHostAndPort;

		if( (remoteHostAndPort = connection.split(":")).length == 2 ){

			String remoteHost = remoteHostAndPort[0];
			int remotePort;

			try {
				remotePort = Integer.parseInt(remoteHostAndPort[1]);
			}catch (NumberFormatException e){
				return;
			}

			try {
				if (!APILookupUtility.isLocal() &&
						APILookupUtility.getCurrentRemoteInstance().equalsByKey(remoteHost + '-' + remotePort))
					return; // Current remote connection already points to url from params
			}catch (IllegalStateException ignored){
				// Means moskito has no remote connection while GET query specifies one.
			}

			RemoteInstance newRemoteInstance = new RemoteInstance();
			newRemoteInstance.setHost(remoteHost);
			newRemoteInstance.setPort(remotePort);
			WebUIConfig.getInstance().addRemote(newRemoteInstance);
			APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.REMOTE);
			APILookupUtility.setCurrentRemoteInstance(newRemoteInstance);

		}

	}

	/**
	 * Checks is current action available only in beta mode
	 * by {@link BetaAction} annotation.
	 *
	 * @return  true  - this action is beta
	 * 			false -
	 */
	private boolean isBetaAction(){
		return this.getClass().getAnnotation(BetaAction.class) != null;
	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		alertBeans.clear();

		if(!WebUIConfig.getInstance().isBetaMode() && isBetaAction()){
			res.sendError(403, "This action available only in beta mode.");
			throw new AbortExecutionException();
		}

		String currentIntervalName = getCurrentInterval(req);

		prepareBasics(req);

		fetchRemoteConnectionFromUrl(req);

		//configuration issues.
		req.setAttribute("config", WebUIConfig.getInstance());

		//moved connectivity app, otherwise we don't have selector in case of an error.
		//set pagename
		req.setAttribute("pagename", getPageName());
		req.setAttribute("connection", APILookupUtility.describeConnectivity());

		//prepare selector.
		LinkedList<LabelValueBean> connectivityOptions = new LinkedList<>();
		connectivityOptions.add(new LabelValueBean("Local", "Local"));
		for (RemoteInstance ri : WebUIConfig.getInstance().getRemotes()){
			connectivityOptions.add(new LabelValueBean(ri.toString(), ri.getSelectKey()));
		}
		req.setAttribute("connectivityOptions", connectivityOptions);
		req.setAttribute("selectedConnectivity", APILookupUtility.isLocal() ? "Local" : APILookupUtility.getCurrentRemoteInstance().getSelectKey());


		///////////// prepare intervals
		req.setAttribute("intervals", APILookupUtility.getAdditionalFunctionalityAPI().getIntervalInfos());
		req.setAttribute("currentInterval", currentIntervalName);

		//prepare interval timestamp and age.
		Long currentIntervalUpdateTimestamp = getAdditionalFunctionalityAPI().getIntervalUpdateTimestamp(currentIntervalName);
		if (currentIntervalUpdateTimestamp==null){
			req.setAttribute("currentIntervalUpdateTimestamp", "Never");
			req.setAttribute("currentIntervalUpdateAge", "n.A.");
		}else{
			req.setAttribute("currentIntervalUpdateTimestamp", NumberUtils.makeISO8601TimestampString(currentIntervalUpdateTimestamp));
			req.setAttribute("currentIntervalUpdateAge", (System.currentTimeMillis()-currentIntervalUpdateTimestamp)/1000+" seconds");
		}

		req.setAttribute("currentCategory", "");
		req.setAttribute("currentSubsystem", "");

		setSystemStatusAttributes(req);

		//check for autoreload.
		String pReloadInterval = req.getParameter(PARAM_RELOAD_INTERVAL);
		if (pReloadInterval!=null && pReloadInterval.length()>0){
			if (pReloadInterval.equalsIgnoreCase("OFF")){
				req.getSession().removeAttribute(BEAN_AUTORELOAD);
			}else{
				try{
					Integer.parseInt(pReloadInterval);
					req.getSession().setAttribute(BEAN_AUTORELOAD, pReloadInterval);
				}catch(NumberFormatException e){
					log.warn("Attempt to set illegal reload time "+pReloadInterval+" ignored: ", e);
				}
			}
		}

		checkNavigationMenuState(req);

		//set page title.
		String subTitle = getSubTitle();
		String title = DEFAULT_TITLE;
		if (subTitle!=null && subTitle.length()>0){
			title += " :: "+subTitle;
		}
		req.setAttribute("title", title);

		req.setAttribute("exportSupported", exportSupported());
		req.setAttribute("logoUrl", WebUIConfig.getInstance().getCustomLogoUrl());


		//check if a new threshold has been added.
		if(req.getParameter("newThreshold")!=null){
			req.setAttribute("newThresholdAdded","true");
			req.setAttribute("newThresholdName",req.getParameter("newThreshold"));
		}

		//check if a new accumulator has been added.
		if (req.getParameter("newAccumulator") != null) {
			req.setAttribute("newAccumulatorAdded", "true");
			req.setAttribute("newAccumulatorName", req.getParameter("newAccumulator"));
		}
	}

	private void setSystemStatusAttributes(HttpServletRequest req) throws APIException {
		SystemStatusAO systemStatus = getAdditionalFunctionalityAPI().getSystemStatus();
		ThresholdStatus worstThreshold = systemStatus.getWorstThreshold();
		KillSwitchConfiguration killSwitchConfiguration = systemStatus.getKillSwitchConfiguration();

		req.setAttribute("systemStatus", worstThreshold);
		req.setAttribute("systemStatusColor", worstThreshold.toString().toLowerCase());
		req.setAttribute("killSwitchConfiguration", killSwitchConfiguration);

		// notify user that metrics are disabled
		if (killSwitchConfiguration != null && killSwitchConfiguration.isDisableMetricCollection()) {
			addAlert(AlertBean.create(AlertType.DANGER, "Metrics currently disabled"));
		}
	}


	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		long timestamp = System.currentTimeMillis();
		String timestampAsDate = NumberUtils.makeISO8601TimestampString(timestamp);
		req.setAttribute("timestamp", timestamp);
		req.setAttribute("timestampAsDate", timestampAsDate);
		req.setAttribute("alertBeans", alertBeans);
	}

	/**
	 * Returns the link to the current page.
	 * @param req
	 * @return
	 */
	protected abstract String getLinkToCurrentPage(HttpServletRequest req);

	/**
	 * Returns the decorator registry.
	 * @return
	 */
	protected static IDecoratorRegistry getDecoratorRegistry(){
		return decoratorRegistry;
	}

	private String maskAsXML(String link){
		return maskAsExtension(link, ".xml");
	}

	private String maskAsCSV(String link){
		return maskAsExtension(link, ".csv");
	}

	/**
	 * Masks the link as json.
	 * @param link
	 * @return
	 */
	private String maskAsJSON(String link){
		return maskAsExtension(link, ".json");
	}

	private String maskAsExtension(String link, String extension){
		if (link==null || link.length()==0)
			return link;
		int indexOfQ = link.indexOf('?');
		if (indexOfQ==-1)
			return link + extension;
		return link.substring(0,indexOfQ)+extension+link.substring(indexOfQ);
	}

	/**
	 * Returns the highlighted navigation item.
	 * @return
	 */
	protected abstract NaviItem getCurrentNaviItem();

	/**
	 * Returns the highlighted sub navigation item, if subnavigation is active.
	 * This method returns a string instead of naviitem, because we actually don't need
	 * @return
	 */
	protected NaviItem getCurrentSubNaviItem(){
		return NaviItem.NONE;
	}

	/**
	 * Rebuilds query string from source.
	 * @param source original source.
	 * @param params parameters that should be included in the query string.
	 * @return
	 */
	protected String rebuildQueryStringWithoutParameter(String source, String ... params){
		if (source==null || source.length()==0)
			return "";
		if (params == null || params.length==0)
			return source;
		HashSet<String> paramsSet = new HashSet<>(params.length);
		paramsSet.addAll(Arrays.asList(params));
		String[] tokens = StringUtils.tokenize(source, '&');
		StringBuilder ret = new StringBuilder();
		for (String t : tokens){
			String[] values = StringUtils.tokenize(t, '=');
			if (paramsSet.contains(values[0]))
				continue;
			if (ret.length()>0)
				ret.append('&');
			ret.append(values[0]).append('=').append(values.length >= 2 ? values[1] : "");
		}
		return ret.toString();
	}

	/**
	 * Check, if navigation menu default state present in request. If there is no state, set default value.
	 *
	 * @param req
	 * 		{@link HttpServletRequest}
	 */
	private void checkNavigationMenuState(final HttpServletRequest req) {
		if (req == null)
			return;

		if (req.getSession().getAttribute(ATTR_IS_NAV_MENU_COLLAPSED) != null)
			return;

		// here we can specify default collapse value
		req.getSession().setAttribute(ATTR_IS_NAV_MENU_COLLAPSED, false);
	}

	protected String getPageName(){ return "unnamed"; }

	protected ProducerAPI getProducerAPI(){
		return APILookupUtility.getProducerAPI();
	}

	protected ThresholdAPI getThresholdAPI(){
		return APILookupUtility.getThresholdAPI();
	}

	protected LoadFactorsAPI getLoadFactorsAPI(){
		return APILookupUtility.getLoadFactorsAPI();
	}

	protected NowRunningAPI getNowRunningAPI(){
		return APILookupUtility.getNowRunningAPI();
	}

	protected JourneyAPI getJourneyAPI(){
		return APILookupUtility.getJourneyAPI();
	}

	protected AccumulatorAPI getAccumulatorAPI(){
		return APILookupUtility.getAccumulatorAPI();
	}

	protected GaugeAPI getGaugeAPI() {
		return APILookupUtility.getGaugeAPI();
	}

	protected AdditionalFunctionalityAPI getAdditionalFunctionalityAPI(){
		return APILookupUtility.getAdditionalFunctionalityAPI();
	}

	protected DashboardAPI getDashboardAPI(){
		return APILookupUtility.getDashboardAPI();
	}

	protected TracerAPI getTracerAPI(){
		return APILookupUtility.getTracerAPI();
	}

	protected TagAPI getTagAPI(){
		return APILookupUtility.getTagAPI();
	}

	protected String getSubTitle(){
		return "";
	}

	/**
	 * Override and return true if your action supports csv/xml/json export.
	 * @return
	 */
	protected boolean exportSupported(){
		return false;
	}

	/**
	 * Sets an info message that can be shown on next screen. The info message is readable exactly once.
	 * @param message the info message.
	 */
	protected void setInfoMessage(String message) {
		try {
			APICallContext.getCallContext().getCurrentSession().setAttribute("infoMessage", APISession.POLICY_FLASH, message);
		}catch(NullPointerException e){
			log.error("Can't set info message (flash) due", e);
			log.error("APICallContext: "+APICallContext.getCallContext());
			if (APICallContext.getCallContext()!=null){
				log.error("Current Session: "+APICallContext.getCallContext().getCurrentSession());
			}
		}
	}

	/**
	 * Returns a previously set info message or null if no message has been set.
	 * @return
	 */
	protected String getInfoMessage() {
		try {
			return (String) APICallContext.getCallContext().getCurrentSession().getAttribute("infoMessage");
		}catch(Exception any){
			return null;
		}
	}

	protected void addInfoMessage(String message){
		String previousMessage = getInfoMessage();
		if (previousMessage == null)
			previousMessage = "";
		if (previousMessage.length()>0)
			previousMessage+= " ";
		setInfoMessage(previousMessage+message);
	}


	protected void addAlert(AlertBean bean) {
		this.alertBeans.add(bean);
	}

	public String getProducerIdParameter(HttpServletRequest request){
		String producerId = request.getParameter(PARAM_PRODUCER_ID);
		return producerId;
	}
}
