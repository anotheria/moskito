package net.anotheria.moskito.core.config.errorhandling;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * If true *all* errors will be logged into a separate slf4j logger. If you want to log a specific error, use a catcher. Default false.
	 */
	@Configure private boolean logErrors = false;

	@SerializedName("@catchers")
	@Configure private ErrorCatcherConfig[] catchers = new ErrorCatcherConfig[0];

	/**
	 * Number of errors an memory catcher should held.
 	 */
	@Configure private int catchersMemoryErrorLimit = 50;


	private transient Map<String, ErrorCatcherConfig> catcherCache = new HashMap<>();

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

	public boolean isLogErrors() {
		return logErrors;
	}

	public void setLogErrors(boolean logErrors) {
		this.logErrors = logErrors;
	}

	@Override
	public String toString(){
		return "autoChartErrors: "+isAutoChartErrors()+", autoChartErrorsInterval: " + getAutoChartErrorsInterval();
	}

	public ErrorCatcherConfig[] getCatchers() {
		return catchers;
	}

	public void setCatchers(ErrorCatcherConfig[] catchers) {
		this.catchers = catchers;
	}

	@AfterConfiguration public void afterConfiguration(){
		Map<String, ErrorCatcherConfig> newCatcherCache = new HashMap<>();
		if (catchers != null && catchers.length>0) {
			for (ErrorCatcherConfig config : catchers) {
				newCatcherCache.put(config.getClazz(), config);
			}
		}
		catcherCache = newCatcherCache;
	}

	public ErrorCatcherConfig getCatcherConfig(String name) {
		return catcherCache.get(name);
	}

	public int getCatchersMemoryErrorLimit() {
		return catchersMemoryErrorLimit;
	}

	public void setCatchersMemoryErrorLimit(int catchersMemoryErrorLimit) {
		this.catchersMemoryErrorLimit = catchersMemoryErrorLimit;
	}
}
