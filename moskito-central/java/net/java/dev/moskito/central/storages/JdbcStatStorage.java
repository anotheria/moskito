package net.java.dev.moskito.central.storages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.central.StatStorageException;
import net.java.dev.moskito.core.producers.DefaultStatsSnapshot;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;

/**
 * TODO: Make sure the implementation saves all the snapshot data to the database
 * this jdbc implementation has not beed of high importance since xml storage appered
 * that is the reason why this implementation might become outdated
 */
public class JdbcStatStorage implements StatStorage {

	private Connection connection;
	
	public JdbcStatStorage(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public IStatsSnapshot queryLastSnapshotByDate(final Date when, final String host, final String statName,
			final Interval interval) throws StatStorageException {
		
		final IStatsSnapshot[] result = new IStatsSnapshot[1];
		new JdbcDataAlgorithm() {
			@Override
			protected PreparedStatement doOperate() throws SQLException, StatStorageException {
				PreparedStatement prep = connection.prepareStatement("SELECT id, name, date_created, interval, interface FROM snapshots WHERE name=? and date_created<? and interval=? and host=? ORDER BY date_created DESC");
    			prep.clearParameters();
                prep.setString(1, statName);
                prep.setTimestamp(2, new java.sql.Timestamp(when.getTime()));
                prep.setInt(3, interval.getLength());
                prep.setString(4, host);
                ResultSet rs = prep.executeQuery();
                if (!rs.next()) {
                	result[0] = null;
                } else {
                	DefaultStatsSnapshot newSnapshot = new DefaultStatsSnapshot();
                	result[0] = newSnapshot;
                	newSnapshot.setName(statName);
                	newSnapshot.setDateCreated(new Date(rs.getTimestamp(3).getTime()));
                    newSnapshot.setInterfaceName(rs.getString(5));
                    Map<String, Number> properties = readSnapshotProperies(rs.getInt(1));
                	newSnapshot.setProperties(properties);
                }
				return prep;
			}
		}.operate("snapshot " + statName);
		return result[0];
	}

	protected Map<String, Number> readSnapshotProperies(final int snapshotId) throws StatStorageException {
		final Map<String, Number> properties = new HashMap<String, Number>();
		new JdbcDataAlgorithm() {
			@Override
			protected PreparedStatement doOperate() throws SQLException {
				PreparedStatement prep = connection.prepareStatement("SELECT name, value FROM stats WHERE snapshot_id=?");
    			prep.clearParameters();
                prep.setInt(1, snapshotId);
                ResultSet rs = prep.executeQuery();
                while (rs.next()) {
                	String name = rs.getString(1);
                	Number value = rs.getDouble(2);
                	properties.put(name, value);
                }
				return prep;
			}
		}.operate("snapshot properties id=" + snapshotId);
		return properties;
	}

	@Override
	public void store(Collection<IStatsSnapshot> snapshots, final Date when, final String host, final Interval interval)
			throws StatStorageException {
		
		for (final IStatsSnapshot snapshot : snapshots) {
			String snapshotTitle = "snapshot " + snapshot.getName();
			new JdbcDataAlgorithm() {
				@Override
				protected PreparedStatement doOperate() throws SQLException {
					PreparedStatement prep = connection.prepareCall("INSERT INTO snapshots (name, date_created, interval, host, interface) VALUES (?,?,?,?,?)");
	    			prep.clearParameters();
	                prep.setString(1, snapshot.getName());
	                prep.setTimestamp(2, new java.sql.Timestamp(when == null ? snapshot.getDateCreated().getTime() : when.getTime()));
	                prep.setInt(3, interval.getLength());
                    prep.setString(4, host);
                    prep.setString(5, snapshot.getInterfaceName());
                    prep.execute();
					return prep;
				}
			}.operate(snapshotTitle);
			final int lastSnapshotId = getLastSnapshotId();
			Iterator<String> it = snapshot.getProperties().keySet().iterator();
			while (it.hasNext()) {
				final String statName = it.next();
				final Number statValue = snapshot.getProperties().get(statName);
				new JdbcDataAlgorithm() {
					@Override
					protected PreparedStatement doOperate() throws SQLException {
						PreparedStatement prep = connection.prepareCall("INSERT INTO stats (name, value, snapshot_id) VALUES (?,?,?)");
		    			prep.clearParameters();
		                prep.setString(1, statName);
		                prep.setDouble(2, statValue.doubleValue());
		                prep.setInt(3, lastSnapshotId);
		                prep.execute();
						return prep;
					}
				}.operate(snapshotTitle + " stat " + statName);
			}
		}
        try {
			connection.commit();
		} catch (SQLException e) {
			throw new StatStorageException("Cannot commit interval " + interval.getName());
		}
		
	}
	
	protected int getLastSnapshotId() throws StatStorageException {
		Statement stat = null;
		ResultSet result = null;
        try {
    		stat = connection.createStatement();
            result = stat.executeQuery("SELECT max(id) FROM snapshots");
            if (!result.next()) {
            	throw new StatStorageException("Cannot retireve snapshot id of the last inserted snapshot");
            }
			return result.getInt(1);
		} catch (SQLException e) {
			throw new StatStorageException("Cannot retrieve snapshot id of the last inserted snapshot", e); 
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new StatStorageException("Cannot close result set when retrieving last snapshot id", e);
				}
			}
		}
	}

	protected abstract class JdbcDataAlgorithm {
		protected abstract PreparedStatement doOperate() throws SQLException, StatStorageException; 
		protected void operate(String title) throws StatStorageException {
			PreparedStatement prep = null;
            try {
            	prep = doOperate();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new StatStorageException("Failed to rollback saved data due to failed save of " + title, e1);
				}
				throw new StatStorageException("Failed to operate " + title, e);
			} finally {
				if (prep != null) {
					try {
						prep.close();
					} catch (SQLException e) {
						throw new StatStorageException("Failed to close prepared statement for failed save of " + title, e);
					}
				}
			}
		}
	}

}
