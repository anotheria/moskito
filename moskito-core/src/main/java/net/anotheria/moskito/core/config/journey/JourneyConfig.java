package net.anotheria.moskito.core.config.journey;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration object which
 *
 * @author lrosenberg
 * @since 22.04.16 18:08
 */
@ConfigureMe
public class JourneyConfig implements Serializable{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2366619430532796926L;

	/**
	 * Limit for the parameter length. Default is 100. If parameter length is more than configured, it will be cut. This also applies to return values.
	 */
	@Configure
	private int parameterLengthLimit = 100;

	/**
	 * If true collections are "toStringed", if false, only size is shown. Default true.
	 */
	@Configure
	private boolean toStringCollections = true;

	/**
	 * If true maps are "toStringed", if false, only size is shown. Default true.
	 */
	@Configure
	private boolean toStringMaps = true;

	@Configure
	private String[] exclusionURLs = new String[0];

	@Configure
	private String[] exclusionURLPrefixes = new String[0];

	@Configure
	private String[] exclusionURLSuffixes = new String[0];

	public int getParameterLengthLimit() {
		return parameterLengthLimit;
	}

	public void setParameterLengthLimit(int parameterLengthLimit) {
		this.parameterLengthLimit = parameterLengthLimit;
	}

	public boolean isToStringCollections() {
		return toStringCollections;
	}

	public void setToStringCollections(boolean toStringCollections) {
		this.toStringCollections = toStringCollections;
	}

	public boolean isToStringMaps() {
		return toStringMaps;
	}

	public void setToStringMaps(boolean toStringMaps) {
		this.toStringMaps = toStringMaps;
	}

	public String[] getExclusionURLs() {
		return exclusionURLs;
	}

	public void setExclusionURLs(String[] exclusionURLs) {
		this.exclusionURLs = exclusionURLs;
	}

	public String[] getExclusionURLPrefixes() {
		return exclusionURLPrefixes;
	}

	public void setExclusionURLPrefixes(String[] exclusionURLPrefixes) {
		this.exclusionURLPrefixes = exclusionURLPrefixes;
	}

	public String[] getExclusionURLSuffixes() {
		return exclusionURLSuffixes;
	}

	public void setExclusionURLSuffixes(String[] exclusionURLSuffixes) {
		this.exclusionURLSuffixes = exclusionURLSuffixes;
	}

	@Override
	public String toString() {
		return "JourneyConfig{" +
				"parameterLengthLimit=" + parameterLengthLimit +
				", toStringCollections=" + toStringCollections +
				", toStringMaps=" + toStringMaps +
				", exclusionURLs=" + Arrays.toString(exclusionURLs) +
				", exclusionURLPrefixes=" + Arrays.toString(exclusionURLPrefixes) +
				", exclusionURLSuffixes=" + Arrays.toString(exclusionURLSuffixes) +
				'}';
	}
}
