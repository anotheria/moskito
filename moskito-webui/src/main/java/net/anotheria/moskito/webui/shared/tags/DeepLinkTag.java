package net.anotheria.moskito.webui.shared.tags;

import net.anotheria.moskito.webui.util.DeepLinkUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DeepLinkTag extends SimpleTagSupport implements DynamicAttributes {

    private Map<String, Object> attributes = new HashMap<>();
    private String href;

    @Override
    public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
        attributes.put(localName, value);
    }

    public void doTag()
            throws JspException, IOException
    {

        JspWriter out = getJspContext().getOut();
        out.print("<a ");
        out.print("href='" + getHref() + "' ");

        for (Map.Entry<String, Object> attrbute : attributes.entrySet())
            out.print(attrbute.getKey() + "='" + attrbute.getValue() + "' ");

        out.print(">");
        StringWriter sw = new StringWriter();
        getJspBody().invoke(sw);
        out.print(sw.toString());
        out.print("</a>");

    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = DeepLinkUtil.makeDeepLink(href);
    }

}
