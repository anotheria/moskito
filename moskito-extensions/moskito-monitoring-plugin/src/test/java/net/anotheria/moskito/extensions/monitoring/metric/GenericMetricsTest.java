package net.anotheria.moskito.extensions.monitoring.metric;

import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.extensions.monitoring.metrics.GenericMetrics;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for {@link GenericMetrics}
 *
 * @author dzhmud
 */
public class GenericMetricsTest {

    @Test
    public void testAnonymousMetricClass() {
        int count = TestMetrics.getMetrics().size();

        new TestMetrics(StatValueTypes.COUNTER, "test");
        new TestMetrics(StatValueTypes.COUNTER, "test");
        assertEquals(count + 2, TestMetrics.getMetrics().size());

        new TestMetrics(StatValueTypes.COUNTER, "test"){
            @Override public String getCaption() {
                return "anonymous test";
            }
        };
        assertEquals(count + 3, TestMetrics.getMetrics().size());
    }

    @Test
    public void testCaptionChaining() {
        String caption = "caption", shortExpl = "shortExplanation", fullExpl = "fullExpl";
        TestMetrics testMetric = new TestMetrics(StatValueTypes.INT, caption);
        assertEquals(caption, testMetric.getCaption());
        assertEquals(caption, testMetric.getShortExplanation());
        assertEquals(caption, testMetric.getExplanation());

        testMetric = new TestMetrics(StatValueTypes.INT, caption, shortExpl);
        assertEquals(caption, testMetric.getCaption());
        assertEquals(shortExpl, testMetric.getShortExplanation());
        assertEquals(shortExpl, testMetric.getExplanation());

        testMetric = new TestMetrics(StatValueTypes.INT, caption, shortExpl, fullExpl);
        assertEquals(caption, testMetric.getCaption());
        assertEquals(shortExpl, testMetric.getShortExplanation());
        assertEquals(fullExpl, testMetric.getExplanation());
    }

    @Test
    public void testValueTypes() {
        testIntMetric();
        testLongMetric();
        testRateMetric();
        testStringMetric();
        testDoubleMetric();
    }

    private void testIntMetric() {
        TestMetrics intMetric = new TestMetrics(StatValueTypes.INT, "int");
        assertFalse(intMetric.isRateMetric());
        assertFalse(intMetric.isDoubleValue());
        assertTrue(intMetric.isIntegerValue());
        assertFalse(intMetric.isLongValue());
        assertFalse(intMetric.isStringValue());

        assertTrue(intMetric.isCorrectValue(null));
        assertFalse(intMetric.isCorrectValue(42.0d));
        assertTrue(intMetric.isCorrectValue(42));
        assertFalse(intMetric.isCorrectValue(42L));
        assertFalse(intMetric.isCorrectValue("42"));

        assertEquals(42, intMetric.parseValue("42"));
        assertEquals(null, intMetric.parseValue(""));
    }

    private void testLongMetric() {
        TestMetrics longMetric = new TestMetrics(StatValueTypes.LONG, "long");
        assertFalse(longMetric.isRateMetric());
        assertFalse(longMetric.isDoubleValue());
        assertFalse(longMetric.isIntegerValue());
        assertTrue(longMetric.isLongValue());
        assertFalse(longMetric.isStringValue());

        assertTrue(longMetric.isCorrectValue(null));
        assertFalse(longMetric.isCorrectValue(42.0d));
        assertFalse(longMetric.isCorrectValue(42));
        assertTrue(longMetric.isCorrectValue(42L));
        assertFalse(longMetric.isCorrectValue("42"));

        assertEquals(42L, longMetric.parseValue("42"));
        assertEquals(null, longMetric.parseValue(""));
    }
    
    private void testRateMetric() {
        TestMetrics rateMetric = new TestMetrics(StatValueTypes.DIFFLONG, true, "rateMetric");
        assertTrue(rateMetric.isRateMetric());
        assertTrue(rateMetric.isDoubleValue());
        assertFalse(rateMetric.isIntegerValue());
        assertFalse(rateMetric.isLongValue());
        assertFalse(rateMetric.isStringValue());

        assertTrue(rateMetric.isCorrectValue(null));
        assertFalse(rateMetric.isCorrectValue(42.0d));
        assertFalse(rateMetric.isCorrectValue(42));
        assertTrue(rateMetric.isCorrectValue(42L));
        assertFalse(rateMetric.isCorrectValue("42"));

        assertEquals(42L, rateMetric.parseValue("42"));
        assertEquals(null, rateMetric.parseValue(""));
    }

    private void testStringMetric() {
        TestMetrics stringMetric = new TestMetrics(StatValueTypes.STRING, "string");
        assertFalse(stringMetric.isRateMetric());
        assertFalse(stringMetric.isDoubleValue());
        assertFalse(stringMetric.isIntegerValue());
        assertFalse(stringMetric.isLongValue());
        assertTrue(stringMetric.isStringValue());

        assertTrue(stringMetric.isCorrectValue(null));
        assertFalse(stringMetric.isCorrectValue(42.0d));
        assertFalse(stringMetric.isCorrectValue(42));
        assertFalse(stringMetric.isCorrectValue(42L));
        assertTrue(stringMetric.isCorrectValue("42"));

        assertEquals("42", stringMetric.parseValue("42"));
        assertEquals(null, stringMetric.parseValue(null));
    }

    private void testDoubleMetric() {
        TestMetrics doubleMetric = new TestMetrics(StatValueTypes.DOUBLE, "double");
        assertFalse(doubleMetric.isRateMetric());
        assertTrue(doubleMetric.isDoubleValue());
        assertFalse(doubleMetric.isIntegerValue());
        assertFalse(doubleMetric.isLongValue());
        assertFalse(doubleMetric.isStringValue());

        assertTrue(doubleMetric.isCorrectValue(null));
        assertTrue(doubleMetric.isCorrectValue(42.0d));
        assertFalse(doubleMetric.isCorrectValue(42));
        assertFalse(doubleMetric.isCorrectValue(42L));
        assertFalse(doubleMetric.isCorrectValue("42"));

        assertEquals(42d, (Double)doubleMetric.parseValue("42"), 0.01d);
        assertEquals(null, doubleMetric.parseValue(null));
    }

    @Test
    public void testConstructorsAndOtherStuff() {
        try {
            new TestMetrics(null, "test");
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
        try {
            new TestMetrics(StatValueTypes.INT, null);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}

        try {
            new TestMetrics(StatValueTypes.INT, "test", null);
            new TestMetrics(StatValueTypes.INT, false, "test", null);
        } catch (IllegalArgumentException e) {
            fail("unexpected IllegalArgumentException");
        }

        try {
            new TestMetrics(StatValueTypes.INT, "test").createStatValue();
            new TestMetrics(StatValueTypes.DIFFLONG, "test").createStatValue();
        } catch (Throwable e) {
            fail("unexpected Exception!");
        }

        for (StatValueTypes type: StatValueTypes.values()) {
            assertEquals(type, new TestMetrics(type, type.name()).getType());
        }

    }

    static class TestMetrics extends GenericMetrics {

        TestMetrics(StatValueTypes type, String valueName) {
            super(type, valueName);
        }

        TestMetrics(StatValueTypes type, String valueName, String ... strings ) {
            super(type, valueName, strings);
        }

        TestMetrics(StatValueTypes type, boolean isRateMetric, String valueName, String... strings) {
            super(type, isRateMetric, valueName, strings);
        }

        static List<TestMetrics> getMetrics() {
            return getMetricsOfType(TestMetrics.class);
        }

    }

}
