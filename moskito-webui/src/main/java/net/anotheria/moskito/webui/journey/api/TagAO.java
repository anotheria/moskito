package net.anotheria.moskito.webui.journey.api;

/**
 * This bean contains info about tag.
 *
 * @author lrosenberg
 * @since 22.05.17 22:53
 */
public class TagAO {
	/**
	 * Name of the tag.
	 */
	private String tagName;
	/**
	 * Value of the tag.
	 */
	private String tagValue;

	public TagAO(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}

	public TagAO(){

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
