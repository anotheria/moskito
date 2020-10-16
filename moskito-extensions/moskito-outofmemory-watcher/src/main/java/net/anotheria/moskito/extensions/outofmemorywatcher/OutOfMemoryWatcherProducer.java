package net.anotheria.moskito.extensions.outofmemorywatcher;

import net.anotheria.moskito.core.predefined.OurOfMemoryErrorCountStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Out of memory watcher producer.
 */
public class OutOfMemoryWatcherProducer implements IStatsProducer {
    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(OutOfMemoryWatcherProducer.class);

    /**
     * Log file.
     */
    private File logFile;
    /**
     * The id of the producer.
     */
    private String producerId;
    /**
     * Stats.
     */
    private OurOfMemoryErrorCountStats stats;
    /**
     * Cached stats list.
     */
    private List<IStats> statsList;

    public OutOfMemoryWatcherProducer(File logFile) {
        this.logFile = logFile;
        producerId = logFile.getAbsolutePath();
        statsList = new CopyOnWriteArrayList<>();
        stats = new OurOfMemoryErrorCountStats(producerId);
        statsList.add(stats);
        BuiltinUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                watchingLogs();
            }
        });
    }

    @Override
    public String getCategory() {
        return "watcher";
    }

    @Override
    public String getProducerId() {
        return producerId;
    }

    @Override
    public List<IStats> getStats() {
        return statsList;
    }

    @Override
    public String getSubsystem() {
        return "plugins";
    }

    private void watchingLogs() {
        try {
            long errorCount = 0L;
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String line = reader.readLine();

            while (line != null) {
                if (line.contains("OutOfMemory")) {
                    errorCount++;
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
            stats.update(errorCount);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return super.toString() + ' ' + this.getClass().getSimpleName();
    }
}
