package net.anotheria.moskito.extension.mongodb;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for mongodb monitor
 */
@Ignore
public class MongodbMonitorTest {

    @BeforeClass
    public static void setup(){
        /* to disable builtin producers */
        System.setProperty("JUNITTEST", Boolean.TRUE.toString());
    }

    @Test
    public void test(){
        new MongodbMonitor();
    }
}
