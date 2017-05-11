package net.anotheria.moskito.extension.nginx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

    private String getStatus(int[] values) {
        assert values.length == placeholders.length;
        String status = STATUS_TEMPLATE;
        for (int i = 0; i < values.length; i++) {
            status = status.replace(placeholders[i], ""+values[i]);
        }
        return status;
    }

    @Test
    public void test() {
        int[] values = {1, 45, 45, 67, 0, 1, 0};
        try {
            NginxStatus status = NginxStubStatusParser.parseStatus(getStatus(values));
            assertNotNull(status);

            assertEquals(values[0], status.getActive());
            assertEquals(values[1], status.getAccepted());
            assertEquals(values[2], status.getHandled());
            assertEquals(values[3], status.getRequests());
            assertEquals(values[4], status.getReading());
            assertEquals(values[5], status.getWriting());
            assertEquals(values[6], status.getWaiting());
        } catch (NginxStubStatusParser.NginxStatusParserException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testNegativeCases() {
        List<String> statuses = new ArrayList<>();
        statuses.add(null);
        statuses.add("");
        statuses.add("fgdsg");
        statuses.add(getStatus(new int[]{1, 45, 1000, 67, 0, 1, 0}).replace("1000", "---"));
        statuses.add(getStatus(new int[]{1, 45, -1000, 67, 0, 1, 0}));

        for (String statusString : statuses) {
            try {
                NginxStubStatusParser.parseStatus(statusString);
                fail("Expected that parser will throw exception");
            } catch (NginxStubStatusParser.NginxStatusParserException e) {
                //expected
            }
        }
    }

}
