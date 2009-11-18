package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public class EqualTag extends CompareTagBase {

	@Override protected boolean condition() throws JspException {
		return (condition(0, 0));
	}

}
