package net.anotheria.moskito.central.connectors.embedded;

import net.anotheria.moskito.central.Central;
import net.anotheria.moskito.central.CentralConstants;
import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.SnapshotMetaData;
import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.snapshot.ProducerSnapshot;
import net.anotheria.moskito.core.snapshot.SnapshotConsumer;
import net.anotheria.moskito.core.snapshot.SnapshotRepository;
import net.anotheria.moskito.core.snapshot.StatSnapshot;
import net.anotheria.net.util.NetUtils;

import java.util.Map;

/**
 * This connector allows to run moskito central embedded in any moskito instance.
 * This is useful for 1 server systems to reduce complexity.
 *
 * @author lrosenberg
 * @since 20.03.13 14:17
 */
public class EmbeddedConnector extends AbstractMoskitoPlugin implements SnapshotConsumer{

	/**
	 * Link to local copy of central.
	 */
	private Central central;

	/**
	 * Name of the component. Default is app.
	 */
	private String componentName = "app";

	/**
	 * Hostname.
	 */
	private String host;

	public EmbeddedConnector(){
		central = Central.getInstance();
		componentName = System.getProperty(CentralConstants.PROP_COMPONENT, componentName);
		try{
			host = NetUtils.getShortComputerName();
		}catch(Exception ignored){
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
	}

	@Override
	public void consumeSnapshot(ProducerSnapshot coreSnapshot) {
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
		for (Map.Entry<String,StatSnapshot> coreStatSnapshot : coreStatSnapshots.entrySet()){
			centralSnapshot.addSnapshotData(coreStatSnapshot.getKey(), coreStatSnapshot.getValue().getValues());
		}


		central.processIncomingSnapshot(centralSnapshot);
	}

	@Override public boolean equals(Object o){
		return o == this;
	}

	@Override public String toString(){
		return getClass().getSimpleName()+" "+componentName+"@"+host;
	}
}


