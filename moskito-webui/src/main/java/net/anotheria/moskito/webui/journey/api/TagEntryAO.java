package net.anotheria.moskito.webui.journey.api;

import java.io.Serializable;

/**
 * This bean contains info about tag.
 *
 * @author lrosenberg
 * @since 22.05.17 22:53
 */
public class TagEntryAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -7151866583428865L;
	/**
	 * Name of the tag.
	 */
	private String tagName;
	/**
	 * Value of the tag.
	 */
	private String tagValue;

	public TagEntryAO(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public String toString(){
		return new StringBuilder(tagName).append(": ").append(tagValue).toString();
	}
}
