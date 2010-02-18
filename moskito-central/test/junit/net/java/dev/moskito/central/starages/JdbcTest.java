package net.java.dev.moskito.central.starages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.central.storages.JdbcStatStorage;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.producers.DefaultStatsSnapshot;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.DefaultIntervals;
import net.java.dev.moskito.core.stats.Interval;

import org.junit.Test;
import static junit.framework.Assert.*;

public class JdbcTest {

	private static final int NUMBER_OF_MINUTES = 10;
	private static final int NUMBER_OF_REQUESTS_PER_MINUTES = 3;
	private static final int REQUEST_DURATION = 1000;
	private static final Interval[] INTERVALS = new Interval[] {
		DefaultIntervals.ONE_MINUTE,
		DefaultIntervals.FIVE_MINUTES
	};
	

	private Connection conn;
	
	@Test
	public void testLocalJdbcStorage() throws Exception {
		
		// JDBC storage test code
		// initialize HSQLDB in memory
		// initialize stats
		// simulate stats gathering and store stats at the end of each interval
		// then read data from DB with SQL or with JDBC storage methods
		// and test assumptions
		
		// get hsqldb connection for db in memory
		Class.forName("org.hsqldb.jdbcDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:mem:central");
        
        // create tables
        update("CREATE TABLE snapshots ( id INTEGER IDENTITY, name VARCHAR(256), date_created DATE NOT NULL, interval INTEGER)");
        update("CREATE TABLE stats ( id INTEGER IDENTITY, name VARCHAR(256), value DOUBLE, snapshot_id INTEGER, FOREIGN KEY (snapshot_id) REFERENCES snapshots)");
        
        // init stats and interval
        ServiceStats stats = new ServiceStats("test", Constants.DEFAULT_INTERVALS);
        StatStorage jdbcStorage = new JdbcStatStorage(conn);
        Date now = new Date();
        
        // get NUMBER_OF_MINUTES minutes ago date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, -NUMBER_OF_MINUTES);

        // generate and store snapshots for NUMBER_OF_MINUTES previous minutes
        for (int i = 0; i < NUMBER_OF_MINUTES; i++) {
        	//each loop iteration emulates 1 minute
        	for (int j = 0; j < NUMBER_OF_REQUESTS_PER_MINUTES; j++) {
        		stats.addRequest();
        		stats.addExecutionTime(REQUEST_DURATION);
        	}
        	
        	// at the end of each minute stats are stored to the in memory database
    		calendar.add(Calendar.MINUTE, 1);
    		Date then = calendar.getTime();
        	for (Interval interval : INTERVALS) {
        		//test if interval is ended on current minute
        		if (((i + 1) * 60) % interval.getLength() == 0) {
        			DefaultStatsSnapshot snapshot = (DefaultStatsSnapshot) stats.createSnapshot(interval.getName());
        			snapshot.setDateCreated(then);
        			List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
        			snapshots.add(snapshot);
        			jdbcStorage.store(snapshots, interval);
        		}
        	}
        }
        
        // now the data are stored for NUMBER_OF_MINUTES
        // the following tests are performed then
        // 1) total number of requests saved in DB should be NUMBER_OF_REQUESTS_PER_MINUTES * NUMBER_OF_MINUTES
        //   This is tested in 2 ways:
        //  a) a simple SQL query that counts that
        //  b) a call to queryLastSnapshotByDate storage method to get the latest snapshot after which necessary assertion is made.

        // handle 1.a
        PreparedStatement pstmt = conn.prepareStatement("SELECT value FROM stats WHERE snapshot_id=? and name=?");
        pstmt.clearParameters();
        pstmt.setInt(1, findInt("SELECT max(id) FROM snapshots"));
        pstmt.setString(2, "TotalRequests");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int readTotalRequests = rs.getInt(1);
        assertEquals(readTotalRequests, stats.getTotalRequests());
        
        // handle 1.b using stored 1 minute intervals
        IStatsSnapshot snapshot = jdbcStorage.queryLastSnapshotByDate(new Date(), "test", DefaultIntervals.ONE_MINUTE.getLength());
        assertEquals(snapshot.getProperties().get("TotalRequests"), stats.getTotalRequests());
        
        // shutdown db
        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();
	}
	
	private int findInt(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		return rs.getInt(1);
	}
	
	private void update(String expression) throws SQLException {

        Statement st = null;
        st = conn.createStatement();    // statements
        try {
            int i = st.executeUpdate(expression);    // run the query
            assertEquals(i == -1, false);
        } finally {
        	st.close();
        }
    }
}
