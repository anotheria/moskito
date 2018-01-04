package net.anotheria.moskito.webui.tags.bean;

import net.anotheria.moskito.core.tag.TagType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private Map<String, Integer> lastValues;

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

	public void setLastValues(Map<String, Integer> lastValues) {
		this.lastValues = lastValues;
	}

	public List<String> getLastValues() {
		List<String> values = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : lastValues.entrySet()) {
			values.add(String.format("%s (%d)", entry.getKey(), entry.getValue()));
		}
		return values;
	}

}
