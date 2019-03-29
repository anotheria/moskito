package net.anotheria.moskito.webui.shared.api;

import net.anotheria.moskito.core.config.KillSwitchConfiguration;
import net.anotheria.moskito.core.threshold.ThresholdStatus;

import java.io.Serializable;

/**
 * Represents system status.
 *
 * @author Illya Bogatyrchuk
 */
public class SystemStatusAO implements Serializable {
    private static final long serialVersionUID = -4454516193679348818L;
    private ThresholdStatus worstThreshold;
    private KillSwitchConfiguration killSwitchConfiguration;

    public ThresholdStatus getWorstThreshold() {
        return worstThreshold;
    }

    public void setWorstThreshold(ThresholdStatus worstThreshold) {
        this.worstThreshold = worstThreshold;
    }

    public KillSwitchConfiguration getKillSwitchConfiguration() {
        return killSwitchConfiguration;
    }

    public void setKillSwitchConfiguration(KillSwitchConfiguration killSwitchConfiguration) {
        this.killSwitchConfiguration = killSwitchConfiguration;
    }
}
