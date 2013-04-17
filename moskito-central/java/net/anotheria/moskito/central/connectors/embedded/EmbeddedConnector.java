package net.anotheria.moskito.central.connectors.embedded;

import net.anotheria.moskito.central.Central;
import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.connectors.AbstractCentralConnector;

/**
 * This connector allows to run moskito central embedded in any moskito
 * instance. This is useful for 1 server systems to reduce complexity.
 * 
 * @author lrosenberg
 * @author dagafonov
 * @since 20.03.13 14:17
 */
public class EmbeddedConnector extends AbstractCentralConnector {

	/**
	 * Link to local copy of central.
	 */
	private Central central;

	/**
	 * Default constructor.
	 */
	public EmbeddedConnector() {
		super();
		central = Central.getInstance();
	}

	@Override
	protected void sendData(Snapshot snapshot) {
		central.processIncomingSnapshot(snapshot);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
}
