package net.anotheria.moskito.extensions.monitoring.decorator;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.extensions.monitoring.metrics.IGenericMetrics;
import net.anotheria.moskito.extensions.monitoring.stats.GenericStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic stats decorator, containing common logic.
 *
 * @param <GS> the type of stats decorated by this class.
 * @param <GM> the type of metrics bound to <GS> stats type.
 *
 * @author dzhmud
 */
public class GenericStatsDecorator<GS extends GenericStats<GM>, GM extends IGenericMetrics> extends AbstractDecorator<GS> {

    /**
     * Creates new GenericStatsDecorator with given name and captions/explanations
     * extracted from given metrics.
     */
    GenericStatsDecorator(String aName, List<? extends IGenericMetrics> metrics) {
        super(aName,
                getCaptions(metrics),
                getShortExplanations(metrics),
                getExplanations(metrics)
        );
    }

    @Override
    public List<StatValueAO> getValues(GS genericStats, String interval, TimeUnit unit) {
        List<StatValueAO> bean = new ArrayList<>(getCaptions().size());
        Map<String,GM> metrics = new HashMap<>();
        for (GM metric : genericStats.getAvailableMetrics()) {
            metrics.put(metric.getCaption(), metric);
        }
        for (StatCaptionBean caption : getCaptions()) {
            if (genericStats.isNeverUpdated()) {
                //display "NoR" to clearly differentiate not connectible/parseable from inactive
                bean.add(new StringValueAO(caption.getCaption(), "NoR"));
            } else {
                GM metric = metrics.get(caption.getCaption());
                if (metric == null)
                    bean.add(new StringValueAO(caption.getCaption(), "---"));
                else if (metric.isDoubleValue())
                    bean.add(new DoubleValueAO(metric.getCaption(), genericStats.getStatValueAsDouble(metric, interval)));
                else if (metric.isIntegerValue())
                    bean.add(new LongValueAO(metric.getCaption(), genericStats.getStatValueAsInteger(metric, interval)));
                else if (metric.isLongValue())
                    bean.add(new LongValueAO(metric.getCaption(), genericStats.getStatValueAsLong(metric, interval)));
                else
                    bean.add(new StringValueAO(metric.getCaption(), genericStats.getStatValueAsString(metric, interval)));

            }
        }
        return bean;
    }

    /**
     * Get captions of all available ApacheMetrics.
     * @param metrics metrics to extract values from.
     * @return array of metric captions.
     */
    private static String[] getCaptions(List<? extends IGenericMetrics> metrics) {
        return extract(metrics, Extractor.CAPTIONS);
    }

    /**
     * Get short explanations of all available ApacheMetrics.
     * @param metrics metrics to extract values from.
     * @return array of metric short explanations.
     */
    private static String[] getShortExplanations(List<? extends IGenericMetrics> metrics) {
        return extract(metrics, Extractor.SHORT_EXPLANATIONS);
    }

    /**
     * Get full explanations of all available ApacheMetrics.
     * @param metrics metrics to extract values from.
     * @return array of metric full explanations.
     */
    private static String[] getExplanations(List<? extends IGenericMetrics> metrics) {
        return extract(metrics, Extractor.EXPLANATIONS);
    }

    /**
     * Get array of Strings from metrics - one from each with given Extractor.
     * @param metrics metrics to extract values from.
     * @param e extractor that extracts values
     * @return array of corresponding strings.
     */
    private static String[] extract(List<? extends IGenericMetrics> metrics, Extractor e) {
        List<String> strings = new ArrayList<>(metrics.size());
        for (IGenericMetrics metric : metrics) {
            strings.add(e.extract(metric));
        }
        return strings.toArray(new String[strings.size()]);
    }

    /** Extractor interface with single {@link #extract(IGenericMetrics)} method. */
    private interface Extractor {

        String extract(IGenericMetrics metric);

        /** Implementation that extracts caption.*/
        Extractor CAPTIONS = new Extractor() {
            @Override public String extract(IGenericMetrics metric) {
                return metric.getCaption();
            }
        };
        /** Implementation that extracts full explanation.*/
        Extractor EXPLANATIONS = new Extractor() {
            @Override public String extract(IGenericMetrics metric) {
                return metric.getExplanation();
            }
        };
        /** Implementation that extracts short explanation.*/
        Extractor SHORT_EXPLANATIONS = new Extractor() {
            @Override public String extract(IGenericMetrics metric) {
                return metric.getShortExplanation();
            }
        };
    }

}
