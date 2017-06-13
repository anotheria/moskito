package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.extensions.monitoring.metrics.NginxMetrics;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Generic NginxStubStatusParser test.
 * Tests that values are parsed and assigned to correct fields in NginxStatus object.
 *
 * @author dzhmud
 */
public class NginxStubStatusParserTest {

    private final String[] placeholders = {"ACTIVE", "ACCEPTS",
            "HANDLED", "REQUESTS", "READING", "WRITING", "WAITING"};

    private final String STATUS_TEMPLATE = "Active connections: ACTIVE \n" +
            "server accepts handled requests\n" +
            " ACCEPTS HANDLED REQUESTS \n" +
            "Reading: READING Writing: WRITING Waiting: WAITING ";

    private String getStatus(long[] values) {
        assert values.length == placeholders.length;
        String status = STATUS_TEMPLATE;
        for (int i = 0; i < values.length; i++) {
            status = status.replace(placeholders[i], ""+values[i]);
        }
        return status;
    }

    @Test
    public void test() {
        long[] values = {1, 45, 45, 67, 0, 1, 0};
        StatusData<NginxMetrics> status = NginxStubStatusParser.INSTANCE.parse(getStatus(values));
        assertNotNull(status);

        assertEquals(values[0], status.get(NginxMetrics.ACTIVE));
        assertEquals(values[1], status.get(NginxMetrics.ACCEPTED));
        assertEquals(values[2], status.get(NginxMetrics.HANDLED));
        assertEquals(values[3], status.get(NginxMetrics.REQUESTS));
        assertEquals(values[4], status.get(NginxMetrics.READING));
        assertEquals(values[5], status.get(NginxMetrics.WRITING));
        assertEquals(values[6], status.get(NginxMetrics.WAITING));
    }

    @Test
    public void testNegativeCases() {
        List<String> statuses = new ArrayList<>();
        statuses.add(null);
        statuses.add("");
        statuses.add("fgdsg");
        statuses.add(getStatus(new long[]{1, 45, 1000, 67, 0, 1, 0}).replace("1000", "---"));
        statuses.add(getStatus(new long[]{1, 45, -1000, 67, 0, 1, 0}));

        for (String statusString : statuses) {
            try {
                NginxStubStatusParser.INSTANCE.parse(statusString);
                fail("Expected that parser will throw exception");
            } catch (IllegalArgumentException e) {
                //expected
            }
        }
    }

}
