package net.anotheria.moskito.extension.nginx.config;

import net.anotheria.moskito.extension.nginx.HttpHelper;
import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test of NginxMonitorConfig configuring.
 *
 * @author dzhmud
 */
public class NginxMonitorConfigTest {

    @Test
    public void testConfigParsing() {
        NginxMonitorConfig config = new NginxMonitorConfig();
        ConfigurationManager.INSTANCE.configureAs(config, "nginx-monitor");
        assertNotNull(config.getMonitoredInstances());
        assertEquals(2, config.getMonitoredInstances().length);
        for (NginxMonitoredInstance nginx : config.getMonitoredInstances()) {
            assertNotNull(nginx.getLocation());
            assertNotNull(nginx.getName());
            assertTrue(StringUtils.isEmpty(nginx.getUsername()));
            assertTrue(StringUtils.isEmpty(nginx.getPassword()));
        }
    }

    @Test
    @Ignore("added for manual testing - simulating some load on nginx.")
    public void fetchStatus() {
        while (!Thread.currentThread().isInterrupted()) {
            int random = 1 + new Random().nextInt(5);
            for (int i = 0; i < 100 / random; i++) {
                try {
                    fetchStatusOnce();
                    Thread.sleep(60 * random);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fetchStatusOnce() {
        try {
            System.out.println(
                    HttpHelper.getURLContent("http://localhost/nginx_status")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
