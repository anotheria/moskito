package net.anotheria.moskito.extensions.monitoring.metric;

import net.anotheria.moskito.extensions.monitoring.metrics.NginxMetrics;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Some tests for NginxMetrics class.
 *
 * @author dzhmud
 */
public class NginxMetricsTest {

    @Test
    public void testGetAllAndFindMetrics() {
        assertTrue(NginxMetrics.getAll().contains(NginxMetrics.HANDLED));
        assertEquals(12, NginxMetrics.getAll().size());

        //NginxMetrics.HANDLED should be added always.
        assertTrue(NginxMetrics.findMetrics(Collections.<String>emptyList()).contains(NginxMetrics.HANDLED));
        assertEquals(1, NginxMetrics.findMetrics(Collections.<String>emptyList()).size());

        assertTrue(NginxMetrics.findMetrics(Collections.singletonList("Handled")).contains(NginxMetrics.HANDLED));
        assertEquals(1, NginxMetrics.findMetrics(Collections.singletonList("Handled")).size());

        assertTrue(NginxMetrics.findMetrics(Collections.singletonList("Requests")).contains(NginxMetrics.HANDLED));
        assertTrue(NginxMetrics.findMetrics(Collections.singletonList("Requests")).contains(NginxMetrics.REQUESTS));
        assertEquals(2, NginxMetrics.findMetrics(Collections.singletonList("Requests")).size());
    }

    @Test
    public void testToStringNotFails() {
        try {
            System.out.println(NginxMetrics.REQUESTSPERSECOND.toString());
        } catch (Throwable e) {
            fail("unexpected Exception: " + e);
        }
    }
    
}
