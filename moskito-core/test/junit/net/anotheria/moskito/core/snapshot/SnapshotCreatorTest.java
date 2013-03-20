package net.anotheria.moskito.core.snapshot;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 30.09.12 15:30
 */
public class SnapshotCreatorTest extends BaseSnapshotTest{
	@Test public void testCreateSnapshot() throws OnDemandStatsProducerException {
		OnDemandStatsProducer<ServiceStats> producer = setupProducer();
		String intervalName = "5m";

		//force interval update
		forceIntervalUpdate(intervalName);
		//add first stat.
		ServiceStats stat1 =  producer.getStats("case1");
		for (int i=0; i<10; i++){
			stat1.addRequest();
			stat1.addExecutionTime(100); //100 milliseconds
		}

		ServiceStats stat2 =  producer.getStats("case2");
		stat2.addExecutionTime(500);
		stat2.addRequest();
		//force interval update
		forceIntervalUpdate(intervalName);

		ProducerSnapshot snapshot = SnapshotCreator.createSnapshot(producer, intervalName);
		System.out.println("Snapshot: "+snapshot);

		assertEquals(3, snapshot.getStatSnapshots().size());

		StatSnapshot first = snapshot.getStatSnapshot("case1");
		assertEquals("10", first.getValue("TR"));
		assertEquals("1000", first.getValue("TT"));
		assertEquals("100.0", first.getValue("Avg"));
		assertEquals("100", first.getValue("Min"));
		assertEquals("100", first.getValue("Max"));
		assertEquals("100", first.getValue("Last"));
		assertEquals("0", first.getValue("ERR"));

		StatSnapshot second = snapshot.getStatSnapshot("case2");
		assertEquals("1", second.getValue("TR"));
		assertEquals("500", second.getValue("TT"));
		assertEquals("500.0", second.getValue("Avg"));
		assertEquals("500", second.getValue("Min"));
		assertEquals("500", second.getValue("Max"));
		assertEquals("500", second.getValue("Last"));
		assertEquals("0", second.getValue("ERR"));

	}

	@Test public void testDeclarativeProperties(){
		OnDemandStatsProducer<ServiceStats> p = new OnDemandStatsProducer<ServiceStats>("testProducerId", "aCategory", "aSubsystem", new ServiceStatsFactory());
		ProducerSnapshot snapshot = SnapshotCreator.createSnapshot(p, null);
		assertEquals("aCategory", snapshot.getCategory());
		assertEquals("aSubsystem", snapshot.getSubsystem());
		assertEquals("testProducerId", snapshot.getProducerId());
	}
}
