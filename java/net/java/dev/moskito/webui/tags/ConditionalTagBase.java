package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ConditionalTagBase extends TagSupport {

	protected String name = null;
	protected String scope = null;

	public String getName() {
		return (this.name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScope() {
		return (this.scope);
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	protected abstract boolean condition() throws JspException;
	
    public int doStartTag() throws JspException {
        return condition() ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        name = null;
        scope = null;
    }

}