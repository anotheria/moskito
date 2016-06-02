package net.anotheria.moskito.extensions.notificationtemplate;

import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.content.template.TemplateUtility;

/**
 * {@link ThresholdAlert} mail template.
 */
public class ThresholdAlertTemplate extends AbstractMailTemplate {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * {@link ThresholdAlert#threshold} template variable name.
	 */
	private static final String THRESHOLD = "threshold";
	/**
	 * {@link ThresholdAlert#timestamp} template variable name.
	 */
	private static final String TIMESTAMP = "timestamp";
	/**
	 * {@link ThresholdAlert#oldStatus} template variable name.
	 */
	private static final String OLD_STATUS = "oldStatus";
	/**
	 * {@link ThresholdAlert#newStatus} template variable name.
	 */
	private static final String NEW_STATUS = "newStatus";
	/**
	 * {@link ThresholdAlert#newValue} template variable name.
	 */
	private static final String NEW_VALUE = "newValue";
	/**
	 * {@link ThresholdAlert#oldValue} template variable name.
	 */
	private static final String OLD_VALUE = "oldValue";

	/**
	 * Constructor.
	 *
	 * @param thresholdAlert {@link ThresholdAlert}
	 */
	public ThresholdAlertTemplate(final ThresholdAlert thresholdAlert) {
		if (thresholdAlert == null)
			throw new IllegalArgumentException("thresholdAlert is null");
		setParameter(THRESHOLD, thresholdAlert.getThreshold().getName());
		setParameter(TIMESTAMP, NumberUtils.makeISO8601TimestampString(thresholdAlert.getTimestamp()));
		setParameter(OLD_STATUS, thresholdAlert.getOldStatus().name());
		setParameter(NEW_STATUS, thresholdAlert.getNewStatus().name());
		setParameter(NEW_VALUE, thresholdAlert.getNewValue());
		setParameter(OLD_VALUE, thresholdAlert.getOldValue());
	}

	@Override
	public String process(String src)  {
		// Create replacement contexts with current template
		MailTemplateReplacementContext context = new MailTemplateReplacementContext(this);

		return TemplateUtility.replaceVariables(context, src);
	}
}
