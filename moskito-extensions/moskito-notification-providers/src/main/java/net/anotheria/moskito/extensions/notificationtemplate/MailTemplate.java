package net.anotheria.moskito.extensions.notificationtemplate;


import java.io.Serializable;

/**
 * Generic mail template interface.
 */
public interface MailTemplate extends Serializable {
	/**
	 * Set parameter for mail template.
	 *
	 * @param key   parameter key
	 * @param value parameter value
	 */
	void setParameter(String key, Serializable value);

	/**
	 * Returns parameter for mail template.
	 *
	 * @param key parameter key
	 * @return {@link Serializable} parameter
	 */
	Serializable getParameter(String key);

	/**
	 * Process mail template and get result.
	 *
	 * @param content template content
	 * @return content with replaced variables
	 */
	String process(final String content) ;
}
