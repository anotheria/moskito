package net.anotheria.moskito.core.config.tagging;

import com.google.gson.annotations.SerializedName;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configured tagging options.
 *
 * @author lrosenberg
 * @since 28.09.17 19:21
 */
@ConfigureMe
public class TaggingConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 8060717826503504338L;

	/**
	 * If true and the session exists the session id is automatically assigned to a tag.
	 */
	@Configure
	private boolean autotagSessionId = true;

	/**
	 * Add remote ip to the tag.
	 */
	@Configure
	private boolean autotagIp = true;


	/**
	 * Add user agent as tag.
	 */
	@Configure
	private boolean autotagUserAgent = true;

	/**
	 * Add the referer as tag.
	 */
	@Configure
	private boolean autotagReferer = true;

	/**
	 * User defined tags.
	 */
	@Configure
	@SerializedName("@customTags")
	private CustomTag[] customTags = new CustomTag[] {};

	/**
	 * Maximum tag history size.
	 */
	@Configure
	private int tagHistorySize = 10;

	public boolean isAutotagSessionId() {
		return autotagSessionId;
	}

	public void setAutotagSessionId(boolean autotagSessionId) {
		this.autotagSessionId = autotagSessionId;
	}

	public boolean isAutotagIp() {
		return autotagIp;
	}

	public void setAutotagIp(boolean autotagIp) {
		this.autotagIp = autotagIp;
	}

	public boolean isAutotagUserAgent() {
		return autotagUserAgent;
	}

	public void setAutotagUserAgent(boolean autotagUserAgent) {
		this.autotagUserAgent = autotagUserAgent;
	}

	public boolean isAutotagReferer() {
		return autotagReferer;
	}

	public void setAutotagReferer(boolean autotagReferer) {
		this.autotagReferer = autotagReferer;
	}

	public CustomTag[] getCustomTags() {
		return customTags;
	}

	public void setCustomTags(CustomTag[] customTags) {
		this.customTags = customTags;
	}

	public int getTagHistorySize() {
		return tagHistorySize;
	}

	public void setTagHistorySize(int tagHistorySize) {
		this.tagHistorySize = tagHistorySize;
	}

	@Override
	public String toString() {
		return "TaggingConfig{" +
				"autotagSessionId=" + autotagSessionId +
				", autotagIp=" + autotagIp +
				", autotagUserAgent=" + autotagUserAgent +
				", autotagReferer=" + autotagReferer +
				", customTags=" + Arrays.toString(customTags) +
				", tagHistorySize=" + tagHistorySize +
				'}';
	}
}
