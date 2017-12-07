package net.anotheria.moskito.webui.tags.bean;

import net.anotheria.moskito.core.tag.TagType;

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
     * {@link TagType}.
     */
    private TagType type;

    /**
     * Source for tag value.
     */
    private String source;

    /**
     * List of last tag values.
     */
    private List<String> lastValues;


    public TagBean() {
        lastValues = new ArrayList<>();
    }

    public TagBean(String name, TagType type, String source, List<String> lastValues) {
        this.name = name;
        this.type = type;
        this.source = source;
        this.lastValues = lastValues;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getLastValues() {
        return lastValues;
    }

    public void setLastValues(List<String> lastValues) {
        this.lastValues = lastValues;
    }

    public String getLastValuesTruncatedString() {
        StringBuilder result = new StringBuilder();

        if (lastValues == null || lastValues.isEmpty()) {
            return "";
        }

        // Appending values while length is less then 100
        for (String value : lastValues) {
            if (result.length() < 100) {
                result.append(value);
                result.append(' ');
            }
        }

        // If length of string exceeds 100, truncate it
        if (result.length() >= 100) {
            result.setLength(97);
            result.append("...");
        }

        return result.toString();
    }
}
