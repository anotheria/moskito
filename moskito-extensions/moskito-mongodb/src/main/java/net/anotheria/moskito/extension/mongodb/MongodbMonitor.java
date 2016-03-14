package net.anotheria.moskito.extension.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.extension.mongodb.config.MongodbMonitorConfig;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class to monitor mongodb instance
 */
public class MongodbMonitor {

    private final static Logger LOGGER = LoggerFactory.getLogger(MongodbMonitor.class);

    private static final String DB_ADMIN = "admin";

    private MongoClient mongoClient;

    private static OnDemandStatsProducer<MongodbStats> producer;

    public MongodbMonitor() {
        producer = new OnDemandStatsProducer<MongodbStats>("MongoMonitor", "Monitor", "db", new MongodbStatsFactory());
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new UpdateTask(), 0,
                MongodbMonitorConfig.getInstance().getUpdatePeriod(), TimeUnit.MINUTES);
    }

    public void updateStats() {
        try {
            mongoClient = createClient(MongodbMonitorConfig.getInstance());
            MongoDatabase adminDb = mongoClient.getDatabase(DB_ADMIN);
            updateMongoServerStats(adminDb);
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    private MongoClient createClient(MongodbMonitorConfig config) {
        List<MongoCredential> mongoCredentials = createMongoCredentials(config);
        if (mongoCredentials.size() == 0) {
            return new MongoClient(config.getHost(), Integer.parseInt(config.getPort()));
        } else {
            return new MongoClient(new ServerAddress(config.getHost(), Integer.parseInt(config.getPort())), mongoCredentials);
        }
    }

    private List<MongoCredential> createMongoCredentials(MongodbMonitorConfig config) {
        List<MongoCredential> mongoCredentials = new ArrayList<>();
        if (config.getLogin() == null) {
            return mongoCredentials;
        }
        mongoCredentials.add(MongoCredential.createCredential(config.getLogin(), config.getDbName(), config.getPassword().toCharArray()));
        return mongoCredentials;
    }

    private void updateMongoServerStats(MongoDatabase dbAdmin) {
        DBObject serverStats = executeMongoCommand(dbAdmin, "serverStatus");
        Map<String, Object> map = serverStats.toMap();
        updateFlushing(map);
        updateConnections(map);
    }

    private void updateFlushing(Map<String, Object> map) {
        Map<String, Object> backgroundFlushing = ((Map<String, Object>) map.get("backgroundFlushing"));
        MongodbStats stats = getProducerStats();
        stats.getFlushes().setValueAsLong(((Long) backgroundFlushing.get("flushes")));
        stats.getTotal_ms_write().setValueAsLong(((Long) backgroundFlushing.get("total_ms")));
        stats.getAvg_ms_write().setValueAsDouble(((Double) backgroundFlushing.get("average_ms")));
        stats.getLast_ms_write().setValueAsLong(((Long) backgroundFlushing.get("last_ms")));
    }

    private void updateConnections(Map<String, Object> map) {
        Map<String, Object> connections = ((Map<String, Object>) map.get("connections"));
        MongodbStats stats = getProducerStats();
        stats.getCurrent_connections().setValueAsLong(((Long) connections.get("current")));
        stats.getAvailable_connections().setValueAsLong(((Long) connections.get("available")));
        stats.getTotal_created_connections().setValueAsLong(((Long) connections.get("totalCreated")));
    }

    private MongodbStats getProducerStats() {
        try {
            return producer.getStats("cumulated");
        } catch (OnDemandStatsProducerException e) {
            throw new IllegalStateException(e);
        }
    }

    private DBObject executeMongoCommand(MongoDatabase db, String command) {
        DBObject dbObject = null;
        try {
            dbObject = (DBObject) JSON.parse(db.runCommand(new Document(command, 1)).toJson());
        } catch (MongoCommandException e) {
            LOGGER.error("Couldn't execute mongo command", e);
        }
        return dbObject;
    }

    public static MongodbMonitor createMongodbMonitor() {
        return new MongodbMonitor();
    }

    public static void destroyMongodbMonitor() {
        ProducerRegistryFactory.getProducerRegistryInstance().unregisterProducer(producer);
    }

    private class UpdateTask implements Runnable {
        @Override
        public void run() {
            updateStats();
        }
    }


}
