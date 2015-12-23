package net.anotheria.moskito.extensions.diskspacemonitoring;

import net.anotheria.moskito.core.predefined.MemoryStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DiscSpace producer.
 */
public class DiscSpaceProducer implements IStatsProducer {
    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(DiscSpaceProducer.class);
    /**
     * The id of the producer.
     */
    private String path;
    /**
     * The id of the producer.
     */
    private String producerId;
    /**
     * Stats.
     */
    private MemoryStats stats;
    /**
     * Cached stats list.
     */
    private List<IStats> statsList;

    private static final String ID = "DiscSpaceUsable. path - ";

    private FileStore root;

    public DiscSpaceProducer(Path rootPath){
        path = rootPath.toString();
        producerId = ID + path;
        statsList = new CopyOnWriteArrayList<IStats>();
        stats = new MemoryStats(producerId);
        statsList.add(stats);

        try {
            root = Files.getFileStore(rootPath);
        } catch (IOException e) {
            log.error("Querying space. Init error: " + e.toString());
            return;
        }

        BuiltinUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                readMemory();
            }
        });
    }

    @Override
    public String getCategory() {
        return "memory";
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

    /**
     * Reads the memory value from the resolver and updates internal stats.
     */
    private void readMemory() {
        long space;
        try {
            space = root.getUsableSpace();
        } catch (IOException e) {
            log.error("Querying space. Querying error: " + e.toString());
            return;
        }
        stats.updateMemoryValue(space);
    }

    @Override public String toString(){
        return super.toString()+" "+this.getClass().getSimpleName();
    }
}
