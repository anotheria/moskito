package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public class NotPresentTag extends PresentTag {

	@Override protected boolean condition() throws JspException {
		return (condition(false));
	}
}
