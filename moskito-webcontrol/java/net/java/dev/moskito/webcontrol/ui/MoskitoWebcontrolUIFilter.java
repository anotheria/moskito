package net.java.dev.moskito.webcontrol.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
			FeedGetter getter = new HttpGetter();
			Document doc = getter.retreive(source);
			if (doc != null) {
				fillRepository(source, doc);
			}
		}
		calculateTotals();
	}

	private static void calculateTotals() {
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
							Snapshot ss = Repository.INSTANCE.getSnapshot(viewConfig.getContainerName(), new SnapshotSource(source.getName()));
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
		Repository.INSTANCE.addSnapshot("TestMem", snapshot);
	}

	private static void fillRepository(SourceConfiguration source, Document doc) {
		Repository.INSTANCE.addSnapshot("TestMem", createSnapshot(source.getName(), doc));
	}

	private static Snapshot createSnapshot(String name, Document doc) {
		Snapshot snapshot = new Snapshot(new SnapshotSource(name));
		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();
			for (ViewField field : fields) {
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

	private static Attribute getAttribute(Snapshot snapshot, List<ViewField> fields, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		if (attr == null) {
			for (ViewField field : fields) {
				if (field.getAttributeName().equals(input)) {
					try {
						XPath xpath = XPathFactory.newInstance().newXPath();
						String value = (String) xpath.compile(field.getPath()).evaluate(doc, XPathConstants.STRING);
						attr = AttributeFactory.create(AttributeType.convert(field.getJavaType()), field.getAttributeName(), value);
						break;
					} catch (Exception e) {
						e.printStackTrace();
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
