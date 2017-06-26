package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Global Request Processor Stats Test
 *
 * @author esmakula
 */
public class GlobalRequestProcessorStatsTest {

    @Test
    public void testStatsValues() {
        GlobalRequestProcessorStats stats = new GlobalRequestProcessorStats("test");
        for (int i = 0; i < 100; i++) {
            stats.update(i, 0, 0, 0, 0 , i % 2 == 0 ? i : i-1);
        }
        assertEquals(99, stats.getRequestCount(null));
        assertEquals(98, stats.getErrorCount(null));
    }

    @Test
    public void getValueByNameAsStringNullTest() {
        GlobalRequestProcessorStats s = new GlobalRequestProcessorStats("test");
        try {
            s.getValueByNameAsString(null, null, null);
            fail("Expected assertion error");
        } catch (AssertionError e) {
          // skip
        }
    }

    @Test
    public void getValueNamesTest() {
        List<String> names = new GlobalRequestProcessorStats("test").getAvailableValueNames();
        //we don't have to check ALL.
        assertTrue(names.contains("RequestCount"));
        assertTrue(names.contains("MaxTime"));
        assertTrue(names.contains("BytesReceived"));
    }


    @Test
    public void getValueByNameAsStringTest() {
        GlobalRequestProcessorStats s = new GlobalRequestProcessorStats("test");

        //prepare data
        s.update(10, 100, 256, 128, 200 , 1);

        //now check
        assertEquals("10", s.getValueByNameAsString("RequestCount", null, TimeUnit.SECONDS));
        assertEquals("100", s.getValueByNameAsString("MaxTime", null, TimeUnit.SECONDS));
        assertEquals("256", s.getValueByNameAsString("BytesReceived", null, TimeUnit.SECONDS));
        assertEquals("128", s.getValueByNameAsString("BytesSent", null, TimeUnit.SECONDS));
        assertEquals("200", s.getValueByNameAsString("ProcessingTime", null, TimeUnit.SECONDS));
        assertEquals("1", s.getValueByNameAsString("ErrorCount", null, TimeUnit.SECONDS));

    }

    @Test
    public void testToStatsString() {
        GlobalRequestProcessorStats s = new GlobalRequestProcessorStats("test");

        //prepare data
        s.update(10, 100, 256, 128, 200 , 1);

        String stats = s.toStatsString();
        assertTrue(stats.contains("RequestCount: 10"));
        assertTrue(stats.contains("MaxTime: 100"));
        assertTrue(stats.contains("BytesReceived: 256"));
        assertTrue(stats.contains("BytesSent: 128"));
        assertTrue(stats.contains("ProcessingTime: 200"));
        assertTrue(stats.contains("ErrorCount: 1"));

    }
}
