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
import javax.xml.xpath.XPathExpressionException;
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

		// for (SourceConfiguration source : sources) {
		for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
			calculateTotals(ConfigurationRepository.INSTANCE.getContainerName(name));
		}
		// }

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

	private static void fillRepository(SourceConfiguration source, Document doc, String containerName) {
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
						boolean wildcard = isPathWildCard(snapshot, fields, field.getAttributeName());
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
		XPath xpath = XPathFactory.newInstance().newXPath();
		if (attr == null) {
			if (field.getAttributeName().equals(input)) {
				String pattern = field.getPath();
				String start = "";
				String end = "";
				String attributeName = "";
				Pattern p = Pattern.compile("^(.*)(\\[@([^=]+)=\\*\\])+(.*)$");
				Matcher m = p.matcher(pattern);
				if (m.find()) {
					start = m.group(1);
					end = m.group(4);
					attributeName = m.group(3);
					try {
						NodeList list = (NodeList) xpath.compile(start).evaluate(doc, XPathConstants.NODESET);
						for (int i = 0; i < list.getLength(); i++) {
							String attrName = list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
							String fullPattern = pattern.replaceAll("@" + attributeName + "=\\*", "@" + attributeName + "=" + "'" + attrName + "'");
							
							String attribName = field.getAttributeName() + "." + attrName;

							ViewField newField = new ViewField(field.getFieldName() + "." + attrName, attribName, field.getType(), field.getJavaType(), field.getVisible(), fullPattern);
							newField.setTotal(field.getTotal());
							
							viewConfig.addField(newField);
						}
					} catch (XPathExpressionException e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
	}

//	private static void addAttributedByWildCard(ViewConfiguration viewConfig, Snapshot snapshot, List<ViewField> fields, String input, Document doc) {
//		Attribute attr = snapshot.getAttribute(input);
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		if (attr == null) {
//			for (int j = 0; j < fields.size(); j++) {
//				ViewField field = fields.get(j);
//				if (field.getAttributeName().equals(input)) {
//					String pattern = field.getPath();
//					String start = "";
//					String end = "";
//					String attributeName = "";
//					Pattern p = Pattern.compile("^(.*)(\\[@([^=]+)=\\*\\])+(.*)$");
//					Matcher m = p.matcher(pattern);
//					if (m.find()) {
//						start = m.group(1);
//						end = m.group(4);
//						attributeName = m.group(3);
//						try {
//							NodeList list = (NodeList) xpath.compile(start).evaluate(doc, XPathConstants.NODESET);
//							for (int i = 0; i < list.getLength(); i++) {
//								String attrName = list.item(i).getAttributes().getNamedItem(attributeName).getNodeValue();
//								String fullPattern = pattern.replaceAll("@" + attributeName + "=\\*", "@" + attributeName + "=" + "'" + attrName
//										+ "'");
//								String value = (String) xpath.compile(fullPattern).evaluate(doc, XPathConstants.STRING);
//								String attribName = field.getAttributeName() + "." + attrName;
//								
//								snapshot.addAttribute(AttributeFactory.create(AttributeType.convert(field.getJavaType()), attribName, value));
//							}
//						} catch (XPathExpressionException e) {
//							log.error(e.getMessage(), e);
//						}
//					}
//				}
//			}
//		}
//	}

	private static boolean isPathWildCard(Snapshot snapshot, List<ViewField> fields, String input) {
		Attribute attr = snapshot.getAttribute(input);
		if (attr == null) {
			for (ViewField field : fields) {
				if (field.getAttributeName().equals(input)) {
					String pattern = field.getPath();
					Pattern p = Pattern.compile("^(.*)(\\[@([^=]+)=\\*\\])+(.*)$");
					Matcher m = p.matcher(pattern);
					if (m.find()) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	private static Attribute getAttribute(Snapshot snapshot, List<ViewField> fields, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		XPath xpath = XPathFactory.newInstance().newXPath();
		if (attr == null) {
			for (ViewField field : fields) {
				if (field.getAttributeName().equals(input)) {
					try {
						String value = (String) xpath.compile(field.getPath()).evaluate(doc, XPathConstants.STRING);
//						System.out.println(field.getJavaType());
//						System.out.println(field.getAttributeName());
//						System.out.println(value);
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
