package net.anotheria.moskito.core.config.tagging;

import java.io.Serializable;

/**
 * Configured tagging options.
 *
 * @author lrosenberg
 * @since 28.09.17 19:21
 */
public class TaggingConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 8060717826503504338L;

	private boolean autotagSessionId = true;

	private boolean autotagIp = true;

	private boolean autotagUserAgent = true;

	private boolean autotagReferer = true;

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

	@Override
	public String toString() {
		return "TaggingConfig{" +
				"autotagSessionId=" + autotagSessionId +
				", autotagIp=" + autotagIp +
				", autotagUserAgent=" + autotagUserAgent +
				", autotagReferer=" + autotagReferer +
				'}';
	}
}
