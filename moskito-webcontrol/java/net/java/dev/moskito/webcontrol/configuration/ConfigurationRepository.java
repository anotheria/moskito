package net.java.dev.moskito.webcontrol.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.IOUtils;
import net.java.dev.moskito.webcontrol.guards.Guard;
import net.java.dev.moskito.webcontrol.repository.ColumnType;
import net.java.dev.moskito.webcontrol.repository.TotalFormulaType;

import org.configureme.Configuration;
import org.configureme.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum ConfigurationRepository {

	/**
	 * singleton
	 */
	INSTANCE;

	private ConcurrentMap<String, ViewConfiguration> views;
	private List<SourceConfiguration> sources;
	private List<IntervalConfiguration> intervals;

	private ConfigurationRepository() {
		views = new ConcurrentHashMap<String, ViewConfiguration>();
		sources = new CopyOnWriteArrayList<SourceConfiguration>();
		intervals = new ArrayList<IntervalConfiguration>();
	}

	/***********************************/
	/********* intervals ***************/
	/***********************************/
	public void addInterval(String name, String containerName) {
		intervals.add(new IntervalConfiguration(name, containerName));
	}

	public List<String> getIntervalsNames() {
		List<String> res = new ArrayList<String>();
		for (IntervalConfiguration config : intervals) {
			res.add(config.getName());
		}
		return res;
	}

	public String getContainerName(String name) {
		for (IntervalConfiguration config : intervals) {
			if (name.equals(config.getName())) {
				return config.getContainerName(); 
			}
		}
		return null;
	}

	/***********************************/
	/********* views *******************/
	/***********************************/
	public void addView(ViewConfiguration toAdd) {
		views.put(toAdd.getName(), toAdd);
	}

	public List<ViewConfiguration> getAllViews() {
		ArrayList<ViewConfiguration> ret = new ArrayList<ViewConfiguration>();
		ret.addAll(views.values());
		return ret;
	}

	public ViewConfiguration getView(String viewName) {
		ViewConfiguration ret = views.get(viewName);
		if (ret == null)
			throw new IllegalArgumentException("Unknown view: " + viewName);
		return ret;
	}

	public List<String> getViewNames() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(new TreeSet<String>(views.keySet()));
		Collections.sort(ret);
		return ret;
	}

	/***********************************/
	/********* sources *****************/
	/***********************************/
	public List<SourceConfiguration> getSources() {
		ArrayList<SourceConfiguration> ret = new ArrayList<SourceConfiguration>();
		ret.addAll(sources);
		return ret;
	}

	public void addSource(SourceConfiguration toAdd) {
		if (!sources.contains(toAdd)) {
			sources.add(toAdd);
		}
	}

	/**
	 * 
	 */
	public void loadViewsConfiguration() throws IOException, JSONException {
		{
			InputStream intervalsIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("intervals.json");
			String content = IOUtils.getInputStreamAsString(intervalsIS);
			JSONArray intrvls = new JSONObject(content).getJSONArray("intervals");
			for (int i = 0; i < intrvls.length(); i++) {
				JSONObject interval = intrvls.getJSONObject(i);
				addInterval(interval.getString("name"), interval.getString("containerName"));
			}
		}

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("views.json");
		String content = IOUtils.getInputStreamAsString(is);
		JSONObject views = new JSONObject(content);

		Configuration serversConfig = ConfigurationManager.INSTANCE.getConfiguration("servers");

		{
			String appPath = serversConfig.getAttribute("app-path");
			String baseUrl = views.getString("baseURL");
			JSONArray listServers = new JSONArray(serversConfig.getAttribute("list-servers"));
			for (int i = 0; i < listServers.length(); i++) {
				String server = (String) listServers.get(i);
				String url = new StringBuilder().append(serversConfig.getAttribute(server)).append(appPath).append(baseUrl).toString();
				ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration(server, url));
			}
		}

		JSONArray viewList = views.getJSONArray("views");
		for (int i = 0; i < viewList.length(); i++) {
			JSONObject view = viewList.getJSONObject(i);
			ViewConfiguration viewConfig = new ViewConfiguration(view.getString("name"));

			JSONArray columns = view.getJSONArray("columns");
			for (int k = 0; k < columns.length(); k++) {
				JSONObject column = columns.getJSONObject(k);
				String columnName = column.getString("attribute");
				System.out.println("columnName=" + columnName);
				String name = column.getString("name");
				String type = column.getString("type");
				String klass = (String) column.opt("class");
				if (StringUtils.isEmpty(klass)) {
					klass = "unknown";
				}

				Boolean visible = column.getBoolean("visible");
				String path = (String) column.opt("path");
				ViewField field = new ViewField(name, columnName, ColumnType.convert(type), klass, visible, path);

				JSONArray inputs = (JSONArray) column.opt("inputs");
				if (inputs != null) {
					List<String> inputsList = new ArrayList<String>();
					for (int j = 0; j < inputs.length(); j++) {
						inputsList.add(inputs.getString(j));
					}
					field.setInputs(inputsList);
				}

				String total = (String) column.opt("total");
				if (StringUtils.isEmpty(total)) {
					total = "EMPTY";
				}
				
				String guard = (String) column.opt("guard");
				if (!StringUtils.isEmpty(guard)) {
					try {
						@SuppressWarnings("unchecked")
						Class guardClass = Class.forName(guard);
						Guard instance = (Guard)guardClass.newInstance();
						String guardRules = (String) column.opt("guardRules");
						if (!StringUtils.isEmpty(guardRules)) {
							instance.setRules(guardRules);
						}
						field.setGuard(instance);
					} catch (Exception e) {
						
					}
				}
				

				field.setTotal(TotalFormulaType.convert(total));
				viewConfig.addField(field);
			}

			ConfigurationRepository.INSTANCE.addView(viewConfig);
		}

	}

}
