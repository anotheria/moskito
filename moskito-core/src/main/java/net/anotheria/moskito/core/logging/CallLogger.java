package net.anotheria.moskito.core.logging;

/**
 * TODO document me !ine
 *
 * @author lrosenberg
 * @since 2019-08-20 15:28
 */
public interface CallLogger {
	void callStarted(String callDescription);

	void callEnded(String callDescription, long callDuration);
}
