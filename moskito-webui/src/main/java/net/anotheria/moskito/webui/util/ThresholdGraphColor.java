package net.anotheria.moskito.webui.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * Configuration which allow to specify threshold color of the accumulator's graph.
 *
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class ThresholdGraphColor implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Threshold status
     */
    @Configure
    private ThresholdStatus status;

    /**
     * Graph color.
     * Html color value, e.g. #RRGGBB.
     */
    @Configure
    private String color;

    public ThresholdStatus getStatus() {
        return status;
    }

    public void setStatus(ThresholdStatus status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ThresholdGraphColor{" +
                "status='" + status + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
