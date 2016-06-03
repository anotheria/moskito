package net.anotheria.moskito.extensions.notificationtemplate;

import net.anotheria.util.StringUtils;
import net.anotheria.util.content.template.TemplateUtility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract mail template.
 */
public abstract class AbstractMailTemplate implements MailTemplate {
	/**
	 * Basic serial version UID.
	 */
	private static final long serialVersionUID = -838222945180416828L;

	/**
	 * Mail template parameters.
	 */
	private Map<String, Serializable> parameters = new HashMap<>();

	/**
	 * Static init block.
	 */
	static {
		TemplateUtility.addProcessor(MailTemplateProcessor.PREFIX, new MailTemplateProcessor());
	}

	@Override
	public void setParameter(final String key, final Serializable value) {
		if (StringUtils.isEmpty(key) || value == null)
			return;

		parameters.put(key, value);
	}

	@Override
	public Serializable getParameter(final String key) {
		if (StringUtils.isEmpty(key))
			return null;

		return parameters.get(key);
	}
}
