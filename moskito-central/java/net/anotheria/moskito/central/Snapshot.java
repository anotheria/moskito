package net.anotheria.moskito.central;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.03.13 23:15
 */
public class Snapshot implements Serializable {
	private SnapshotMetaData metaData;

	public SnapshotMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(SnapshotMetaData metaData) {
		this.metaData = metaData;
	}
}
