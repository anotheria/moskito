package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.NumberUtils;

/**
 * {@link ThresholdAlert} converter.
 *
 * @author Yuriy Koval'.
 */
public class ThresholdAlertConverter {

    /**
     * Convert {@link ThresholdAlert} to plain text.
     *
     * @param thresholdAlert {@link ThresholdAlert}.
     * @return threshold alert plain text.
     */
    public static String toPlainText(ThresholdAlert thresholdAlert) {
        String text = "Threshold alert:\n";
        text += "Timestamp: "+thresholdAlert.getTimestamp()+ '\n';
        text += "ISO Timestamp: "+ NumberUtils.makeISO8601TimestampString(thresholdAlert.getTimestamp())+ '\n';
        text += "Threshold: "+thresholdAlert.getThreshold().getName()+ '\n';
        text += "OldStatus: "+thresholdAlert.getOldStatus()+ '\n';
        text += "NewStatus: "+thresholdAlert.getNewStatus()+ '\n';
        text += "OldValue: "+thresholdAlert.getOldValue()+ '\n';
        text += "NewValue: "+thresholdAlert.getNewValue()+ '\n';
        return text;
    }
}
