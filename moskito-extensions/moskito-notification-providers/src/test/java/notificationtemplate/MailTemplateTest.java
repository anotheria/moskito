package notificationtemplate;

import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.extensions.notificationtemplate.ThresholdAlertTemplate;
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

		ThresholdAlertTemplate thresholdAlertTemplate = new ThresholdAlertTemplate(a1);
		thresholdAlertTemplate.setParameter("test", "asd");


		final String template = " <td>{mail:timestamp}</td>\n" +
				"<td>{mail:threshold}</td>\n" +
				"<td>{mail:oldStatus}</td>\n" +
				"<td>{mail:newStatus}</td>\n" +
				"<td>{mail:oldValue}</td>\n" +
				"<td>{mail:newValue}</td>\n" +
				"=====";
		String result = thresholdAlertTemplate.process(template);
		System.out.println(result);
	}
}
