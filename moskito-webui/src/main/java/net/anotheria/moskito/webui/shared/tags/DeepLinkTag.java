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

/**
 * Tag to create deep internal links in moskito-inspect with remote connection information.
 * This tag translates to `a` html tag with its body and attributes.
 * So, this tag acts same as `a` tag except this one adds `remoteConnection`
 * GET parameter to href attribute of tag, if it was not already present or current monitor connection is local.
 *
 * Example (tag name `mos:deepLink` means this tag) :
 *
 *      <mos:deepLink id="deepLink" href="mskDashboards">Dashboards</mos:deepLink>
 *                                         ⇩
 *  <a href="mskDashboards?remoteConnection=localhost:9104" id="deepLink">Dashboards</a>
 *
 */
public class DeepLinkTag extends SimpleTagSupport implements DynamicAttributes {

    /**
     * Dynamic tag attributes collects here
     */
    private Map<String, Object> attributes = new HashMap<>();
    /**
     * href attribute as in `a` html tag
     */
    private String href;

    @Override
    public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
        attributes.put(localName, value);
    }

    public void doTag()
            throws JspException, IOException
    {

        JspWriter out = getJspContext().getOut();

        // Begin `a` open tag
        out.print("<a ");
        // Adding href attribute to tag
        out.print("href='" + getHref() + "' ");

        // Adding all other attributes
        for (Map.Entry<String, Object> attribute : attributes.entrySet())
            out.print(attribute.getKey() + "='" + attribute.getValue() + "' ");

        // Closing open `a` tag.
        out.print(">");
        // Appending tag body
        StringWriter sw = new StringWriter();
        getJspBody().invoke(sw);
        out.print(sw.toString());
        // Appending closing tag
        out.print("</a>");

    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = DeepLinkUtil.makeDeepLink(href);
    }

}
