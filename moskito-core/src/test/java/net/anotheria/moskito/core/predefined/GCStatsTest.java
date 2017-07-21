package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Garbage collector Stats Test
 *
 * @author esmakula
 */
public class GCStatsTest {

    @Test
    public void testStatsValues() {
        GCStats stats = new GCStats("test");
        for (int i = 0; i < 10; i++) {
            stats.update(i, i * 10);
        }
        assertEquals(9, stats.getCollectionCount(null));
        assertEquals(90, stats.getCollectionTime(null));
    }

    @Test
    public void getValueByNameAsStringNullTest() {
        GCStats s = new GCStats("test");
        try {
            s.getValueByNameAsString(null, null, null);
            fail("Expected assertion error");
        } catch (AssertionError e) {
          // skip
        }
    }

    @Test
    public void getValueNamesTest() {
        List<String> names = new GCStats("test").getAvailableValueNames();
        assertTrue(names.contains("CollectionCount"));
        assertTrue(names.contains("CollectionTime"));
    }


    @Test
    public void getValueByNameAsStringTest() {
        GCStats s = new GCStats("test");

        //prepare data
        s.update(10, 100);

        //now check
        assertEquals("10", s.getValueByNameAsString("CollectionCount", null, TimeUnit.SECONDS));
        assertEquals("100", s.getValueByNameAsString("CollectionTime", null, TimeUnit.SECONDS));

    }

    @Test
    public void testToStatsString() {
        GCStats s = new GCStats("test");

        //prepare data
        s.update(10, 100);

        String stats = s.toStatsString();
        assertTrue(stats.contains("CollectionCount: 10"));
        assertTrue(stats.contains("CollectionTime: 100"));

    }
}
