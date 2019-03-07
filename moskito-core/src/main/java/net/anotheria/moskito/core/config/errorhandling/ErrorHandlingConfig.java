package net.anotheria.moskito.core.config.errorhandling;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.errorhandling.BuiltInErrorProducer;
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

	/**
	 * If true *all* errors will be logged into a separate slf4j logger. If you want to log a specific error, use a catcher. Default false.
	 */
	@Configure private boolean logErrors = false;

	@SerializedName("@catchers")
	@Configure private ErrorCatcherConfig[] catchers = new ErrorCatcherConfig[0];

	/**
	 * The default catchers are applied to all exceptions.
	 */
	@SerializedName("@defaultCatchers")
	@Configure private ErrorCatcherConfig[] defaultCatchers = new ErrorCatcherConfig[0];


	/**
	 * Number of errors an memory catcher should held.
 	 */
	@Configure private int catchersMemoryErrorLimit = 50;

	/**
	 * Count rethrows of a an error. This can cause a potential memory leak if you have many threads and do not cleanup MoSKitoContext afterwards, use with care.
	 * Default: disabled.
	 */
	@Configure private boolean countRethrows = false;


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

	//this should be called by configureme, but we call it manually instead to ensure the top object has been configured.
	public void afterConfiguration(){
		BuiltInErrorProducer.getInstance().afterConfiguration(this);
	}

	public int getCatchersMemoryErrorLimit() {
		return catchersMemoryErrorLimit;
	}

	public void setCatchersMemoryErrorLimit(int catchersMemoryErrorLimit) {
		this.catchersMemoryErrorLimit = catchersMemoryErrorLimit;
	}

	public boolean isCountRethrows() {
		return countRethrows;
	}

	public void setCountRethrows(boolean countRethrows) {
		this.countRethrows = countRethrows;
	}

	public ErrorCatcherConfig[] getDefaultCatchers() {
		return defaultCatchers;
	}

	public void setDefaultCatchers(ErrorCatcherConfig[] defaultCatchers) {
		this.defaultCatchers = defaultCatchers;
	}
}
