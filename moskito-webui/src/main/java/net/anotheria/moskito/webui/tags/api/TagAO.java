package net.anotheria.moskito.webui.tags.api;

import net.anotheria.moskito.core.tag.TagType;

import java.io.Serializable;
import java.util.List;

/**
 * Tag api object.
 *
 * @author esmakula
 */
public class TagAO implements Serializable {

	private static final long serialVersionUID = -7122373275171387195L;
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

	@Override
	public String toString() {
		return "TagAO{" +
				"name='" + name + '\'' +
				", type=" + type +
				", source='" + source + '\'' +
				", lastValues=" + lastValues +
				'}';
	}
}
