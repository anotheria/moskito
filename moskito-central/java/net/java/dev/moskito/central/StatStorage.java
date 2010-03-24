package net.java.dev.moskito.central;

import java.util.Collection;
import java.util.Date;

import net.java.dev.moskito.core.producers.IStatsSnapshot;


import net.anotheria.anoprise.metafactory.Service;

import org.distributeme.annotation.DistributeMe;

/**
 * This interface represents local storage for Miskito stats.
 * This is implemented to store Miskito stats to RDBMS, text file, 
 * whatever locally accessible
 * Remote storage is represented with SynchroneousConnector interface
 * 
 * @author igor
 *
 */
@DistributeMe(
		initcode={"MetaFactory.addFactoryClass(net.java.dev.moskito.central.StatStorage.class, Extension.LOCAL, net.java.dev.moskito.central.storages.RemoteStorageFactory.class);"}
)
public interface StatStorage extends Service {
	
	/**
	 * Stores list of stat snapshots for given interval. 
	 * The date of the snapshot creation is passed as a parameter.
	 * @param snapshots list of stat snapshots
     * @param when stapshots created
     * @param host that generates the stats
	 * @param interval name which is used for stats calculation
	 * 
	 * @throws StatStorageException in case the storage cannot store the snapshots for some reason
	 */
	void store(Collection<IStatsSnapshot> snapshots, Date when, String host, String interval) throws StatStorageException;
	
	/**
	 * Retrieves the most recent snapshot recorded right before the given date
	 * per given interval
	 * 
	 * @param when
     * @param host that generates the stats
	 * @param statName
	 * @param interval
	 * @return snapshot instance or null if none was recorded that matches the criteria
	 * @throws StatStorageException in case the storage cannot load or calculate the snapshots for some reason
	 */
	IStatsSnapshot queryLastSnapshotByDate(Date when, String host, String statName, String interval) throws StatStorageException;
	
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
