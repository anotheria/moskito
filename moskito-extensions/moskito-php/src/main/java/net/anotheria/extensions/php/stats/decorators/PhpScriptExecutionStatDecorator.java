package net.anotheria.extensions.php.stats.decorators;

import net.anotheria.extensions.php.stats.PHPScriptExecutionStats;
import net.anotheria.moskito.core.decorators.predefined.RequestOrientedStatsDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.List;

/**
 * Decorator for php execution stats.
 * Based on service oriented decorator with added memory consumption captions and explanations
 */
public class PhpScriptExecutionStatDecorator extends RequestOrientedStatsDecorator {

    /**
     * Captions.
     */
    private static final String CAPTIONS[] = {
            "Req",
            "Time",
            "CR",
            "MCR",
            "MinTime",
            "MaxTime",
            "AvgTime",
            "LastTime",
            "Err",
            "ERate",
            "MinMem",
            "MaxMem",
            "AvgMem"
    };

    /**
     * Short explanations.
     */
    private static final String SHORT_EXPLANATIONS[] = {
            "Number of requests",
            "Time spent in millis (duration)",
            "Concurrent requests",
            "Max concurrent requests",
            "Minimal duration in ms",
            "Maximal duration in ms",
            "Average duration in ms",
            "Last request durations",
            "Number of errors",
            "Error rate in %",
            "Minimal memory consumption",
            "Maximal memory consumption",
            "Average memory consumption"
    };

    /**
     * Explanations.
     */
    private static final String EXPLANATIONS[] = {
            "Total number of requests (in the defined interval or since start, depending on your interval selection).",
            "Total amount of time spent for request. Although, if the called method is waiting for something to be transported from net or from disk, the value is not equal to spent processor time, this value is usually an important indicator to determine how much some functionality costs.",
            "Number of concurrent requests. This value is not of much interest for time intervals, since it will be any value which was set at the moment of the interval update (and can be even negative since interval updates are fires unsynchronized to prevent performance loss). However, with the default interval (since start) selected it will tell you how many requests are served in the moment.",
            "Max concurrent requests. Unlike the CR value, this value is interesting for intervals; it gives you the info how much parallel load a method / interface suffers.",
            "The minimum amount of time (in milliseconds) spent on request. For most use-cases you can expect this value to be pretty low or even zero.",
            "The maximum amount of time (in milliseconds) spent on request",
            "The average amount of time spent on request. This method will give you the average duration of a request. This is especially interesting if you have different load through the day, by comparing or drawing for example the 5 mins value of AVG you can determine how well your system handles different load. This value is calculated by simple division time / requests and can be slightly incorrent, if you have very many requests which have short duration. ",
            "The duration of the last request.",
            "Total number of uncaught errors of the method / interface.",
            "The number of uncaught errors as percent of total requests.",
            "The minimum amount of time (in milliseconds) spent on request",
            "The maximum amount of time (in milliseconds) spent on request",
            "The average amount of time (in milliseconds) spent on request",
    };


    public PhpScriptExecutionStatDecorator(){
        super("phpExecution", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }

    @Override
    public List<StatValueAO> getValues(IStats stats, String interval, TimeUnit unit) {
        List<StatValueAO> statsList = super.getValues(stats, interval, unit);
        PHPScriptExecutionStats phpStats = ((PHPScriptExecutionStats) stats);

        statsList.add(new LongValueAO("Min Memory", phpStats.getMinMemoryUsage()));
        statsList.add(new LongValueAO("Max Memory", phpStats.getMaxMemoryUsage()));
        statsList.add(new DoubleValueAO("Avg Memory", phpStats.getAverageMemoryUsage(interval)));

        return statsList;

    }
}
