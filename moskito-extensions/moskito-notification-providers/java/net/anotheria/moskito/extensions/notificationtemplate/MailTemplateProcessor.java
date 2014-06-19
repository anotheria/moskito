package net.anotheria.moskito.extensions.notificationtemplate;

import net.anotheria.util.StringUtils;
import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;

import java.io.Serializable;

/**
 * Mail template processor.
 */
public class MailTemplateProcessor implements TemplateProcessor {
	/**
	 * Processor's prefix constant.
	 */
	public static final String PREFIX = "mail";

	@Override
	public String replace(final String aPrefix, final String aVariable, final String aDefValue, final TemplateReplacementContext aContext) {
		if (!processingAllowed(aPrefix, aVariable, aContext))
			return aDefValue;

		MailTemplateReplacementContext context = MailTemplateReplacementContext.class.cast(aContext);

		MailTemplate mailTemplate = context.getMailTemplate();

		Serializable parameter = mailTemplate.getParameter(aVariable);

		if (!(parameter instanceof String))
			return aDefValue;

		String result = String.valueOf(parameter);

		return StringUtils.isEmpty(result) ? aDefValue : result;
	}

	/**
	 * Checks whether processing is allowed.
	 *
	 * @param prefix  variable prefix
	 * @param context {@link TemplateReplacementContext}
	 * @return {@code boolean} flag
	 */
	private boolean processingAllowed(final String prefix, final String variable, final TemplateReplacementContext context) {
		return (PREFIX.equals(prefix) && !StringUtils.isEmpty(variable) && context instanceof MailTemplateReplacementContext);
	}

}
