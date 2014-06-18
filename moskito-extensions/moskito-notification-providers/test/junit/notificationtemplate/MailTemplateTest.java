package notificationtemplate;

import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.extensions.notificationtemplate.AlertThresholdTemplate;
import org.junit.Ignore;
import org.junit.Test;

/**
 * JUnit for {@link net.anotheria.moskito.extensions.notificationtemplate.MailTemplate}.
 */
public class MailTemplateTest {
	//@Ignore
	@Test
	public void testAlertThresholdTemplate() throws Exception {
		ThresholdDefinition td = new ThresholdDefinition();
		td.setName("TEST");

		Threshold testT = new Threshold(td);
		ThresholdAlert a1 = new ThresholdAlert(testT, ThresholdStatus.GREEN, ThresholdStatus.YELLOW, "oldValue", "newValue", 0);

		AlertThresholdTemplate alertThresholdTemplate = new AlertThresholdTemplate(a1);
		alertThresholdTemplate.setParameter("test", "asd");


		final String template = " <td>{mail:timestamp}</td>\n" +
				"<td>{mail:threshold}</td>\n" +
				"<td>{mail:oldStatus}</td>\n" +
				"<td>{mail:newStatus}</td>\n" +
				"<td>{mail:oldValue}</td>\n" +
				"<td>{mail:newValue}</td>\n" +
				"=====";
		String result = alertThresholdTemplate.process(template);
		System.out.println(result);
	}
}
