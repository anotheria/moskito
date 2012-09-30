package net.anotheria.moskito.webui.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.bean.DashboardBean;
import net.anotheria.moskito.webui.bean.DashboardWidgetBean;
import net.anotheria.moskito.webui.bean.GraphDataBean;
import net.anotheria.moskito.webui.bean.NaviItem;
import net.anotheria.moskito.webui.bean.ProducerBean;
import net.anotheria.moskito.webui.bean.ProducerDecoratorBean;
import net.anotheria.moskito.webui.bean.StatCaptionBean;
import net.anotheria.moskito.webui.bean.WidgetType;
import net.anotheria.util.StringUtils;
import net.anotheria.util.TimeUnit;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Dashboard action. This code is yet experimental and already unsupported.
 *
 * @author dsilenko
 */
public class ShowDashboardAction extends BaseMoskitoUIAction {

	private static final String DASHBOARD_PARAMETER_NAME = "dashboard";
	private static final String DELETE_WIDGET_PARAMETER_NAME = "deleteWidget";
	private static final String WIDGET_NAME_PARAMETER_NAME = "widgetName";
	private static final String WIDGET_TYPE_PARAMETER_NAME = "widgetType";
	private static final String RELOAD_PARAMETER_NAME = "reload";
	private static final String DASHBOARDS_COOKIE_NAME = "dashboards";

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws JSONException {

		System.out.println("REQ in ");
		String debug = "";
		try {
			@SuppressWarnings("unchecked") Enumeration<String> en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String p = "" + en.nextElement();
				if (debug.length() > 0)
					debug += ", ";
				debug += p + "= " + req.getParameter(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--> " + debug);

		String selectedDashboardName = getSelectedDashboardNameFromSession(req);

		if (!StringUtils.isEmpty(req.getParameter(DELETE_WIDGET_PARAMETER_NAME))) {
			deleteWidget(req, req.getParameter(DELETE_WIDGET_PARAMETER_NAME), selectedDashboardName);
			saveDashboardsToCookie(req, res);
		}

		List<ProducerDecoratorBean> producerDecoratorBeans = getDecoratedProducers(req, getAPI().getAllProducers(), new HashMap<String, GraphDataBean>());
		refreshWidgetsData(req, selectedDashboardName, producerDecoratorBeans);


		if (/*widgets.isEmpty() || */req.getParameter(RELOAD_PARAMETER_NAME) != null) {
			try {
				loadDashboardsFromCookie(producerDecoratorBeans, req);
			} catch (JSONException e) {
				System.out.println(e);
				//todo handle this case and log
			}
		} else {
			@SuppressWarnings("unchecked")
			List<String> configAttributes = Collections.list(req.getParameterNames());
			configAttributes.remove(WIDGET_NAME_PARAMETER_NAME);
			configAttributes.remove(WIDGET_TYPE_PARAMETER_NAME);

			List<DashboardWidgetBean.ProducerGroup> widgetContent = getWidgetContent(producerDecoratorBeans, configAttributes);
			putWidgetToDashboard(req, selectedDashboardName, req.getParameter(WIDGET_NAME_PARAMETER_NAME), req.getParameter(WIDGET_TYPE_PARAMETER_NAME), widgetContent, configAttributes);

			saveDashboardsToCookie(req, res);
		}


		List<DashboardWidgetBean> widgetsLeft = new ArrayList<DashboardWidgetBean>();
		List<DashboardWidgetBean> widgetsRight = new ArrayList<DashboardWidgetBean>();

		DashboardBean selectedDashboard = getDashboardByName(req, selectedDashboardName);
		if (selectedDashboard != null && selectedDashboard.getWidgets() != null) {
			for (DashboardWidgetBean widget : selectedDashboard.getWidgets())
				if (widgetsLeft.size() <= widgetsRight.size())
					widgetsLeft.add(widget);
				else
					widgetsRight.add(widget);
		}


		req.setAttribute("decorators", producerDecoratorBeans);
		req.setAttribute("dashboards", getDashboardsFromSession(req));
		req.setAttribute("selectedDashboardName", selectedDashboardName);
		//req.setAttribute("widgets", widgets);
		req.setAttribute("widgetsLeft", widgetsLeft);
		req.setAttribute("widgetsRight", widgetsRight);
		//req.setAttribute("graphDatas", graphData.values());
		req.setAttribute("pageTitle", "Dashboard");
		req.setAttribute("isCanAddWidget", !StringUtils.isEmpty(selectedDashboardName));

		return mapping.findCommand(getForward(req));
	}

	/**
	 * Delete widget with specified name from given dashboard.
	 *
	 * @param widgetName	name of widget that should be deleted.
	 * @param dashboardName name of dashboard from which widget should be deleted
	 */
	private void deleteWidget(HttpServletRequest req, String widgetName, String dashboardName) {
		if (dashboardName == null)
			return;

		List<DashboardBean> dashboards = getDashboardsFromSession(req);
		for (DashboardBean dashboard : dashboards) {
			if (!dashboard.getName().equalsIgnoreCase(dashboardName))
				continue;

			List<DashboardWidgetBean> widgets = dashboard.getWidgets();
			if (widgets == null)
				continue;
			for (int i = 0; i < widgets.size(); i++)
				if (widgets.get(i).getName().equals(widgetName)) {
					widgets.remove(i);
					return;
				}
		}
	}

	/**
	 * Returns selected dashboard name from session.
	 *
	 * @param req
	 * @return dashboards
	 */
	private String getSelectedDashboardNameFromSession(HttpServletRequest req) {

		String dashboard = req.getParameter(DASHBOARD_PARAMETER_NAME);
		if (!StringUtils.isEmpty(dashboard)) {
			req.getSession().setAttribute("selectedDashboardName", dashboard);
			return dashboard;
		}

		String selectedDashBoardName = (String) req.getSession().getAttribute("selectedDashboardName");
		if (StringUtils.isEmpty(selectedDashBoardName)) {
			List<DashboardBean> dashboardBeans = getDashboardsFromSession(req);
			if (!dashboardBeans.isEmpty())
				req.getSession().setAttribute("selectedDashboardName", dashboardBeans.get(0));
		}

		return selectedDashBoardName;
	}

	/**
	 * Returns dashboards from session.
	 *
	 * @param req
	 * @return dashboards
	 */
	@SuppressWarnings("unchecked")
	private List<DashboardBean> getDashboardsFromSession(HttpServletRequest req) {
		Object dashboards = req.getSession().getAttribute("dashboards");
		if (dashboards == null) {
			ArrayList<DashboardBean> dashboardsNew = new ArrayList<DashboardBean>();
			req.getSession().setAttribute("dashboards", dashboardsNew);
			return dashboardsNew;
		}

		if (dashboards instanceof List)
			return (List<DashboardBean>) dashboards;

		throw new IllegalArgumentException("Wrong attribute in session");
	}

	/**
	 * Put dashboard to sessions dashboard collection.
	 *
	 * @param req
	 * @param dashboardBean dashboard to put
	 */
	private void putDashboardToSession(HttpServletRequest req, DashboardBean dashboardBean) {
		List<DashboardBean> dashboards = getDashboardsFromSession(req);
		for (DashboardBean dashboard : dashboards)
			if (dashboard.getName().equals(dashboardBean.getName())) {
				dashboard = dashboardBean;
				return;
			}

		dashboards.add(dashboardBean);
	}

	/**
	 * Perform saving all dashboards into cookie.
	 *
	 * @param req
	 * @param res
	 */
	private void saveDashboardsToCookie(HttpServletRequest req, HttpServletResponse res) throws JSONException {

		JSONObject jsonDashboards = new JSONObject();
		for (DashboardBean dashboard : getDashboardsFromSession(req)) {
			jsonDashboards.put(dashboard.getName(), toJson(dashboard.getWidgets()));
		}


		Cookie cookie = null;
		try {
			cookie = new Cookie(DASHBOARDS_COOKIE_NAME, URLEncoder.encode( compressString(jsonDashboards.toString()), "UTF-8"));
		} catch (IOException e) {
			System.out.println(e);
		}

		int cookieMaxAge = Long.valueOf(TimeUnit.YEAR.getMillis() / 1000).intValue();
		cookie.setMaxAge(cookieMaxAge);
		cookie.setPath(req.getContextPath());

		res.addCookie(cookie);
	}

	/**
	 * Compresses data using zip deflater.
	 *
	 * @param data data to compress
	 * @return string representation of compressed data, encoded with BASE64Encoder
	 * @throws IOException
	 */
	public static String compressString(String data) throws IOException {
		byte[] input = data.getBytes("UTF-8");
		Deflater df = new Deflater();
		df.setLevel(Deflater.BEST_COMPRESSION);
		df.setInput(input);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
		df.finish();
		byte[] buff = new byte[1024];
		while (!df.finished()) {
			int count = df.deflate(buff);
			baos.write(buff, 0, count);
		}
		baos.close();
		byte[] output = baos.toByteArray();
		
		return Base64.encodeBase64String(output);
	}

	/**
	 * Decompresses data.
	 *
	 * @param data data to decompress
	 * @return decompressed string
	 * @throws IOException
	 * @throws DataFormatException
	 */
	public static String decompressString(String data) throws IOException, DataFormatException {
		byte[] input = Base64.decodeBase64(data);
		
		Inflater ifl = new Inflater();
		ifl.setInput(input);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
		byte[] buff = new byte[1024];
		while (!ifl.finished()) {
			int count = ifl.inflate(buff);
			baos.write(buff, 0, count);
		}
		baos.close();
		byte[] output = baos.toByteArray();

		return new String(output, "UTF-8");
	}

	/**
	 * Make JSON representation of list with widgets.
	 *
	 * @param widgets list of dashboard widgets
	 * @return json representation
	 * @throws JSONException
	 */
	private JSONArray toJson(List<DashboardWidgetBean> widgets) throws JSONException {
		JSONArray jsonWidgets = new JSONArray();
		for (DashboardWidgetBean widget : widgets) {
			JSONObject jsonWidget = new JSONObject();
			jsonWidget.put("name", widget.getName());
			jsonWidget.put("type", widget.getType());
			jsonWidget.put("attributes", widget.getConfigAttributes());

			jsonWidgets.put(jsonWidget);
		}

		return jsonWidgets;
	}

	/**
	 * Creates content for widget. Content - is a representation of html form with selected properties and loaded values.
	 *
	 * @param producerDecoratorBeans data for widgets
	 * @param attributes			 config for widgets
	 * @return content for widget
	 */
	private List<DashboardWidgetBean.ProducerGroup> getWidgetContent(List<ProducerDecoratorBean> producerDecoratorBeans, List<String> attributes) {
		List<DashboardWidgetBean.ProducerGroup> producerGroups = new ArrayList<DashboardWidgetBean.ProducerGroup>();


		for (ProducerDecoratorBean producerDecoratorBean : producerDecoratorBeans) {
			if (producerDecoratorBean.getVisibility().isHidden())
				continue;

			List<DashboardWidgetBean.Caption> captions = new ArrayList<DashboardWidgetBean.Caption>();
			List<DashboardWidgetBean.Producer> producers = new ArrayList<DashboardWidgetBean.Producer>();

			boolean captionSelected = false;
			boolean producerSelected = false;

			List<Integer> indexes = new ArrayList<Integer>();
			int i = 0;
			for (StatCaptionBean statCaptionBean : producerDecoratorBean.getCaptions()) {
				DashboardWidgetBean.Caption caption = new DashboardWidgetBean.Caption(statCaptionBean.getCaption());
				if (attributes.contains(producerDecoratorBean.getName() + "_" + statCaptionBean.getCaption())) {
					indexes.add(i);
					caption.setSelectedCaption(true);
					captionSelected = true;
				}
				captions.add(caption);
				i++;
			}

			for (ProducerBean producerBean : producerDecoratorBean.getProducers()) {
				DashboardWidgetBean.Producer producer = new DashboardWidgetBean.Producer(producerBean.getId());
				if (attributes.contains(producerBean.getId())) {
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
	 * @param req					HttpServletRequest
	 * @throws JSONException
	 */
	private void loadDashboardsFromCookie(List<ProducerDecoratorBean> producerDecoratorBeans, HttpServletRequest req) throws JSONException {
		Cookie[] cookies = req.getCookies();

		if (cookies == null)
			return;
		String cookieValue = null;

		for (Cookie cookie : cookies)
			if (cookie.getName().equals(DASHBOARDS_COOKIE_NAME)) {
				cookieValue = cookie.getValue();
				break;
			}

		if (cookieValue == null)
			return;

		JSONObject jsonDashboards = null;
		try {
			jsonDashboards = new JSONObject(decompressString(URLDecoder.decode(cookieValue, "UTF-8")));
		} catch (IOException e) {
			//todo log here
			System.out.println(e);
		} catch (DataFormatException e) {
			System.out.println(e);
			//todo log here
		}

		String[] dashboardNames = JSONObject.getNames(jsonDashboards);
		if (dashboardNames == null)
			return;

		for (String dashboardName : dashboardNames) {
			JSONArray jsonWidgets = jsonDashboards.getJSONArray(dashboardName);

			for (int i = 0; i < jsonWidgets.length(); i++) {
				JSONObject jsonWidget = jsonWidgets.getJSONObject(i);

				String name = jsonWidget.getString("name");
				String type = jsonWidget.getString("type");

				List<String> attributes = new ArrayList<String>();
				JSONArray jsonAttributes = jsonWidget.getJSONArray("attributes");
				for (int i2 = 0; i2 < jsonAttributes.length(); i2++)
					attributes.add(jsonAttributes.getString(i2));

				putWidgetToDashboard(req, dashboardName, name, type, getWidgetContent(producerDecoratorBeans, attributes), attributes);
			}
		}
	}

	/**
	 * Refreshes data for all widgets in specified dashboard.
	 *
	 * @param req
	 * @param dashboardName		  name of dashboard that should be refreshed
	 * @param producerDecoratorBeans data for widgets
	 */
	private void refreshWidgetsData(HttpServletRequest req, String dashboardName, List<ProducerDecoratorBean> producerDecoratorBeans) {
		for (DashboardBean dashboard : getDashboardsFromSession(req))
			if (dashboard.getName().equals(dashboardName)) {
				for (DashboardWidgetBean widget : dashboard.getWidgets())
					widget.setProducerGroups(getWidgetContent(producerDecoratorBeans, widget.getConfigAttributes()));
			}
	}

	/**
	 * Puts widget into specified dashboard. If specified dashboard is absent it wil be created automatically.
	 *
	 * @param req
	 * @param dashboardName	name of dashboard into which widget will be puted
	 * @param widgetName	   widget name
	 * @param widgetTypeName   widget type name
	 * @param producerGroups   content of widget
	 * @param configAttributes config that helps for content for widget
	 */
	private void putWidgetToDashboard(HttpServletRequest req, String dashboardName, String widgetName, String widgetTypeName, List<DashboardWidgetBean.ProducerGroup> producerGroups, List<String> configAttributes) {
		if (StringUtils.isEmpty(dashboardName))
			return;

		DashboardBean dashboardBean = getDashboardByName(req, dashboardName);
		if (dashboardBean == null) {
			dashboardBean = new DashboardBean(dashboardName);
			putDashboardToSession(req, dashboardBean);
		}

		if (StringUtils.isEmpty(widgetName))
			return;

		List<DashboardWidgetBean> widgetBeans = dashboardBean.getWidgets();

		for (DashboardWidgetBean widget : widgetBeans)
			if (widget.getName().equals(widgetName)) {
				widget.setProducerGroups(producerGroups);
				widget.setConfigAttributes(configAttributes);
				return;
			}


		WidgetType widgetType = WidgetType.TABLE;
		try {
			widgetType = WidgetType.valueOf(widgetTypeName);
		} catch (IllegalArgumentException e) {
			//todo add logging here
		}

		widgetBeans.add(new DashboardWidgetBean(widgetName, widgetType, producerGroups, configAttributes));
	}

	/**
	 * If there is no dashboard with given name than null will be returned.
	 *
	 * @param req
	 * @param dashboardName dashboard name
	 * @return dash board with specified name
	 */
	private DashboardBean getDashboardByName(HttpServletRequest req, String dashboardName) {
		for (DashboardBean dashboard : getDashboardsFromSession(req))
			if (dashboard.getName().equals(dashboardName))
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
