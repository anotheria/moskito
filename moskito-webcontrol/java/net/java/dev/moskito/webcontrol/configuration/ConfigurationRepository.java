package net.java.dev.moskito.webcontrol.configuration;

import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.IOUtils;
import net.java.dev.moskito.webcontrol.feed.FeedGetter;
import net.java.dev.moskito.webcontrol.feed.HttpGetter;
import net.java.dev.moskito.webcontrol.guards.Guard;
import net.java.dev.moskito.webcontrol.repository.ColumnType;
import net.java.dev.moskito.webcontrol.repository.TotalFormulaType;
import net.java.dev.moskito.webcontrol.ui.beans.PatternWithName;
import org.apache.log4j.Logger;
import org.configureme.Configuration;
import org.configureme.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConfigurationRepository {

	/**
	 * singleton
	 */
	INSTANCE;

	private ConcurrentMap<String, ViewConfiguration> views;
	private List<SourceConfiguration> sources;
	private List<IntervalConfiguration> intervals;

	private List<ViewField> avaibleColumns;

	private static final Logger log = Logger.getLogger(ConfigurationRepository.class);

	private ConfigurationRepository() {
		views = new ConcurrentHashMap<String, ViewConfiguration>();
		sources = new CopyOnWriteArrayList<SourceConfiguration>();
		intervals = new ArrayList<IntervalConfiguration>();
		avaibleColumns = new ArrayList<ViewField>();
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

	/***********************************/
	/********* sources *****************/
	/***********************************/
	public List<ViewField> getAvailableColumns() {
		ArrayList<ViewField> ret = new ArrayList<ViewField>();
		ret.addAll(avaibleColumns);
		return ret;
	}

	public void addAvaiableColumn(ViewField column) {
		avaibleColumns.add(column);
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
                String username = serversConfig.getAttribute(server + ".username");
                String password = serversConfig.getAttribute(server + ".password");
				ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration(server, url, username, password));
			}
		}

		updateViews(views);

	}
	
	public void updateViews(JSONObject views) throws JSONException {
		ConfigurationRepository.INSTANCE.clearViews();
		JSONArray viewList = views.getJSONArray("views");
		for (int i = 0; i < viewList.length(); i++) {
			JSONObject view = viewList.getJSONObject(i);
			ViewConfiguration viewConfig = new ViewConfiguration(view.getString("name"));

			JSONArray columns = view.getJSONArray("columns");
			for (int k = 0; k < columns.length(); k++) {
				JSONObject column = columns.getJSONObject(k);
				viewConfig.addField(prepareField(column));
			}

			ConfigurationRepository.INSTANCE.addView(viewConfig);
		}
	}

	private void clearViews() {
		views.clear();
	}

	public static ViewField prepareField(JSONObject column) throws JSONException {
		String columnName = column.getString("attribute");
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
				Guard instance = (Guard) guardClass.newInstance();
				String guardRules = (String) column.opt("guardRules");
				if (!StringUtils.isEmpty(guardRules)) {
					instance.setRules(guardRules);
				}
				field.setGuard(instance);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		String format = (String) column.opt("format");
		if (!StringUtils.isEmpty(format)) {
			field.setFormat(format.trim());
		}

		field.setTotal(TotalFormulaType.convert(total));

		return field;
	}

	public void loadAllAvailableColumns() throws JSONException, IOException, XPathExpressionException, CloneNotSupportedException {
		
		String interval = null;
		List<String> intervals = ConfigurationRepository.INSTANCE.getIntervalsNames();
		if (intervals.size() > 0) {
			interval = intervals.size() > 0 ? intervals.get(0) : "default";
		}

		SourceConfiguration source = null;
		List<SourceConfiguration> sourceList = ConfigurationRepository.INSTANCE.getSources();
		if (sourceList.size() > 0) {
			source = sourceList.get(0);
		} else {
			throw new RuntimeException("there is no any server config");
		}
		
		
		FeedGetter getter = new HttpGetter();
//		SourceConfiguration sourceConf = new SourceConfiguration(source.getName(), source.getUrl() + "&pInterval=" + interval);
        SourceConfiguration sourceConf = source.build("&pInterval=" + interval);
		Document doc = getter.retreive(sourceConf);
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("views.json");
		String content = IOUtils.getInputStreamAsString(is);
		JSONObject views = new JSONObject(content);
		
		JSONObject baseColumn = views.getJSONObject("baseColumn");
		
		ViewField baseField = ConfigurationRepository.prepareField(baseColumn);
		
		HashMap<String, ViewField> map = new HashMap<String, ViewField>();
		List<PatternWithName> result = new ArrayList<PatternWithName>();
		result.add(new PatternWithName("", baseField.getPath()));
		buildFullPath(result, doc);
		for (PatternWithName p : result) {
			ViewField column = (ViewField)baseField.clone();
			column.setAttributeName(p.getFieldName());
			column.setFieldName(p.getFieldName());
			column.setPath(p.getPattern());
			map.put(column.getAttributeName(), column);
//			addAvaiableColumn(column);
		}
//		System.out.println("map size = "+map.entrySet().size());
//		System.out.println("result size = " + avaibleColumns.size());
		for (ViewField vf : map.values()) {
			addAvaiableColumn(vf);
		}
	}

	public static boolean isWildCard(String pattern) {
		Pattern p = Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static void buildFullPath(List<PatternWithName> result, Document doc) throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();

		List<PatternWithName> res = new ArrayList<PatternWithName>();

		for (int j = 0; j < result.size(); j++) {
			PatternWithName pattern = result.get(j);

			if (isWildCard(pattern.getPattern())) {

				result.remove(pattern);

				Pattern p = Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
				Matcher m = p.matcher(pattern.getPattern());
				while (m.find()) {
					String fullMatch = m.group(0);
					String attributeName = m.group(1);
					String value = m.group(2);

					String start = pattern.getPattern().substring(0, pattern.getPattern().indexOf(fullMatch));

					NodeList list = (NodeList) xpath.compile(start).evaluate(doc, XPathConstants.NODESET);
					for (int i = 0; i < list.getLength(); i++) {
						String attributeValue = list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();

						if (checkAttributeValue(value, attributeValue)) {
							String s = value.replaceAll("\\*", "\\\\*");
							String replMatch = fullMatch.replaceAll(s, attributeValue);

							int idxOf = pattern.getPattern().indexOf(fullMatch);
							String nextMatch = pattern.getPattern().substring(0, idxOf) + replMatch
									+ pattern.getPattern().substring(idxOf + fullMatch.length(), pattern.getPattern().length());

							res.add(new PatternWithName((pattern.getFieldName().equals("") ? "" : pattern.getFieldName() + "_") + attributeValue,
									nextMatch));
						}
					}
				}
			}
		}
		if (res.size() > 0) {
			for (PatternWithName p : res) {
				result.add(p);
			}
			buildFullPath(result, doc);
		}
	}

	private static boolean checkAttributeValue(String value, String attributeValue) {
		String pattern = value.replaceAll("\\*", "(.*)");
		Pattern p = Pattern.compile("^" + pattern + "$");
		Matcher m = p.matcher(attributeValue);
		if (m.find()) {
			return true;
		}
		return false;
	}

}
