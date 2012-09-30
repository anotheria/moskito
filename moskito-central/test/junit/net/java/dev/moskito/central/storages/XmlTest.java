package net.java.dev.moskito.central.storages;

import net.anotheria.moskito.central.StatStorage;
import net.anotheria.moskito.central.storages.XmlStatStorage;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.producers.DefaultStatsSnapshot;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.DefaultIntervals;
import net.java.dev.moskito.core.stats.Interval;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class XmlTest {

    private static final String[] HOST_NAMES = new String[] {"host01", "host02", "host03"};

    private static final int NUMBER_OF_DAYS = 10;
    private static final int NUMBER_OF_REQUESTS_PER_DAY = 3;
    private static final int REQUEST_DURATION = 1000;
    private static final Interval[] INTERVALS = new Interval[] {
        DefaultIntervals.ONE_DAY
    };

    private static final String XML_STORAGE_ROOT_DIR = "/projects/companies/Anotheria/SVN/tests/xml";

    @Ignore @Test
    public void testLocalXmlStorage() throws Exception {
        File xmlStorageDir = new File(XML_STORAGE_ROOT_DIR);
        assert(xmlStorageDir.mkdirs());
        StatStorage xmlStorage = new XmlStatStorage(xmlStorageDir);

        Date now = new Date();
        for (String hostName : HOST_NAMES) {
            // init stats and interval
            ServiceStats stats = new ServiceStats("test", Constants.getDefaultIntervals());

            // get NUMBER_OF_DAYS ago date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DATE, -NUMBER_OF_DAYS);

            // generate and store snapshots for NUMBER_OF_DAYS previous minutes
            for (int i = 0; i < NUMBER_OF_DAYS; i++) {
                //each loop iteration emulates 1 day
                for (int j = 0; j < NUMBER_OF_REQUESTS_PER_DAY; j++) {
                    stats.addRequest();
                    stats.addExecutionTime(REQUEST_DURATION);
                }

                // at the end of each day stats are stored to the XML storage
                calendar.add(Calendar.DATE, 1);
                Date then = calendar.getTime();
                for (Interval interval : INTERVALS) {
                    DefaultStatsSnapshot snapshot = (DefaultStatsSnapshot) stats.createSnapshot(interval.getName(), "xmltest");
                    snapshot.setDateCreated(then);
                    List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
                    snapshots.add(snapshot);
                    xmlStorage.store(snapshots, then, hostName, interval.getName());
                }
            }
        }

        //todo: finish the test, go query the xml storage to test what was saved
    }
}
