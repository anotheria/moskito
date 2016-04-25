package net.anotheria.moskito.webui.shared.api.filter;

/**
 * A single matcher that can be used in filters.
 *
 * @author lrosenberg
 * @since 20.04.15 09:22
 */
public interface Matcher {
	boolean doesMatch(String target);
}
