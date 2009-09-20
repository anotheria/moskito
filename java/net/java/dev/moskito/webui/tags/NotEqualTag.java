package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public class NotEqualTag extends CompareTagBase {

	protected boolean condition() throws JspException {
		return (condition(1, -1));
	}

}
