package net.anotheria.moskito.extensions.monitoring.metric;

import net.anotheria.moskito.extensions.monitoring.metrics.ApacheMetrics;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Some tests for ApacheMetrics class.
 *
 * @author dzhmud
 */
public class ApacheMetricsTest {

    @Test
    public void testGetAllAndFindMetrics() {
        assertTrue(ApacheMetrics.getAll().contains(ApacheMetrics.SERVER_UPTIME));
        assertEquals(33, ApacheMetrics.getAll().size());

        assertTrue(ApacheMetrics.findMetrics(Collections.<String>emptyList()).contains(ApacheMetrics.SERVER_UPTIME));
        assertEquals(1, ApacheMetrics.findMetrics(Collections.<String>emptyList()).size());

        assertTrue(ApacheMetrics.findMetrics(Collections.singletonList("Uptime(sec)")).contains(ApacheMetrics.SERVER_UPTIME));
        assertEquals(1, ApacheMetrics.findMetrics(Collections.singletonList("Uptime(sec)")).size());

        assertTrue(ApacheMetrics.findMetrics(Collections.singletonList("Hostname")).contains(ApacheMetrics.SERVER_UPTIME));
        assertTrue(ApacheMetrics.findMetrics(Collections.singletonList("Hostname")).contains(ApacheMetrics.HOSTNAME));
        assertEquals(2, ApacheMetrics.findMetrics(Collections.singletonList("Hostname")).size());
    }

    @Test
    public void testToStringNotFails() {
        try {
            System.out.println(ApacheMetrics.CONNECTIONS_ASYNC_KEEPALIVE.toString());
        } catch (Throwable e) {
            fail("unexpected Exception: " + e);
        }
    }

}
