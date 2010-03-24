package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.core.producers.IStatsSnapshot;

import java.util.Date;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * Utility methods used in storage implementations
 *
 * @author imercuriev
 *         Date: Mar 24, 2010
 *         Time: 1:28:44 PM
 */
public class StatStorageUtils {

    public static final String LOG_MESSAGE_FORMAT = "Host: {0} Interval: {1} Producer: {2} Stat: {3} Date: {4}";
    private static final SimpleDateFormat MESSAGE_DATE_FORMAT =  new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public static String createLogMessage(final IStatsSnapshot snapshot, final Date when, final String host, final String interval) {
        return MessageFormat.format(LOG_MESSAGE_FORMAT, host, interval, snapshot.getProducerId(), snapshot.getName(), MESSAGE_DATE_FORMAT.format(when));
    }
}
