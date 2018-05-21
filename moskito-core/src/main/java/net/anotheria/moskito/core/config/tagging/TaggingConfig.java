package net.anotheria.moskito.core.config.tagging;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
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
	 * Add url as tag
	 */
	@Configure
	private boolean autotagUrl = true;

	/**
	 * Add server name as tag
	 */
	@Configure
	private boolean autotagServerName = true;

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

	public boolean isAutotagUrl() {
		return autotagUrl;
	}

	public void setAutotagUrl(boolean autotagUrl) {
		this.autotagUrl = autotagUrl;
	}

	public boolean isAutotagServerName() {
		return autotagServerName;
	}

	public void setAutotagServerName(boolean autotagServerName) {
		this.autotagServerName = autotagServerName;
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
				", autotagUrl=" + autotagUrl +
				", autotagServerName=" + autotagServerName +
				", customTags=" + Arrays.toString(customTags) +
				", tagHistorySize=" + tagHistorySize +
				'}';
	}
}
