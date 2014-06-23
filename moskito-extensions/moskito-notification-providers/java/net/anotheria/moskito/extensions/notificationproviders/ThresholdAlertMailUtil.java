package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.NumberUtils;

/**
 * @author Yuriy Koval'.
 */
public class ThresholdAlertMailUtil {

    public static String alertToPlainText(ThresholdAlert thresholdAlert) {
        String mailText = "Threshold alert:\n";
        mailText += "Timestamp: "+thresholdAlert.getTimestamp()+"\n";
        mailText += "ISO Timestamp: "+ NumberUtils.makeISO8601TimestampString(thresholdAlert.getTimestamp())+"\n";
        mailText += "Threshold: "+thresholdAlert.getThreshold()+"\n";
        mailText += "OldStatus: "+thresholdAlert.getOldStatus()+"\n";
        mailText += "NewStatus: "+thresholdAlert.getNewStatus()+"\n";
        mailText += "OldValue: "+thresholdAlert.getOldValue()+"\n";
        mailText += "NewValue: "+thresholdAlert.getNewValue()+"\n";
        return mailText;
    }
}
