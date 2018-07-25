package net.anotheria.moskito.core.config.dashboards;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Configuration holder for a chart by accumulator patterns.
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class ChartPattern implements Serializable {

    /**
     * SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Chart caption.
     */
    @Configure
    private String caption;

    /**
     * All accumulators which names matches the pattern should be present on dashboard
     */
    @Configure
    private String[] accumulatorPatterns;

    /**
     * Stores compiled accumulatorPatterns
     */
    private Pattern[] patterns;

    /**
     * Graph data mode (single charts or combined chart).
     */
    @Configure
    private AccumulatorSetMode mode;


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String[] getAccumulatorPatterns() {
        return accumulatorPatterns;
    }

    public void setAccumulatorPatterns(String[] accumulatorPatterns) {
        this.accumulatorPatterns = accumulatorPatterns;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }

    public void setPatterns(Pattern[] patterns) {
        this.patterns = patterns;
    }

    public AccumulatorSetMode getMode() {
        return mode;
    }

    public void setMode(AccumulatorSetMode mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "ChartPattern{" +
                "caption='" + caption + '\'' +
                ", mode='" + mode + '\'' +
                ", accumulatorPatterns=" + Arrays.toString(accumulatorPatterns) +
				", patterns: "+Arrays.toString(patterns)+
                '}';
    }
}
