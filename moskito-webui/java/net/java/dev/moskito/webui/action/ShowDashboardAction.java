package net.java.dev.moskito.webui.action;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.anotheria.util.TimeUnit;
import net.java.dev.moskito.webui.bean.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Dashboard action.
 *
 * @author dsilenko
 */
public class ShowDashboardAction extends BaseMoskitoUIAction{
	private static final String DASHBOARD_PARAMETER_NAME = "dashboard";
	private static final String DELETE_WIDGET_PARAMETER_NAME = "deleteWidget";
	private static final String WIDGET_NAME_PARAMETER_NAME = "widgetName";
	private static final String WIDGET_TYPE_PARAMETER_NAME = "widgetType";
	private static final String RELOAD_PARAMETER_NAME = "reload";
	private static final String DASHBOARDS_COOKIE_NAME = "dashboards";

	private List<DashboardBean> dashboards = new ArrayList<DashboardBean>();
	private String selectedDashboardName = null;

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		if ( !StringUtils.isEmpty(req.getParameter(DASHBOARD_PARAMETER_NAME)) )
			selectedDashboardName = req.getParameter(DASHBOARD_PARAMETER_NAME);

		if ( !StringUtils.isEmpty(req.getParameter(DELETE_WIDGET_PARAMETER_NAME)) ){
			deleteWidget(req.getParameter(DELETE_WIDGET_PARAMETER_NAME), selectedDashboardName);
			saveDashboardsToCookie(req, res);
		}

		List<ProducerDecoratorBean> producerDecoratorBeans = getDecoratedProducers(req, getAPI().getAllProducers(), new HashMap<String, GraphDataBean>());
		refreshWidgetsData(selectedDashboardName, producerDecoratorBeans);


		if (/*widgets.isEmpty() || */req.getParameter(RELOAD_PARAMETER_NAME) != null){
			try {
				loadDashboardsFromCookie(producerDecoratorBeans, req);
			} catch (JSONException e) {}
		} else {
			List<String> configAttributes = Collections.list(req.getParameterNames());
			configAttributes.remove(WIDGET_NAME_PARAMETER_NAME);
			configAttributes.remove(WIDGET_TYPE_PARAMETER_NAME);

			List<DashboardWidgetBean.ProducerGroup> widgetContent = getWidgetContent(producerDecoratorBeans, configAttributes);
			putWidgetToDashboard(selectedDashboardName, req.getParameter(WIDGET_NAME_PARAMETER_NAME), req.getParameter(WIDGET_TYPE_PARAMETER_NAME), widgetContent, configAttributes);

			saveDashboardsToCookie(req, res);
		}


		List<DashboardWidgetBean> widgetsLeft = new ArrayList<DashboardWidgetBean>();
		List<DashboardWidgetBean> widgetsRight = new ArrayList<DashboardWidgetBean>();

		DashboardBean selectedDashboard = getDashboardByName(selectedDashboardName);
		if (selectedDashboard!=null &&  selectedDashboard.getWidgets()!=null){
			for (DashboardWidgetBean widget : selectedDashboard.getWidgets())
				if (widgetsLeft.size() <= widgetsRight.size())
					widgetsLeft.add(widget);
				else
					widgetsRight.add(widget);
		}


		req.setAttribute("decorators", producerDecoratorBeans);
		req.setAttribute("dashboards", dashboards);
		req.setAttribute("selectedDashboardName", selectedDashboardName);
		//req.setAttribute("widgets", widgets);
		req.setAttribute("widgetsLeft", widgetsLeft);
		req.setAttribute("widgetsRight", widgetsRight);
		//req.setAttribute("graphDatas", graphData.values());
		req.setAttribute("pageTitle", "Dashboard");

