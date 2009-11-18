package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class DefineTag extends BaseBodyTagSupport {

    protected String body = null;

    protected String type = null;
    protected String toScope = null;


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getToScope() {
    	return toScope;
    }
    public void setToScope(String toScope) {
    	this.toScope = toScope;
    }


    public int doStartTag() throws JspException {
        return (EVAL_BODY_TAG);
    }

    public int doAfterBody() throws JspException {

        if (bodyContent != null) {
            body = bodyContent.getString();
            if (body != null) {
                body = body.trim();
            }
            if (body.length() < 1) {
                body = null;
            }
        }
        return (SKIP_BODY);

    }

    public int doEndTag() throws JspException {
        int n = 0;
        if (this.body != null) n++;
        if (this.getName() != null) n++;
        if (n != 1) {
            throw new JspException("Define tag should contain exactly one of name attribute or body content");
        }

        Object value = null;
        if (getName() != null) {
            value = lookup();
        }
        if (body != null) {
            value = body;
        }
        if (value == null) {
            throw new JspException("Define tag cannot set a null value");
        }
        //expose bean
        int inScope = PageContext.PAGE_SCOPE;
        if (toScope != null) {
        	try {
				inScope = TagUtils.getScope(toScope);
        	} catch (JspException e) {}
        }
            
        pageContext.setAttribute(getId(), value, inScope);

        return (EVAL_PAGE);
    }

    public void release() {
        super.release();
        body = null;
        type = null;
        toScope = null;
    }
    
}
