package net.anotheria.moskito.extensions.analyze.connector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single snapshot.
 *
 * @author esmakula
 */
public class Snapshot implements Serializable {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -3803823780824976489L;

	/**
	 * The metadata. The metadata contains data about the snapshot like
	 * producerId or timestamp.
	 */
	private SnapshotMetaData metaData;

	/**
	 * Stats.
	 */
	private List<Stat> stats = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Snapshot() {

	}

	public SnapshotMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(SnapshotMetaData metaData) {
		this.metaData = metaData;
	}

	public void addStat(Stat stat) {
		stats.add(stat);
	}

	/**
	 * Gets all statistics.
	 *
	 * @return list of {@link Stat}
	 */
	public List<Stat> getStats() {
		if (stats == null) {
			stats = new ArrayList<>();
		}
		return stats;
	}

	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}

	@Override
	public String toString() {
		return "Snapshot [metaData=" + metaData + ", stats=" + stats + "]";
	}

}
