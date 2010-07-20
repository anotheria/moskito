package net.java.dev.moskito.webcontrol.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.feed.FeedGetter;
import net.java.dev.moskito.webcontrol.feed.HttpGetter;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.AttributeFactory;
import net.java.dev.moskito.webcontrol.repository.AttributeType;
import net.java.dev.moskito.webcontrol.repository.Repository;
import net.java.dev.moskito.webcontrol.repository.Snapshot;
import net.java.dev.moskito.webcontrol.repository.SnapshotSource;
import net.java.dev.moskito.webcontrol.repository.StringAttribute;
import net.java.dev.moskito.webcontrol.repository.TotalFormulaType;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MoskitoWebcontrolUIFilter extends MAFFilter {

	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";

	private static final Timer timer = new Timer("MoskitoMemoryPoolReader", true);

	private static Logger log = Logger.getLogger(MoskitoWebcontrolUIFilter.class);

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.error("initing filter");
		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter != null && pathToImagesParameter.length() > 0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);

		try {

			ConfigurationRepository.INSTANCE.loadViewsConfiguration();

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
			throw new ServletException(e);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error(e);
			throw new ServletException(e);
		}

		// start timer
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}
		}, 0, 1000L * 60);

		// TODO delete later
		// DummyFillRepository.fillRepository();
		// DummyFillViewConfig.fillConfig();

	}

	/**
	 * retrieves xml form each server, parse them and puts data into repository
	 */

	public static void update() {
		List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
		for (SourceConfiguration source : sources) {
			if (!"Totals".equalsIgnoreCase(source.getName())) {
				for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
					try {
						FeedGetter getter = new HttpGetter();
						SourceConfiguration sourceConf = new SourceConfiguration(source.getName(), source.getUrl() + "&pInterval=" + name);
						Document doc = getter.retreive(sourceConf);
						if (doc != null) {
							fillRepository(source, doc, ConfigurationRepository.INSTANCE.getContainerName(name));
						}
					} catch (Exception e) {
						log.debug(e.getMessage(), e);
					}
				}
			}
		}

		for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
			executeGuards(ConfigurationRepository.INSTANCE.getContainerName(name));
		}

		for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
			calculateTotals(ConfigurationRepository.INSTANCE.getContainerName(name));
		}

	}

	private static void executeGuards(String containerName) {

		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();
			for (ViewField field : fields) {
				List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
				for (SourceConfiguration source : sources) {
					Snapshot ss = Repository.INSTANCE.getSnapshot(containerName, new SnapshotSource(source.getName()));
					Attribute attr = ss.getAttribute(field.getAttributeName());
					try {
						field.getGuard().execute(ss, field, attr);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}

	}

	private static void calculateTotals(String containerName) {
		Snapshot snapshot = new Snapshot(new SnapshotSource("Totals"));

		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();
			for (ViewField field : fields) {
				if (!field.getTotal().equals(TotalFormulaType.EMPTY)) {
					List<Attribute> attrs = new ArrayList<Attribute>();
					List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
					for (SourceConfiguration source : sources) {
						if (!"Totals".equalsIgnoreCase(source.getName())) {
							Snapshot ss = Repository.INSTANCE.getSnapshot(containerName, new SnapshotSource(source.getName()));
							attrs.add(ss.getAttribute(field.getAttributeName()));
						}
					}
					Attribute[] ats = attrs.toArray(new Attribute[attrs.size()]);
					try {
						snapshot.addAttribute(AttributeFactory.createFormula(field.getAttributeName(), field.getTotal().getFormulaClass(), ats));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					snapshot.addAttribute(new StringAttribute(field.getAttributeName(), ""));
				}
			}
		}
		SourceConfiguration s = new SourceConfiguration(snapshot.getSource().toString(), "");
		ConfigurationRepository.INSTANCE.addSource(s);
		Repository.INSTANCE.addSnapshot(containerName, snapshot);
	}

	public static void fillRepository(SourceConfiguration source, Document doc, String containerName) {
		Repository.INSTANCE.addSnapshot(containerName, createSnapshot(source.getName(), doc));
	}

	private static Snapshot createSnapshot(String name, Document doc) {
		Snapshot snapshot = new Snapshot(new SnapshotSource(name));
		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();

			ViewField[] fs = fields.toArray(new ViewField[fields.size()]);
			for (int j = 0; j < fs.length; j++) {
				ViewField field = fs[j];
				switch (field.getType()) {
					case FIELD:
						boolean wildcard = isWildCard(field.getPath());
						if (wildcard) {
							fs[j] = null;
							fields.remove(field);
							addFieldByWildCard(viewConfig, field, snapshot, field.getAttributeName(), doc);
						}
						break;
					default:
						break;
				}
			}

			for (int j = 0; j < fields.size(); j++) {
				ViewField field = fields.get(j);
				switch (field.getType()) {
					case FORMULA:
						List<Attribute> attrs = new ArrayList<Attribute>();
						for (String f : field.getInputs()) {
							Attribute attr = getAttribute(snapshot, fields, f, doc);
							attrs.add(attr);
						}
						Attribute[] ats = attrs.toArray(new Attribute[attrs.size()]);
						snapshot.addAttribute(AttributeFactory.createFormula(field.getAttributeName(), field.getJavaType(), ats));
						break;
					case FIELD:
						snapshot.addAttribute(getAttribute(snapshot, fields, field.getAttributeName(), doc));
						break;
				}
			}
		}
		return snapshot;
	}

	private static void addFieldByWildCard(ViewConfiguration viewConfig, ViewField field, Snapshot snapshot, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		if (attr == null) {
			if (field.getAttributeName().equals(input)) {
				try {
					List<PatternWithName> result = new ArrayList<PatternWithName>();
					result.add(new PatternWithName("Column", field.getPath()));
					buildFullPath(result, doc);
					for (PatternWithName p : result) {
						
						ViewField newField = new ViewField(p.getFieldName() + "." + field.getFieldName(), p.getFieldName() + "." + field.getFieldName(), field.getType(), field
								.getJavaType(), field.getVisible(), p.getPattern());
						newField.setGuard(field.getGuard());
						newField.setTotal(field.getTotal());
						viewConfig.addField(newField);
					}
					
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	private static Attribute getAttribute(Snapshot snapshot, List<ViewField> fields, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		XPath xpath = XPathFactory.newInstance().newXPath();
		if (attr == null) {
			for (ViewField field : fields) {
				if (field.getAttributeName().equals(input)) {
					try {
						String value = (String) xpath.compile(field.getPath()).evaluate(doc, XPathConstants.STRING);
						attr = AttributeFactory.create(AttributeType.convert(field.getJavaType()), field.getAttributeName(), value);
						break;
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return attr;
	}

	private static class PatternWithName {
		private String pattern;
		private String fieldName;

		public PatternWithName(String name, String pattern) {
			this.fieldName = name;
			this.pattern = pattern;
		}

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}

	private static void buildFullPath(List<PatternWithName> result, Document doc) throws Exception {
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

							res.add(new PatternWithName(pattern.getFieldName() + "_[" + attributeValue + "]", nextMatch));
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

	private static boolean isWildCard(String pattern) {
		Pattern p = Pattern.compile("\\[@([^=]+)=\'([^*\\]]*\\*[^\\]]*)\'\\]+");
		Matcher m = p.matcher(pattern);
		if (m.find()) {
			return true;
		}
		return false;
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

	@Override
	public String getProducerId() {
		return "moskitoWC";
	}

	@Override
	public String getSubsystem() {
		return "monitoring";
	}

	@Override
	public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators() {
		return Arrays.asList(new ActionMappingsConfigurator[] { new MoskitoWebcontrolMappingsConfigurator() });
	}

}
