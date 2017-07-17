package net.anotheria.moskito.extension.apache;

/**
 * Holder class for values parsed from apache status page.
 *
 * @author dzhmud
 */
public class ApacheStatus implements Cloneable {

    /**Apache hostname.*/
    private String hostname;
    /**Total number of access requests.*/
    private long totalAccesses;
    /**Total number of kilobytes served.*/
    private long totalKbytes;
    /**Requests per second.*/
    private double requestsPerSec;
    /**Bytes per second.*/
    private double bytesPerSec;
    /**Bytes per request.*/
    private double bytesPerRequest;
    /**Number of busy workers.*/
    private int workersBusy;
    /**Number of idle workers.*/
    private int workersIdle;

    /**Uptime stats.*/

    /**Server uptime in seconds.*/
    private long serverUptime;
    /**Server uptime.*/
    private String uptime;

    /**CPU stats.*/

    /**CPU Load.*/
    private double cpuLoad;
    /**CPU user load.*/
    private double cpuUser;
    /**System cpu.*/
    private double cpuSystem;
    /**CPU of children user.*/
    private double cpuChildrenUser;
    /**CPU of children system.*/
    private double cpuChildrenSystem;

    /**Load averages.*/
    
    /**Load average for the last minute.*/
    private double load1;
    /**Load average for the last 5 minutes.*/
    private double load5;
    /**Load average for the last 15 minutes.*/
    private double load15;

    /**Connection stats.*/

    /**Total connections.*/
    private long connectionsTotal;
    /**Async connection writing.*/
    private long connectionsAsyncWriting;
    /**Async keeped alive connections.*/
    private long connectionsAsyncKeepAlive;
    /**Async closed connections.*/
    private long connectionsAsyncClosing;


    /**Scoreboard metrics.*/

    /**Starting up.*/
    private int scoreboardStartingUp;
    /**Reading requests.*/
    private int scoreboardReadingRequest;
    /**Sending Reply.*/
    private int scoreboardSendingReply;
    /**Keep alive.*/
    private int scoreboardKeepalive;
    /**Dns Lookups.*/
    private int scoreboardDnsLookup;
    /**Logging.*/
    private int scoreboardLogging;
    /**Closing connections.*/
    private int scoreboardClosingConnection;
    /**Gracefully finishing.*/
    private int scoreboardGracefullyFinishing;
    /**Idle cleanups.*/
    private int scoreboardIdleCleanup;
    /**Open slots.*/
    private int scoreboardOpenSlot;
    /**Waiting for connections.*/
    private int scoreboardWaitingForConnection;
    /**Total.*/
    private int scoreboardTotal;


    public String getHostname() {
        return hostname;
    }

    public long getTotalAccesses() {
        return totalAccesses;
    }

    public long getTotalKbytes() {
        return totalKbytes;
    }

    public double getRequestsPerSec() {
        return requestsPerSec;
    }

    public double getBytesPerSec() {
        return bytesPerSec;
    }

    public double getBytesPerRequest() {
        return bytesPerRequest;
    }

    public int getWorkersBusy() {
        return workersBusy;
    }

    public int getWorkersIdle() {
        return workersIdle;
    }

    public long getServerUptime() {
        return serverUptime;
    }

    public String getUptime() {
        return uptime;
    }

    public double getCpuLoad() {
        return cpuLoad;
    }

    public double getCpuUser() {
        return cpuUser;
    }

    public double getCpuSystem() {
        return cpuSystem;
    }

    public double getCpuChildrenUser() {
        return cpuChildrenUser;
    }

    public double getCpuChildrenSystem() {
        return cpuChildrenSystem;
    }

    public double getLoad1() {
        return load1;
    }

    public double getLoad5() {
        return load5;
    }

    public double getLoad15() {
        return load15;
    }

    public long getConnectionsTotal() {
        return connectionsTotal;
    }

    public long getConnectionsAsyncWriting() {
        return connectionsAsyncWriting;
    }

    public long getConnectionsAsyncKeepAlive() {
        return connectionsAsyncKeepAlive;
    }

    public long getConnectionsAsyncClosing() {
        return connectionsAsyncClosing;
    }

    public int getScoreboardStartingUp() {
        return scoreboardStartingUp;
    }

    public int getScoreboardReadingRequest() {
        return scoreboardReadingRequest;
    }

    public int getScoreboardSendingReply() {
        return scoreboardSendingReply;
    }

    public int getScoreboardKeepalive() {
        return scoreboardKeepalive;
    }

    public int getScoreboardLogging() {
        return scoreboardLogging;
    }

    public int getScoreboardDnsLookup() {
        return scoreboardDnsLookup;
    }

    public int getScoreboardClosingConnection() {
        return scoreboardClosingConnection;
    }

    public int getScoreboardGracefullyFinishing() {
        return scoreboardGracefullyFinishing;
    }

    public int getScoreboardIdleCleanup() {
        return scoreboardIdleCleanup;
    }

    public int getScoreboardOpenSlot() {
        return scoreboardOpenSlot;
    }

    public int getScoreboardWaitingForConnection() {
        return scoreboardWaitingForConnection;
    }

    public int getScoreboardTotal() {
        return scoreboardTotal;
    }

    /**
     * Create new Builder for ApacheStatus.
     * @return new ApacheStatus.Builder instance.
     */
    public static Builder newBuilder() {
        return new ApacheStatus().new Builder();
    }

    /** Builder for ApacheStatus class.*/
    final class Builder {

        private Builder(){}

        Builder setHostname(String hostname) {
            ApacheStatus.this.hostname = hostname;
            return this;
        }

