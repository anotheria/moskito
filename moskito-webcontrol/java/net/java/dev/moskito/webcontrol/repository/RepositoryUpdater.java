package net.java.dev.moskito.webcontrol.repository;

import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.feed.FeedGetter;
import net.java.dev.moskito.webcontrol.feed.HttpGetter;
import net.java.dev.moskito.webcontrol.ui.beans.PatternWithName;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Singletone class for keeping repository up to date. Monitors all registered apps.
 *
 * @author dsilenko
 */
public class RepositoryUpdater {

	/**
	 * Instance of class.
	 */
	private static RepositoryUpdater instance;
	/**
	 * Class logger.
	 */
	private Logger log = Logger.getLogger(RepositoryUpdater.class);
	/**
	 * Timer for updating.
	 */
	private Timer timer;
	/**
	 * Period of time in milliseconds between successive task executions.
	 */
	private long period;
	/**
	 * Default period for checking.
	 */
	private static final long DEFAULT_PERIOD = 60*1000;

	private RepositoryUpdater(long aPeriod) {
		period = aPeriod;
		try {
			ConfigurationRepository.INSTANCE.loadViewsConfiguration();
			ConfigurationRepository.INSTANCE.loadAllAvailableColumns();
		} catch (Exception e) {
			log.error("RepositoryUpdater() can't instantiate or load moskito repository", e);
			instance=null;
		}
	}

	/**
	 *
 	 * @return class instance.
	 */
	public static RepositoryUpdater getInstance(){
		if (instance == null){
			synchronized (RepositoryUpdater.class){
				if (instance == null)
					instance = new RepositoryUpdater(DEFAULT_PERIOD);
			}
		}

		return instance;
	}

	/**
	 * Starts repository updater with given period, if period of time is null then
	 * default value will be used.
	 * @param aPeriod period of time in milliseconds between successive task executions.
	 */
	public void start(Long aPeriod){
		stop();
		timer = new Timer("MoskitoRepositoryUpdater", true);
		period = (aPeriod==null) ? DEFAULT_PERIOD : aPeriod;
		timer.schedule(new CheckTask(), 1, period);
	}

	/**
	 * Stopping repository updating task.
	 */
	public void stop(){
		if (timer!=null){
			timer.cancel();
			timer.purge();
			timer=null;
		}
	}

	/**
	 * Retrieves xml form each server, parse them and puts data into repository.
	 */
	public void update() {//TODO thread sefity
		List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
		for (SourceConfiguration source : sources) {
			if (!ConfigurationRepository.TOTALS_SOURCE_NAME.equalsIgnoreCase(source.getName())) {
				for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
					try {
						FeedGetter getter = new HttpGetter();
						SourceConfiguration sourceConf = source.build("&pInterval=" + name);
						Document doc = getter.retreive(sourceConf);
						if (doc != null) {
							fillRepository(source, doc, ConfigurationRepository.INSTANCE.getContainerName(name));
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					executeGuards(ConfigurationRepository.INSTANCE.getContainerName(name), source.getName());
				}
			}
		}

		for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {
			calculateTotals(ConfigurationRepository.INSTANCE.getContainerName(name));
		}

	}

	private void executeGuards(String containerName, String sourceConfigName) {
		// log.debug("containerName=" + containerName + ", sourceConfigName=" +
		// sourceConfigName);
		Snapshot ss = null;
		try {
			ss = Repository.INSTANCE.getSnapshot(containerName, new SnapshotSource(sourceConfigName));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (ss == null) {
			return;
		}
		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();
			for (ViewField field : fields) {
				Attribute attr = ss.getAttribute(field.getAttributeName());
				try {
					if (field.getGuard() != null) {
						field.getGuard().execute(ss, field, attr);
					}
//					System.out.println("field="+field+", guard="+field.getGuard());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	private void calculateTotals(String containerName) {
		Snapshot snapshot = new Snapshot(new SnapshotSource(ConfigurationRepository.TOTALS_SOURCE_NAME));

		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();
			for (ViewField field : fields) {
				if (!field.getTotal().equals(TotalFormulaType.EMPTY)) {
					List<Attribute> attrs = new ArrayList<Attribute>();
					List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
					for (SourceConfiguration source : sources) {
						if (!ConfigurationRepository.TOTALS_SOURCE_NAME.equalsIgnoreCase(source.getName())) {
							Snapshot ss;
							try {
								ss = Repository.INSTANCE.getSnapshot(containerName, new SnapshotSource(source.getName()));
								attrs.add(ss.getAttribute(field.getAttributeName()));
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
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

	/**
	 * Adds snapshot to local repository.
	 * @param source name of server from which we should take snapshot(see servers.json).
	 * @param doc remote snapshot.
	 * @param containerName container for snapshot (see intervals.json).
	 */
	public void fillRepository(SourceConfiguration source, Document doc, String containerName) {
		Repository.INSTANCE.addSnapshot(containerName, createSnapshot(source.getName(), doc));
	}

	/**
	 * Creates local snapshot from remote server.
	 * @param name name of server from which we should take snapshot(see servers.json).
	 * @param doc snapshot object to parse.
	 * @return created snapshot.
	 */
	private Snapshot createSnapshot(String name, Document doc) {

		Snapshot snapshot = new Snapshot(new SnapshotSource(name));
		List<String> viewNames = ConfigurationRepository.INSTANCE.getViewNames();
		for (String viewName : viewNames) {
			ViewConfiguration viewConfig = ConfigurationRepository.INSTANCE.getView(viewName);
			List<ViewField> fields = viewConfig.getFields();

			//TODO explain pls this block
			ViewField[] fs = fields.toArray(new ViewField[fields.size()]);
			for (int j = 0; j < fs.length; j++) {
				ViewField field = fs[j];
				switch (field.getType()) {
					case FIELD:
						boolean wildcard = ConfigurationRepository.isWildCard(field.getPath());
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

	private Attribute getAttribute(Snapshot snapshot, List<ViewField> fields, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		XPath xpath = XPathFactory.newInstance().newXPath();
		if (attr == null) {
			for (ViewField field : fields) {
				if (field.getAttributeName().equals(input)) {
					try {
						// log.debug("field.getPath() = " + field.getPath());
						Node value = (Node) xpath.compile(field.getPath()).evaluate(doc, XPathConstants.NODE);
						if (value != null) {
							attr = AttributeFactory
									.create(AttributeType.convert(field.getJavaType()), field.getAttributeName(), value.getNodeValue());
						} else {
							attr = AttributeFactory.create(AttributeType.convert(field.getJavaType()), field.getAttributeName(), "0");
						}
						break;
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return attr;
	}

	private void addFieldByWildCard(ViewConfiguration viewConfig, ViewField field, Snapshot snapshot, String input, Document doc) {
		Attribute attr = snapshot.getAttribute(input);
		if (attr == null) {
			if (field.getAttributeName().equals(input)) {
				try {
					List<PatternWithName> result = new ArrayList<PatternWithName>();
					result.add(new PatternWithName("", field.getPath()));
					ConfigurationRepository.buildFullPath(result, doc);
					for (PatternWithName p : result) {

						ViewField newField = (ViewField) field.clone();
						newField.setFieldName(p.getFieldName() + "." + field.getFieldName());
						newField.setAttributeName(p.getFieldName() + "." + field.getFieldName());
						newField.setPath(p.getPattern());

						viewConfig.addField(newField);
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}


	private class CheckTask extends TimerTask {

		@Override
		public final void run() {
			//System.out.println("check file("+file.getAbsolutePath()+")");
			update();
		}
	}
}
