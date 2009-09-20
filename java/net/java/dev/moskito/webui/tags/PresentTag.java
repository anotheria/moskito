package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public class PresentTag extends ConditionalTagBase {

	protected boolean condition() throws JspException {
		return (condition(true));
	}

	protected boolean condition(boolean desired) throws JspException {
		boolean present = false;

		if (name != null) {
			try {
				Object value = TagUtils.lookup(pageContext, scope, name);
				present = (value != null);
			} catch (JspException e) {
			}
		} else {
			throw new JspException("Attribute 'name' was not specified");
		}

		return (present == desired);

	}
}
