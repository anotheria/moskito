package net.anotheria.moskito.core.config.errorhandling;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration for the BuiltInErrorProducer.
 *
 * @author lrosenberg
 * @since 02.06.17 14:13
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way ConfigureMe works, it provides beans for access")
public class ErrorHandlingConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Should errors be auto-charted. Auto charted means that for every exception/error/throwable type a new accumulator will be created. Default is false.
	 */
	@Configure private boolean autoChartErrors = true;

	/**
	 * If autoChartErrors is enabled, this setting will provide the interval that will be used.
	 */
	@Configure private String autoChartErrorsInterval = "1m";

	public boolean isAutoChartErrors() {
		return autoChartErrors;
	}

	public void setAutoChartErrors(boolean autoChartErrors) {
		this.autoChartErrors = autoChartErrors;
	}

	public String getAutoChartErrorsInterval() {
		return autoChartErrorsInterval;
	}

	public void setAutoChartErrorsInterval(String autoChartErrorsInterval) {
		this.autoChartErrorsInterval = autoChartErrorsInterval;
	}

	@Override
	public String toString(){
		return "autoChartErrors: "+isAutoChartErrors()+", autoChartErrorsInterval: " + getAutoChartErrorsInterval();
	}
}
