package net.anotheria.moskito.extensions.analyze.connector;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.snapshot.ProducerSnapshot;
import net.anotheria.moskito.core.snapshot.SnapshotConsumer;
import net.anotheria.moskito.core.snapshot.SnapshotRepository;
import net.anotheria.moskito.core.snapshot.StatSnapshot;
import net.anotheria.net.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Parent class for all analyze connectors. Describes full logic.
 * 
 * @author esmakula
 * 
 */
public abstract class AbstractAnalyzeConnector extends AbstractMoskitoPlugin implements SnapshotConsumer {

	/**
	 * Name of the component. Default is app.
	 */
	private String componentName = "app";

	/**
	 * Hostname.
	 */
	private String host;

	/**
	 * Logger instance.
	 */
	private final static Logger log = LoggerFactory.getLogger(AbstractAnalyzeConnector.class);

	/**
	 * Default constructor.
	 */
	public AbstractAnalyzeConnector() {
		componentName = System.getProperty(Constants.PROP_COMPONENT, componentName);
		try {
			host = NetUtils.getShortComputerName();
		} catch (Exception ignored) {
		}
		if (host == null)
			host = "unknown";
		host = System.getProperty(Constants.PROP_HOSTNAME, host);
	}

	@Override
	public void initialize() {
		super.initialize();
		SnapshotRepository.getInstance().addConsumer(this);
	}

	@Override
	public void deInitialize() {
		SnapshotRepository.getInstance().removeConsumer(this);
		super.deInitialize();
	}

	@Override
	public void consumeSnapshot(ProducerSnapshot coreSnapshot) {
		Snapshot snapshot = makeSnapshot(coreSnapshot);
		log.debug(this.getClass().getName() + ": \r\n" + snapshot);
		try {
			sendData(snapshot);
		} catch (Exception e) {
			if(log.isDebugEnabled()) {
				log.error(this.getClass().getSimpleName() + ".sendData() failed", e);
			} else {
				log.error(this.getClass().getSimpleName() + ".sendData() failed: " + e.getMessage());
			}
		}
	}

	private Snapshot makeSnapshot(ProducerSnapshot coreSnapshot) {
		Snapshot snapshot = new Snapshot();

		SnapshotMetaData metaData = new SnapshotMetaData();
		metaData.setProducerId(coreSnapshot.getProducerId());
		metaData.setCategory(coreSnapshot.getCategory());
		metaData.setSubsystem(coreSnapshot.getSubsystem());

		metaData.setComponentName(componentName);
		metaData.setHostName(host);
		metaData.setIntervalName(coreSnapshot.getIntervalName());

		metaData.setCreationTimestamp(coreSnapshot.getTimestamp());
		metaData.setStatClassName(coreSnapshot.getStatClassName());
		snapshot.setMetaData(metaData);

		Map<String, StatSnapshot> coreStatSnapshots = coreSnapshot.getStatSnapshots();
		for (Map.Entry<String, StatSnapshot> coreStatSnapshot : coreStatSnapshots.entrySet()) {
			Stat stat = new Stat();
			stat.setStatName(coreStatSnapshot.getKey());
			stat.setValues(new HashMap<>(coreStatSnapshot.getValue().getValues()));
			snapshot.addStat(stat);
		}
		return snapshot;
	}

	/**
	 * Connector specific implementation.
	 * 
	 * @param snapshot snapshot to send
	 */
	protected abstract void sendData(Snapshot snapshot);

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + componentName + "@" + host;
	}

}
