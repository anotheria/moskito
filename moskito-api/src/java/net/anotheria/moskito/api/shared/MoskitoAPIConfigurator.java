package net.anotheria.moskito.api.shared;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Moskito API tier configuration utility.
 * 
 * @author Alexandr Bolbat
 */
public final class MoskitoAPIConfigurator {

	/**
	 * Logback configuration file name.
	 */
	private static final String LOGBACK_CONFIGURATION_FILE = "logback.xml";

	/**
	 * Private constructor for preventing instance creation.
	 */
	private MoskitoAPIConfigurator() {
		throw new IllegalAccessError();
	}

	/**
	 * Configure API tier.
	 */
	public static synchronized void configure() {
		// logger configuration
		try {
			configureLogback();
		} catch (JoranException e) {
			throw new RuntimeException("configure() configuring logger fail.", e);
		}
	}

	/**
	 * Configure logger for API tier.
	 * 
	 * @throws JoranException
	 */
	private static void configureLogback() throws JoranException {
		LoggerContext lc = LoggerContext.class.cast(LoggerFactory.getILoggerFactory());
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		configurator.doConfigure(LOGBACK_CONFIGURATION_FILE);
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
	}

}
