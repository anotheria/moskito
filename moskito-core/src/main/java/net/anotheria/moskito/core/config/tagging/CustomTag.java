package net.anotheria.moskito.core.config.tagging;

import org.apache.commons.lang.StringUtils;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * User defined tag.
 * @author strel
 */
@ConfigureMe
public class CustomTag implements Serializable{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1;

    /**
     * Tag name.
     */
    @Configure
    private String name;

    /**
     * HTTP attribute parameters which are used to retrieve tag value.
     * Attribute consists of prefix and attribute name separated by '.' character.
     * All possible prefixes are listed in {@link CustomTagSource}.
     *
     * Example: <i>session.myAttribute</i>, <i>header.user-agent</i>
     */
    @Configure
    private String attribute;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Retrieves attribute source / prefix.
     * Possible prefixes are listed in {@link CustomTagSource}.
     */
    public String getAttributeSource() {
        return !StringUtils.isEmpty(attribute) && attribute.contains(".") ?
                attribute.substring(0, attribute.indexOf(".")) : "";
    }

    /**
     * Retrieves attribute name.
     */
    public String getAttributeName() {
        return !StringUtils.isEmpty(attribute) && attribute.contains(".") ?
                attribute.substring(attribute.indexOf(".") + 1, attribute.length()) : "";
    }

    @Override
    public String toString() {
        return "CustomTag{" +
                "name='" + name + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
