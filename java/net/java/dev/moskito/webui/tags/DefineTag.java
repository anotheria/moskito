package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DefineTag extends BodyTagSupport {

    protected String body = null;

    protected String id = null;
    protected String name = null;
    protected String property = null;
    protected String type = null;

    public String getId() {
        return (this.id);
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return (this.name);
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProperty() {
        return (this.property);
    }
    public void setProperty(String property) {
        this.property = property;
    }
    public String getType() {
        return (this.type);
    }
    public void setType(String type) {
        this.type = type;
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
        if (this.name != null) n++;
        if (n != 1) {
            throw new JspException("Define tag should contain exactly one of name attribute or body content");
        }

        Object value = null;
        if (name != null) {
            value = TagUtils.lookup(pageContext, null, name, property);
        }
        if (body != null) {
            value = body;
        }
        if (value == null) {
            throw new JspException("Define tag cannot set a null value");
        }

        pageContext.setAttribute(id, value, PageContext.PAGE_SCOPE);
        return (EVAL_PAGE);
    }

    public void release() {
        super.release();
        body = null;
        id = null;
        name = null;
        property = null;
        type = null;
    }
    
}
