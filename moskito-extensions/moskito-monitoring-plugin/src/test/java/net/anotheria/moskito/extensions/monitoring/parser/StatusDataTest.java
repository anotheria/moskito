package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.extensions.monitoring.metrics.IGenericMetrics;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for StatusData bean.
 *
 * @author dzhmud
 */
public class StatusDataTest {

    final TestMetrics intMetric = new TestMetrics() {
        public Object parseValue(String value) {
            return Integer.parseInt(value);
        }
        public boolean isCorrectValue(Object value) {
            return value instanceof Integer;
        }
    };
    final TestMetrics stringMetric = new TestMetrics() {
        public Object parseValue(String value) {
            return value;
        }
        public boolean isCorrectValue(Object value) {
            return value instanceof String;
        }
    };

    @Test
    public void testCorrectScenario() {
        StatusData sd = new StatusData();
        assertEquals(null, sd.get(intMetric));

        int value0 = 12, value1 = 42;
        sd.put(intMetric, value0);
        assertEquals(value0, sd.get(intMetric));
        assertEquals(value0, sd.get(intMetric));

        sd.put(intMetric, value1);
        assertEquals(value1, sd.get(intMetric));
    }

    @Test
    public void testNullHandling() {
        StatusData sd = new StatusData();

        try {
            sd.put(null, 42);
            fail("Expected NullPointerException");
        } catch (IllegalArgumentException iae) {}
        try {
            sd.get(null);
            fail("Expected NullPointerException");
        } catch (IllegalArgumentException iae) {}

        assertEquals(null, sd.get(intMetric));

        sd.put(intMetric, 42);
        assertEquals(42, sd.get(intMetric));

        sd.put(intMetric, null);
        assertEquals(null, sd.get(intMetric));
    }

    @Test
    public void testWrongValueType() {
        try {
            new StatusData().put(intMetric, 42L);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testPutAll() {
        Map<TestMetrics,Object> map = new LinkedHashMap<>();
        //this will be put in status
        map.put(intMetric, 42);
        //this will be put in status
        map.put(stringMetric, 54);

        StatusData status = new StatusData();
        try {
            status.putAll(map);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {}

        assertEquals(42, status.get(intMetric));

        map.put(intMetric, 11);
        map.put(stringMetric, "updated");
        status.putAll(map);
        assertEquals(11, status.get(intMetric));
        assertEquals("updated", status.get(stringMetric));

    }

    private static class TestMetrics implements IGenericMetrics {

        public String getValueName() {return null;}

        public String getCaption() {
            return null;
        }

        public String getShortExplanation() {
            return null;
        }

        public String getExplanation() {
            return null;
        }

        public StatValueTypes getType() {
            return null;
        }

        public StatValue createStatValue() {
            return null;
        }

        public boolean isRateMetric() {
            return false;
        }

        public boolean isDoubleValue() {
            return false;
        }

        public boolean isStringValue() {
            return false;
        }

        public boolean isIntegerValue() {
            return false;
        }

        public boolean isLongValue() {
            return false;
        }

        public Object parseValue(String value) {
            return null;
        }

        public boolean isCorrectValue(Object value) {
            return false;
        }

        public String getValueAsString(StatValue statValue, String intervalName) {
            return null;
        }

        public long getValueAsLong(StatValue statValue, String intervalName) {
            return 0;
        }

        public int getValueAsInt(StatValue statValue, String intervalName) {
            return 0;
        }

        public double getValueAsDouble(StatValue statValue, String intervalName) {
            return 0;
        }
    }
}