		return mapping.findForward( getForward(req) );
	}

	/**
	 * Delete widget with specified name from given dashboard.
	 *
	 * @param widgetName name of widget that should be deleted.
	 * @param dashboardName name of dashboard from which widget should be deleted
	 */
	private void deleteWidget(String widgetName, String dashboardName){
		DashboardBean dashboard = getDashboardByName(dashboardName);
		if (dashboard == null)
			return;

		List<DashboardWidgetBean> widgets = dashboard.getWidgets();
		for (int i=0; i<widgets.size(); i++)
			if ( widgets.get(i).getName().equals(widgetName) ){
				widgets.remove(i);
				return;
			}
	}

	/**
	 * Put dashboard to local dashboards collection.
	 *
 	 * @param dashboardBean dashboard to put
	 */
	private void putDashboard(DashboardBean dashboardBean){
		for (DashboardBean dashboard : dashboards)
			if ( dashboard.getName().equals(dashboardBean.getName()) ){
				dashboard = dashboardBean;
				return;
			}

		dashboards.add(dashboardBean);
	}

	/**
	 * Perform saving all dashboards into cookie.
	 * @param req
	 * @param res
	 */
	private void saveDashboardsToCookie(HttpServletRequest req, HttpServletResponse res) {
		int cookieMaxAge = Long.valueOf( TimeUnit.YEAR.getMillis() / 1000).intValue();

		String jsonText = "";
		for (DashboardBean dashboard : dashboards){
			jsonText += "'" + dashboard.getName() + "':{";
			String widgetJson = "";

			for (DashboardWidgetBean widget : dashboard.getWidgets()){
				String widgetConf = "";
				for(String attribute : widget.getConfigAttributes())
					widgetConf += (widgetConf.length()== 0 ? "" : ",") + attribute;

				widgetJson += "'" + widget.getName() + "'" + ":{attributes:\"" + widgetConf +"\", type:\"" + widget.getType() + "\"},";
			}
			jsonText += widgetJson + "},";
		}

		Cookie cookie = new Cookie(DASHBOARDS_COOKIE_NAME, "{"+jsonText+"}");
		cookie.setMaxAge(cookieMaxAge);
		cookie.setPath(req.getContextPath());

		res.addCookie(cookie);
	}

	/**
	 * Creates content for widget. Content - is a representation of html form with selected properties and loaded values.
	 *
	 * @param producerDecoratorBeans data for widgets
	 * @param attributes config for widgets
	 * @return content for widget
	 */
	private List<DashboardWidgetBean.ProducerGroup> getWidgetContent(List<ProducerDecoratorBean> producerDecoratorBeans, List<String> attributes){
		List<DashboardWidgetBean.ProducerGroup> producerGroups = new ArrayList<DashboardWidgetBean.ProducerGroup>();


		for ( ProducerDecoratorBean producerDecoratorBean : producerDecoratorBeans ){
			if (producerDecoratorBean.getVisibility().isHidden())
				continue;

			List<DashboardWidgetBean.Caption> captions = new ArrayList<DashboardWidgetBean.Caption>();
			List<DashboardWidgetBean.Producer> producers = new ArrayList<DashboardWidgetBean.Producer>();

			boolean captionSelected=false;
			boolean producerSelected=false;

			List<Integer> indexes = new ArrayList<Integer>();
			int i = 0;
			for (StatCaptionBean statCaptionBean : producerDecoratorBean.getCaptions()){
				DashboardWidgetBean.Caption caption = new DashboardWidgetBean.Caption(statCaptionBean.getCaption());
				if (attributes.contains(producerDecoratorBean.getName()+"_"+statCaptionBean.getCaption())){
					indexes.add(i);
					caption.setSelectedCaption(true);
					captionSelected = true;
				}
				captions.add(caption);
				i++;
			}

			for (ProducerBean producerBean : producerDecoratorBean.getProducers()){
				DashboardWidgetBean.Producer producer = new DashboardWidgetBean.Producer(producerBean.getId());
				if (attributes.contains(producerBean.getId())){
					producer.setSelectedProducer(true);
					producerSelected = true;
					for (int index : indexes)
						producer.addValue(producerBean.getValues().get(index).getValue());
				}
				producers.add(producer);
			}

			DashboardWidgetBean.ProducerGroup producerGroup = new DashboardWidgetBean.ProducerGroup(producerDecoratorBean.getName());
			producerGroup.setCaptions(captions);
			producerGroup.setProducers(producers);

			if (captionSelected && producerSelected)
				producerGroup.setSelectedGroup(true);


			producerGroups.add(producerGroup);
		}

		return producerGroups;
	}

	/**
	 * Loads dashboards with widgets from cookies.
	 *
	 * @param producerDecoratorBeans data for widgets
	 * @param req HttpServletRequest
	 * @throws JSONException
	 */
	private void loadDashboardsFromCookie(List<ProducerDecoratorBean> producerDecoratorBeans, HttpServletRequest req) throws JSONException {
		String jsonText = null;
		Cookie[] cookies = req.getCookies();

		if ( cookies == null )
			return;

		for ( Cookie cookie : cookies )
			if ( cookie.getName().equals(DASHBOARDS_COOKIE_NAME) ){
				jsonText = cookie.getValue();
				break;
			}

		if (jsonText == null)
			return;

		JSONObject dashboardsJSON = new JSONObject(jsonText);
		String[] dashboardNames = JSONObject.getNames(dashboardsJSON);
		if (dashboardNames ==null)
			return;

		for (String dashboardName : dashboardNames){
			JSONObject widgetJSON = dashboardsJSON.getJSONObject(dashboardName);
			String[] widgetNames = JSONObject.getNames(widgetJSON);
			if (widgetNames == null ){
				putDashboard(new DashboardBean(dashboardName));
				continue;
			}

			for ( String widgetName : widgetNames ){
				JSONObject widgetConf = widgetJSON.getJSONObject(widgetName);

				List<String> configAttributes = Arrays.asList( widgetConf.get("attributes").toString().split(",") );
				putWidgetToDashboard(dashboardName, widgetName, widgetConf.get("type").toString(), getWidgetContent(producerDecoratorBeans, configAttributes), configAttributes);
			}
		}

	}

	/**
	 * Refreshes data for all widgets in specified dashboard.
	 *
	 * @param dashboardName name of dashboard that should be refreshed
	 * @param producerDecoratorBeans data for widgets
	 */
	private void refreshWidgetsData(String dashboardName, List<ProducerDecoratorBean> producerDecoratorBeans){
		for (DashboardBean dashboard : dashboards)
			if ( dashboard.getName().equals(dashboardName)){
				for (DashboardWidgetBean widget : dashboard.getWidgets())
					widget.setProducerGroups( getWidgetContent(producerDecoratorBeans, widget.getConfigAttributes()));
			}
	}

	/**
	 * Puts widget into specified dashboard. If specified dashboard is absent it wil be created automatically.
 	 * 
	 * @param dashboardName name of dashboard into which widget will be puted
	 * @param widgetName widget name
	 * @param widgetTypeName widget type name
	 * @param producerGroups content of widget
	 * @param configAttributes config that helps for content for widget
	 */
	private void putWidgetToDashboard(String dashboardName, String widgetName, String widgetTypeName, List<DashboardWidgetBean.ProducerGroup> producerGroups, List<String> configAttributes){
		if ( StringUtils.isEmpty(dashboardName))
			return;

		DashboardBean dashboardBean = getDashboardByName(dashboardName);
		if (dashboardBean == null ){
			dashboardBean = new DashboardBean(dashboardName);
			dashboards.add(dashboardBean);
		}

		if (StringUtils.isEmpty(widgetName))
			return;

		List<DashboardWidgetBean> widgetBeans = dashboardBean.getWidgets();

		for (DashboardWidgetBean widget : widgetBeans)
			if (widget.getName().equals(widgetName)){
				widget.setProducerGroups(producerGroups);
				widget.setConfigAttributes(configAttributes);
				return;
			}


		WidgetType widgetType = WidgetType.TABLE;
		try {
			widgetType = WidgetType.valueOf(widgetTypeName);
		} catch (Exception s){}

		widgetBeans.add(new DashboardWidgetBean(widgetName, widgetType, producerGroups, configAttributes));
	}

	/**
	 * If there is no dashboard with given name than null will be returned.
	 *
	 * @param dashboardName dashboard name
	 * @return dash board with specified name
	 */
	private DashboardBean getDashboardByName(String dashboardName){
		for (DashboardBean dashboard : dashboards)
			if ( dashboard.getName().equals(dashboardName) )
				return dashboard;

		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskDashBoard?ts=" + System.currentTimeMillis();
	}


	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.DASHBOARD;
	}


}
