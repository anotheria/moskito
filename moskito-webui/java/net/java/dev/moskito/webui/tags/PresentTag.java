package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public class PresentTag extends ConditionalTagBase {

	@Override protected boolean condition() throws JspException {
		return (condition(true));
	}

	protected boolean condition(boolean desired) throws JspException {
		boolean present = false;

		if (getName() != null) {
			try {
				Object value = lookup();
				present = (value != null);
			} catch (JspException e) {
			}
		} else {
			throw new JspException("Attribute 'name' was not specified");
		}

		return (present == desired);

	}
}
