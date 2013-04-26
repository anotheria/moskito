package net.anotheria.moskito.central.storage.psql;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author dagafonov
 * 
 */
@Entity
@Table(name = "httpstats")
@DiscriminatorValue("httpsession")
public class HttpSessionStatisticsEntity extends StatisticsEntity {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 171284121479249005L;

	/**
	 * 
	 */
	private long curSessions;
	/**
	 * 
	 */
	private long minSessions;
	/**
	 * 
	 */
	private long maxSessions;
	/**
	 * 
	 */
	private long newSessions;
	/**
	 * 
	 */
	private long delSessions;
	
	@Override
	public void setStats(Map<String, String> stats) {
		// TODO Auto-generated method stub

	}

	public long getCurSessions() {
		return curSessions;
	}

	public void setCurSessions(long curSessions) {
		this.curSessions = curSessions;
	}

	public long getMinSessions() {
		return minSessions;
	}

	public void setMinSessions(long minSessions) {
		this.minSessions = minSessions;
	}

	public long getMaxSessions() {
		return maxSessions;
	}

	public void setMaxSessions(long maxSessions) {
		this.maxSessions = maxSessions;
	}

	public long getNewSessions() {
		return newSessions;
	}

	public void setNewSessions(long newSessions) {
		this.newSessions = newSessions;
	}

	public long getDelSessions() {
		return delSessions;
	}

	public void setDelSessions(long delSessions) {
		this.delSessions = delSessions;
	}

}
