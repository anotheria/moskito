package net.anotheria.moskito.webcontrol.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.anotheria.moskito.webcontrol.IOUtils;
import net.anotheria.moskito.webcontrol.feed.FeedGetter;
import net.anotheria.moskito.webcontrol.feed.HttpGetter;
import net.anotheria.moskito.webcontrol.guards.Guard;
import net.anotheria.moskito.webcontrol.repository.ColumnType;
import net.anotheria.moskito.webcontrol.repository.TotalFormulaType;
import net.anotheria.moskito.webcontrol.ui.beans.PatternWithName;
import net.anotheria.util.StringUtils;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public enum ConfigurationRepository {

	/**
	 * singleton
	 */
	INSTANCE;

	private ConcurrentMap<String, ViewConfiguration> views;
	private List<StatsSource> sources;
	private List<IntervalConfiguration> intervals;
	
	private List<String> allCategories;

	private List<ViewField> avaibleColumns;
	/**
	 * Source name for snapshot with total values.
	 */
	public static final String TOTALS_SOURCE_NAME = "Totals";
	private static final Logger log = Logger.getLogger(ConfigurationRepository.class);

	private ConfigurationRepository() {
		views = new ConcurrentHashMap<String, ViewConfiguration>();
		sources = new CopyOnWriteArrayList<StatsSource>();
		intervals = new ArrayList<IntervalConfiguration>();
		avaibleColumns = new ArrayList<ViewField>();
		allCategories = new ArrayList<String>();
		ConfigurationManager.INSTANCE.configure(ServersConfig.INSTANCE);
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

	/**
	 *
	 * @param viewName name of view
	 * @return configuration for spesified view name
	 * @throws IllegalArgumentException if view was not found.
	 */
	public ViewConfiguration getView(String viewName) {
		ViewConfiguration ret = views.get(viewName);
		if (ret == null)
			throw new IllegalArgumentException("Unknown view: " + viewName);
		return ret;
	}
	
	public void removeView(ViewConfiguration toRemove) {
		views.remove(toRemove.getName());
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
	public List<StatsSource> getSources() {
		ArrayList<StatsSource> ret = new ArrayList<StatsSource>();
		ret.addAll(sources);
		return ret;
	}

	public void addSource(StatsSource toAdd) {
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
	
	public List<String> getAvailableCategories() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(allCategories);
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
			InputStream intervalsIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("moskitowc-intervals.json");
			String content = IOUtils.getInputStreamAsString(intervalsIS);
			JSONArray intrvls = new JSONObject(content).getJSONArray("intervals");
			for (int i = 0; i < intrvls.length(); i++) {
				JSONObject interval = intrvls.getJSONObject(i);
				addInterval(interval.getString("name"), interval.getString("containerName"));
			}
		}

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("moskitowc-views.json");
		String content = IOUtils.getInputStreamAsString(is);
		JSONObject views = new JSONObject(content);

		ServersConfig serversConfig = ServersConfig.INSTANCE;

		String producerPath = views.getString("baseURL");
		
		for (String host : serversConfig.getHosts()){
			StatsSource source = new StatsSource(
					host, 
					serversConfig.makeURL(host, producerPath), 
					serversConfig.getUsername(), 
					serversConfig.getPassword());
				
			ConfigurationRepository.INSTANCE.addSource(source);
			log.info("Loaded source: " + source);
			
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

		StatsSource source = null;
		List<StatsSource> sourceList = ConfigurationRepository.INSTANCE.getSources();
		if (sourceList.size() > 0) {
			source = sourceList.get(0);
		} else {
			throw new RuntimeException("there is no any server config");
		}
		
		
		FeedGetter getter = new HttpGetter();
//		SourceConfiguration sourceConf = new SourceConfiguration(source.getName(), source.getUrl() + "&pInterval=" + interval);
        StatsSource sourceConf = source.build("&pInterval=" + interval);
		Document doc = getter.retreive(sourceConf);
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("moskitowc-views.json");
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
		System.out.println("map size = "+map.entrySet().size());
		System.out.println("result size = " + avaibleColumns.size());
		for (ViewField vf : map.values()) {
			findAndSetCategory(vf, doc);
			addAvaiableColumn(vf);
		}
	}

	
	private void findAndSetCategory(ViewField field, Document doc) throws XPathExpressionException {
		String patternXPATH = "../../category/text()";
		String p = field.getPath().substring(0, field.getPath().indexOf("text()"))+patternXPATH;
		XPath xpath = XPathFactory.newInstance().newXPath();
		String category = (String)xpath.compile(p).evaluate(doc, XPathConstants.STRING);
		if (!allCategories.contains(category)){
			allCategories.add(category);
		}
		field.setCategory(category);
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
					
					//System.out.println("fullMatch="+fullMatch+", attrName="+attributeName+", value="+value+", start="+start);

					NodeList list = (NodeList) xpath.compile(start).evaluate(doc, XPathConstants.NODESET);
					for (int i = 0; i < list.getLength(); i++) {
						String attributeValue = list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
						//System.out.println("attrValue="+attributeValue);

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
