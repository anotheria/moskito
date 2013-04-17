package net.anotheria.moskito.central.connectors;

import java.util.HashMap;
import java.util.Map;

import net.anotheria.moskito.central.CentralConstants;
import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.SnapshotMetaData;
import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.snapshot.ProducerSnapshot;
import net.anotheria.moskito.core.snapshot.SnapshotConsumer;
import net.anotheria.moskito.core.snapshot.SnapshotRepository;
import net.anotheria.moskito.core.snapshot.StatSnapshot;
import net.anotheria.net.util.NetUtils;

import org.apache.log4j.Logger;

/**
 * Parent class for all central connectors. Describes full logic.
 * 
 * @author dagafonov
 * 
 */
public abstract class AbstractCentralConnector extends AbstractMoskitoPlugin implements SnapshotConsumer {

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
	private final static Logger log = Logger.getLogger(AbstractCentralConnector.class);

	/**
	 * Default constructor.
	 */
	public AbstractCentralConnector() {
		componentName = System.getProperty(CentralConstants.PROP_COMPONENT, componentName);
		try {
			host = NetUtils.getShortComputerName();
		} catch (Exception ignored) {
		}
		if (host == null)
			host = "unknown";
		host = System.getProperty(CentralConstants.PROP_HOSTNAME, host);
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

		Snapshot centralSnapshot = makeSnapshot(coreSnapshot);

		sendData(centralSnapshot);

		log.debug(this.getClass().getName() + ": " + coreSnapshot + "\r\n" + centralSnapshot);

	}

	private Snapshot makeSnapshot(ProducerSnapshot coreSnapshot) {
		Snapshot centralSnapshot = new Snapshot();

		SnapshotMetaData metaData = new SnapshotMetaData();
		metaData.setProducerId(coreSnapshot.getProducerId());
		metaData.setCategory(coreSnapshot.getCategory());
		metaData.setSubsystem(coreSnapshot.getSubsystem());

		metaData.setComponentName(componentName);
		metaData.setHostName(host);
		metaData.setIntervalName(coreSnapshot.getIntervalName());

		metaData.setCreationTimestamp(coreSnapshot.getTimestamp());
		centralSnapshot.setMetaData(metaData);

		Map<String, StatSnapshot> coreStatSnapshots = coreSnapshot.getStatSnapshots();
		for (Map.Entry<String, StatSnapshot> coreStatSnapshot : coreStatSnapshots.entrySet()) {
			centralSnapshot.addSnapshotData(coreStatSnapshot.getKey(), new HashMap<String, String>(coreStatSnapshot.getValue().getValues()));
		}
		return centralSnapshot;
	}

	/**
	 * Connector specific implementation.
	 * 
	 * @param snapshot
	 */
	protected abstract void sendData(Snapshot snapshot);

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + componentName + "@" + host;
	}

}
