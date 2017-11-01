package net.anotheria.moskito.webui.tags.bean;

import net.anotheria.moskito.core.config.tagging.TagType;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom tag bean.
 *
 * @author strel
 */
public class TagBean {

    /**
     * Tag name.
     */
    private String name;

    /**
     * {@link TagType}, i.e. source for tag value.
     */
    private TagType type;

    /**
     * Attribute name, used to retrieve tag value.
     */
    private String attributeName;

    /**
     * List of last tag values, retrieved from {@link net.anotheria.moskito.core.tag.TagHistory}.
     */
    private List<String> lastAttributeValues;


    public TagBean() {
        lastAttributeValues = new ArrayList<>();
    }

    public TagBean(String name, TagType type, String attributeName, List<String> lastAttributeValues) {
        this.name = name;
        this.type = type;
        this.attributeName = attributeName;
        this.lastAttributeValues = lastAttributeValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<String> getLastAttributeValues() {
        return lastAttributeValues;
    }

    public void setLastAttributeValues(List<String> lastAttributeValues) {
        this.lastAttributeValues = lastAttributeValues;
    }
}
