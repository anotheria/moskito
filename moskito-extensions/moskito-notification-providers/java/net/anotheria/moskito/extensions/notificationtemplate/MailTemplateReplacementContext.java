package net.anotheria.moskito.extensions.notificationtemplate;

import net.anotheria.util.content.template.TemplateReplacementContext;

/**
 * Mail template context.
 */
public class MailTemplateReplacementContext extends TemplateReplacementContext {
	/**
	 * {@link MailTemplate} instance.
	 */
	private MailTemplate template;

	/**
	 * Constructor.
	 *
	 * @param template {@link MailTemplate} to be stored in context.
	 */
	public MailTemplateReplacementContext(MailTemplate template) {
		if (template == null)
			throw new IllegalArgumentException("Parameter template is null");

		this.template = template;
	}

	/**
	 * Returns stored {@link MailTemplate}.
	 *
	 * @return stored {@link MailTemplate}
	 */
	public MailTemplate getMailTemplate() {
		return template;
	}
}
