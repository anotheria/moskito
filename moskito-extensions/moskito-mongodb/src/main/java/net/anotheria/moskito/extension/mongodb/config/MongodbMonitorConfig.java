package net.anotheria.moskito.extension.mongodb.config;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Configuration for mongodb monitor
 */
@ConfigureMe(name = "mongodb-monitor")
public class MongodbMonitorConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(MongodbMonitorConfig.class);

    private static Lock lock = new ReentrantLock();

    private static MongodbMonitorConfig INSTANCE;

    @Configure
    private String host = "localhost";

    @Configure
    private String port = "27017";

    @Configure
    private String dbName = "admin";

    @Configure
    private String login;

    @Configure
    private String password;

    @Configure
    private String collectionName;

    @Configure
    private long updatePeriod = 60 * 1000L;

    private MongodbMonitorConfig() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public long getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public static MongodbMonitorConfig getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        lock.lock();
        INSTANCE = new MongodbMonitorConfig();
        try {
            ConfigurationManager.INSTANCE.configure(INSTANCE);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Can't configure mongodb monitor, working with defaults", e);
        } finally {
            lock.unlock();
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongodbMonitorConfig{");
        sb.append("host='").append(host).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append(", dbName='").append(dbName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", collectionName='").append(collectionName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
