package net.java.dev.moskito.central;

import java.util.Collection;
import java.util.Date;

import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;

/**
 * This interface represents local storage for Miskito stats.
 * This is implemented to store Miskito stats to RDBMS, text file, 
 * whatever locally accessible
 * Remote storage is represented
 * 
 * @author igor
 *
 */
public interface StatStorage {
	
	/**
	 * This group of constants describes stats retrieval procedure from the storage
	 */
	static final int SIMPLE_STAT = 0;
	static final int SUM_STAT    = 1;
	static final int AVG_STAT    = 2;
	static final int MAX_STAT    = 3;
	static final int MIN_STAT    = 4;
	
	/**
	 * Stores list of stat snapshots for given interval. 
	 * The date of the snapshot creation is assumed to part of the snapshot.
	 * @param snapshots list of stat snapshots 
	 * @param interval time interval which is used for stats calculation
	 * 
	 * @throws exception in case the storage cannot store the snapshots for some reason
	 */
	void store(Collection<IStatsSnapshot> snapshots, Interval interval) throws StatStorageException;
	
	/**
	 * Retrieves the most recent snapshot recorded right before the given date
	 * per given interval
	 * 
	 * @param when
	 * @param statName
	 * @param intervalLength
	 * @return snapshot instance or null if none was recorded that matches the criteria
	 * @throws exception in case the storage cannot load or calculate the snapshots for some reason
	 */
	IStatsSnapshot queryLastSnapshotByDate(Date when, String statName, int intervalLength) throws StatStorageException;
	
	/**
	 * FIXME: use more flexible version, pass parameters like Properties and return either StatValue 
	 * or smth else haven't decieded what exactly yet 
	 * Loads stat snapshot for given stat and on given date recorded by a given interval.
	 * Example:
	 * in case statCalculationType == MAX_STAT, intervalLength == 24*60 minutes,
	 * stat name "number of request per index page"
	 * then from 1d interval recorded stats a snapshot will be restored
	 * 
	 * @param when the date used in stats snapshot retrieval
	 * @param statName stat name for the stat
	 * @param intervalLength interval used for recording that stat. 
	 * If interval length param is set to -1 then it is not considered in the query
	 * @param statCalculationType defines calculation procedure for the stat.
	 * Could be one of SIMPLE_STAT, ... constants.
	 * 
	 * @return
	 * 
	 * @throws exception in case the storage cannot load or calculate the snapshots for some reason
	 */
	//IStatsSnapshot queryStatSnapshotByDate(Date when, String statName, String statPropertyName, int intervalLength, int statCalculationType) throws StatStorageException;
}
