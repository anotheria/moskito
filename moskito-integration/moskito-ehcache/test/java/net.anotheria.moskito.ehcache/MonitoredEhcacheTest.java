package net.anotheria.moskito.ehcache;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Statistics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link MonitoredEhcache}.
 *
 * @author Vladyslav Bezuhlyi
 */
public class MonitoredEhcacheTest {

    @BeforeClass
    public static void setup(){
        /* to disable builtin producers */
        System.setProperty("JUNITTEST", Boolean.TRUE.toString());
    }

    @Test
    public void test() throws OnDemandStatsProducerException, InterruptedException {
        Ehcache cache = CacheManager.getInstance().getCache("test-cache");
        cache = new MonitoredEhcache(cache, Statistics.STATISTICS_ACCURACY_GUARANTEED, 2*1000L);
        assertNotNull(cache);

        /* retrieving producer that have been registered by monitoring proxy */
        IProducerRegistryAPI registryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
        OnDemandStatsProducer<EhcacheStats> producer = (OnDemandStatsProducer<EhcacheStats>)registryAPI.getProducer(cache.getName());
        assertNotNull(producer);

        /* adding 100 elements into cache */
        List<Element> elements = new LinkedList<Element>();
        for(int i = 1; i <= 100; i++) {
            elements.add(new Element("element"+i, ""+i));
        }

        /* let's hit the cache for 150 times */
        cache.putAll(elements);
        for(int i = 1; i <= 150; i++) {
            cache.get("element" + (i%100 == 0 ? 100 : i%100));
        }

        Thread.sleep(2000L); // give some time to update producer's stats
        EhcacheStats stats = producer.getStats(cache.getName());
        assertEquals(100, stats.getElements().getValueAsLong());
        assertEquals(150, stats.getHits().getValueAsLong());

        /* removing 50 elements from cache */
        for(int i = 1; i <= 100; i++) {
            if (i%2 == 0) {
                cache.remove("element" + i);
            }
        }

        Thread.sleep(2000L); // need some time again
        assertEquals(50, stats.getElements().getValueAsLong());
        assertEquals(cache.getStatistics().getObjectCount(), stats.getElements().getValueAsLong());
    }

}
