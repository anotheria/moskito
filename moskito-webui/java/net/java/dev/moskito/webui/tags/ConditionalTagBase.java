package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;

public abstract class ConditionalTagBase extends BaseTagSupport {

	protected abstract boolean condition() throws JspException;
	
    public int doStartTag() throws JspException {
        return condition() ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}