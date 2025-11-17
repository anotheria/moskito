package net.anotheria.moskito.core.entrypoint;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Tracks request activity and performance measurements for a specific application entry point.
 *
 * <p>An EntryPoint represents a monitored entry into the application (such as a servlet, REST endpoint,
 * or service method) and maintains statistics about requests passing through that entry point. It tracks
 * both currently active requests and historical data about completed requests.
 *
 * <p><b>Request Tracking:</b>
 * <ul>
 *   <li><b>Total Requests:</b> Cumulative count of all requests ever received</li>
 *   <li><b>Current Requests:</b> Number of requests currently being processed</li>
 *   <li><b>Active Measurements:</b> Detailed information about requests in progress</li>
 *   <li><b>Past Measurements:</b> Historical data about completed requests (slowest calls)</li>
 * </ul>
 *
 * <p><b>Performance Analysis:</b> The entry point maintains a chain of past measurements,
 * automatically keeping track of the slowest requests for performance analysis. This helps
 * identify performance bottlenecks without storing every single request.
 *
 * <p><b>Thread Safety:</b> This class is thread-safe. Request counters use {@link AtomicLong}
 * for atomic operations, and measurements are stored in {@link CopyOnWriteArrayList} for
 * concurrent access.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Entry points are typically managed by EntryPointRepository
 * EntryPoint entryPoint = new EntryPoint("MyServlet");
 *
 * // Mark request start
 * entryPoint.requestStarted();
 *
 * // ... process request ...
 *
 * // Mark request completion
 * entryPoint.requestFinished(measurement);
 *
 * // View statistics
 * long total = entryPoint.getTotalRequestCount();
 * long active = entryPoint.getCurrentRequestCount();
 * List&lt;PastMeasurement&gt; slowest = entryPoint.getPastMeasurements();
 * </pre>
 *
 * @author lrosenberg
 * @since 10.09.20 16:08
 * @see EntryPointRepository
 * @see ActiveMeasurement
 * @see PastMeasurement
 */
public class EntryPoint {
    /**
     * Name of the producer which is also the id of the entry point.
     */
    private String producerId;
    /**
     * Total number of requests this entry saw.
     */
    private AtomicLong totalRequests = new AtomicLong();
    /**
     * Number of currently active requests.
     */
    private AtomicLong currentRequests = new AtomicLong();

    private CopyOnWriteArrayList<ActiveMeasurement> currentMeasurements = new CopyOnWriteArrayList<>();

    private PastMeasurementChainNode pastMeasurements;

    public EntryPoint(String aProducerId) {
        producerId = aProducerId;
    }

    public void requestStarted() {
        totalRequests.incrementAndGet();
        currentRequests.incrementAndGet();
    }

    public void requestFinished(ActiveMeasurement measurement) {
        currentRequests.decrementAndGet();
        currentMeasurements.remove(measurement);
        PastMeasurementChainNode newNode = new PastMeasurementChainNode(measurement);
        pastMeasurements = PastMeasurementChainNode.addToChainIfLongerDuration(pastMeasurements, newNode);
    }

    public void removePastMeasurementByItsPosition(int measurementPosition) {
        pastMeasurements = pastMeasurements.removePastMeasurementByItsPosition(measurementPosition);
    }

    public String toString() {
        return "Id: " + producerId + ", CR: " + currentRequests + ", TR: " + totalRequests + ", CM: " + currentMeasurements;
    }

    public String getProducerId() {
        return producerId;
    }

    public long getTotalRequestCount() {
        return totalRequests.get();
    }

    public long getCurrentRequestCount() {
        return currentRequests.get();
    }

    public void addCurrentMeasurements(ActiveMeasurement measurement) {
        currentMeasurements.add(measurement);
    }

    public List<ActiveMeasurement> getCurrentMeasurements() {
        return currentMeasurements;
    }

    public List<PastMeasurement> getPastMeasurements() {
        return pastMeasurements == null ?
                Collections.<PastMeasurement>emptyList() :
                pastMeasurements.getMeasurements();
    }
}
