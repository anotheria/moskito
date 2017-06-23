package net.anotheria.moskito.extensions.tomcat;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for the Global Request Processor stats.
 *
 * @author esmakula
 */
public class GlobalRequestProcessorStatsDecorator extends AbstractDecorator {

    /**
     * Captions.
     */
    private static final String[] CAPTIONS = {
            "RequestCount",
            "MaxTime",
            "BytesReceived",
            "BytesSent",
            "ProcessingTime",
            "ErrorCount"
    };
    /**
     * Short explanations (mouse-over).
     */
    private static final String[] SHORT_EXPLANATIONS = {
            "Request Count",
            "Max Time",
            "Bytes Received",
            "Bytes Sent",
            "Processing Time",
            "Error Count"
    };

    /**
     * Detailed explanations.
     */
    private static final String[] EXPLANATIONS = {
            "Request count on all the request processors running on the Apache Tomcat container",
            "Max Time on all the request processors running on the Apache Tomcat container",
            "Bytes received by all the request processors running on the Apache Tomcat container",
            "Bytes sent by all the request processors running on the Apache Tomcat container",
            "Processing Time on all the request processors running on the Apache Tomcat container",
            "Error count on all the request processors running on the Apache Tomcat container collection"

    };

    /**
     * Default constructor.
     */
    public GlobalRequestProcessorStatsDecorator() {
        super("GlobalRequestProcessor", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }

    @Override
    public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
        GlobalRequestProcessorStats stats = (GlobalRequestProcessorStats) statsObject;

        List<StatValueAO> ret = new ArrayList<>(CAPTIONS.length);
        int i = 0;
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getRequestCount(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getMaxTime(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getBytesReceived(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getBytesSent(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getProcessingTime(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getErrorCount(interval)));

        return ret;
    }

}