        Builder setTotalAccesses(long totalAccesses) {
            ApacheStatus.this.totalAccesses = totalAccesses;
            return this;
        }

        Builder setTotalKbytes(long totalKbytes) {
            ApacheStatus.this.totalKbytes = totalKbytes;
            return this;
        }

        Builder setRequestsPerSec(double requestsPerSec) {
            ApacheStatus.this.requestsPerSec = requestsPerSec;
            return this;
        }

        Builder setBytesPerSec(double bytesPerSec) {
            ApacheStatus.this.bytesPerSec = bytesPerSec;
            return this;
        }

        Builder setBytesPerRequest(double bytesPerRequest) {
            ApacheStatus.this.bytesPerRequest = bytesPerRequest;
            return this;
        }

        Builder setWorkersBusy(int workersBusy) {
            ApacheStatus.this.workersBusy = workersBusy;
            return this;
        }

        Builder setWorkersIdle(int workersIdle) {
            ApacheStatus.this.workersIdle = workersIdle;
            return this;
        }

        Builder setServerUptime(long serverUptime) {
            ApacheStatus.this.serverUptime = serverUptime;
            return this;
        }

        Builder setUptime(String uptime) {
            ApacheStatus.this.uptime = uptime;
            return this;
        }

        Builder setCpuLoad(double cpuLoad) {
            ApacheStatus.this.cpuLoad = cpuLoad;
            return this;
        }

        Builder setCpuUser(double cpuUser) {
            ApacheStatus.this.cpuUser = cpuUser;
            return this;
        }

        Builder setCpuSystem(double cpuSystem) {
            ApacheStatus.this.cpuSystem = cpuSystem;
            return this;
        }

        Builder setCpuChildrenUser(double cpuChildrenUser) {
            ApacheStatus.this.cpuChildrenUser = cpuChildrenUser;
            return this;
        }

        Builder setCpuChildrenSystem(double cpuChildrenSystem) {
            ApacheStatus.this.cpuChildrenSystem = cpuChildrenSystem;
            return this;
        }

        Builder setLoad1(double load1) {
            ApacheStatus.this.load1 = load1;
            return this;
        }

        Builder setLoad5(double load5) {
            ApacheStatus.this.load5 = load5;
            return this;
        }

        Builder setLoad15(double load15) {
            ApacheStatus.this.load15 = load15;
            return this;
        }

        Builder setConnectionsTotal(long connectionsTotal) {
            ApacheStatus.this.connectionsTotal = connectionsTotal;
            return this;
        }

        Builder setConnectionsAsyncWriting(long connectionsAsyncWriting) {
            ApacheStatus.this.connectionsAsyncWriting = connectionsAsyncWriting;
            return this;
        }

        Builder setConnectionsAsyncKeepAlive(long connectionsAsyncKeepAlive) {
            ApacheStatus.this.connectionsAsyncKeepAlive = connectionsAsyncKeepAlive;
            return this;
        }

        Builder setConnectionsAsyncClosing(long connectionsAsyncClosing) {
            ApacheStatus.this.connectionsAsyncClosing = connectionsAsyncClosing;
            return this;
        }

        Builder setScoreboardStartingUp(int scoreboardStartingUp) {
            ApacheStatus.this.scoreboardStartingUp = scoreboardStartingUp;
            return this;
        }

        Builder setScoreboardReadingRequest(int scoreboardReadingRequest) {
            ApacheStatus.this.scoreboardReadingRequest = scoreboardReadingRequest;
            return this;
        }

        Builder setScoreboardSendingReply(int scoreboardSendingReply) {
            ApacheStatus.this.scoreboardSendingReply = scoreboardSendingReply;
            return this;
        }

        Builder setScoreboardKeepalive(int scoreboardKeepalive) {
            ApacheStatus.this.scoreboardKeepalive = scoreboardKeepalive;
            return this;
        }

        Builder setScoreboardLogging(int scoreboardLogging) {
            ApacheStatus.this.scoreboardLogging = scoreboardLogging;
            return this;
        }

        Builder setScoreboardDnsLookup(int scoreboardDnsLookup) {
            ApacheStatus.this.scoreboardDnsLookup = scoreboardDnsLookup;
            return this;
        }

        Builder setScoreboardClosingConnection(int scoreboardClosingConnection) {
            ApacheStatus.this.scoreboardClosingConnection = scoreboardClosingConnection;
            return this;
        }

        Builder setScoreboardGracefullyFinishing(int scoreboardGracefullyFinishing) {
            ApacheStatus.this.scoreboardGracefullyFinishing = scoreboardGracefullyFinishing;
            return this;
        }

        Builder setScoreboardIdleCleanup(int scoreboardIdleCleanup) {
            ApacheStatus.this.scoreboardIdleCleanup = scoreboardIdleCleanup;
            return this;
        }

        Builder setScoreboardOpenSlot(int scoreboardOpenSlot) {
            ApacheStatus.this.scoreboardOpenSlot = scoreboardOpenSlot;
            return this;
        }

        Builder setScoreboardWaitingForConnection(int scoreboardWaitingForConnection) {
            ApacheStatus.this.scoreboardWaitingForConnection = scoreboardWaitingForConnection;
            return this;
        }

        Builder setScoreboardTotal(int scoreboardTotal) {
            ApacheStatus.this.scoreboardTotal = scoreboardTotal;
            return this;
        }

        ApacheStatus createApacheStatus() {
            try {
                return ApacheStatus.class.cast(ApacheStatus.this.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
