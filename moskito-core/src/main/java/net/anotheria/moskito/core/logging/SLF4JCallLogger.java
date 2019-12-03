package net.anotheria.moskito.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 2019-09-17 16:21
 */
public class SLF4JCallLogger implements CallLogger{

	private static Logger log = LoggerFactory.getLogger("MoSKitoCalls");

	@Override
	public void callStarted(String callDescription) {

	}

	@Override
	public void callEnded(String callDescription, long callDuration) {
		//log.info(MoSKitoContext.get().callDescription+ " --> "+callDuration);
		//MoSKitoContext.get()
	}
}
