package net.anotheria.moskito.core.config.tagging;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * User defined tag.
 * @author strel
 */
@ConfigureMe
public class CustomTag {

    /**
     * Tag name.
     */
    @Configure
    private String name;

    /**
     * Tag value 'container' name (for example session or request attribute).
     * All possible prefixes are listed in {@link TagPrefix}.
     */
    @Configure
    private String prefix;

    /**
     * Attribute name / key.
     * Used to retrieve tag value.
     */
    @Configure
    private String attributeName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return "CustomTag{" +
                "name='" + name + '\'' +
                ", prefix=" + prefix +
                ", attributeName='" + attributeName + '\'' +
                '}';
    }
}
