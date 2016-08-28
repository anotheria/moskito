package net.anotheria.moskito.aop.aspect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.slf4j.LoggerFactory;

/**
 * Common configuration for aspects.
 *
 * @author sshscp
 */
@ConfigureMe (name = "moskito-aspect-config")
public final class MoskitoAspectConfiguration implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -7486675881930422282L;
	/**
	 * Aspect intervalLogger configuration
	 */
	@Configure
	private AspectLoggerConfigHolder intervalLogger = new AspectLoggerConfigHolder();

	/**
	 * Fetch {@link MoskitoAspectConfiguration} instance.
	 *
	 * @return {@link MoskitoAspectConfiguration}
	 */
	public static MoskitoAspectConfiguration getInstance() {
		return MoskitoConfigurationHolder.CONFIG.getInstance();
	}


	/**
	 * Inner init.
	 */
	@AfterConfiguration
	public synchronized void init() {
		intervalLogger.init();
	}

	/**
	 * Return logger name for default stats.
	 *
	 * @return default stats logger name
	 */
	public String getDefaultMoskitoLoggerName() {
		return intervalLogger.getDefaultMoskitoLoggerName();
	}

	/**
	 * Return {@code true} in case if default stats loggers should be initialised, {@code false} otherwise
	 *
	 * @return boolean value
	 */
	public boolean isAttachDefaultStatLoggers() {
		return intervalLogger.isAttachDefaultStatLoggers();
	}


	/**
	 * Fetch logger name for selected interval.
	 *
	 * @param intervalName
	 * 		name of the interval. (e.g 1m, 15m etc)
	 * @return logger name or {@code null}
	 */
	public String getMoskitoLoggerName(final String intervalName) {
		return intervalLogger.getMoskitoLoggerName(intervalName);
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public AspectLoggerConfigHolder getIntervalLogger() {
		return intervalLogger;
	}

	public void setIntervalLogger(AspectLoggerConfigHolder intervalLogger) {
		if (intervalLogger == null)
			return;
		this.intervalLogger = intervalLogger;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("MoskitoAspectConfiguration{");
		sb.append("intervalLogger='").append(intervalLogger).append('\'');
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Config holder facility.
	 */
	private enum MoskitoConfigurationHolder {
		/**
		 * Defaults.
		 */
		CONFIG;
		/**
		 * Configuration instance.
		 */
		private final MoskitoAspectConfiguration instance;

		/**
		 * Common with configuration.
		 */
		MoskitoConfigurationHolder() {
			instance = new MoskitoAspectConfiguration();
			try {
				ConfigurationManager.INSTANCE.configure(instance);
			} catch (final RuntimeException e) {
				LoggerFactory.getLogger(MoskitoConfigurationHolder.class).info("Failed to configure! Relying on defaults! [" + e.getMessage() + "]");
			}
		}

		public MoskitoAspectConfiguration getInstance() {
			return instance;
		}
	}


	/**
	 * Provides stats logger configuration in interval scopes.
	 */
	public static class AspectLoggerConfigHolder implements Serializable {
		/**
		 * SerialVersionUID.
		 */
		private static final long serialVersionUID = -7973226041037384967L;
		/**
		 * Default logger name prefix.
		 */
		@DontConfigure
		private static final String DEFAULT_MOSKITO_LOG_PREFIX = "Moskito";
		/**
		 * Default interval logger name suffix.
		 */
		@DontConfigure
		private static final String DEFAULT_INTERVAL_SUFFIX = "Default";

		/**
		 * Default interval logger name.
		 * With default initializations.
		 */
		@Configure
		private String defaultMoskitoLoggerName = DEFAULT_MOSKITO_LOG_PREFIX + DEFAULT_INTERVAL_SUFFIX;

		/**
		 * Enable - disable default stat intervalLogger init.
		 */
		@Configure
		private boolean attachDefaultStatLoggers = false;

		/**
		 * Logger configuration.
		 */
		@Configure
		private LoggerConfig[] loggers;

		/**
		 * Loggers configuration storage.
		 */
		@DontConfigure
		private volatile Map<String, LoggerConfig> loggersStorage = new HashMap<>();


		/**
		 * Performs initialization job.
		 */
		void init() {
			final Map<String, LoggerConfig> data = new HashMap<>();
			if (loggers == null || loggers.length == 0) {
				loggersStorage = data;
				return;
			}

			for (final LoggerConfig cnf : loggers)
				if (cnf != null && !StringUtils.isEmpty(cnf.getIntervalName()) && !StringUtils.isEmpty(cnf.getLoggerName()))
					data.put(cnf.getIntervalName(), cnf);

			loggersStorage = data;
		}

		public String getDefaultMoskitoLoggerName() {
			return defaultMoskitoLoggerName;
		}

		public void setDefaultMoskitoLoggerName(String defaultMoskitoLoggerName) {
			if (StringUtils.isEmpty(defaultMoskitoLoggerName))
				return;
			this.defaultMoskitoLoggerName = defaultMoskitoLoggerName;
		}

		public boolean isAttachDefaultStatLoggers() {
			return attachDefaultStatLoggers;
		}

		public void setAttachDefaultStatLoggers(boolean attachDefaultStatLoggers) {
			this.attachDefaultStatLoggers = attachDefaultStatLoggers;
		}

		public LoggerConfig[] getLoggers() {
			return loggers;
		}

		public void setLoggers(LoggerConfig[] loggers) {
			if (loggers != null)
				this.loggers = loggers;
		}

		/**
		 * Fetch logger name for selected interval.
		 *
		 * @param intervalName
		 * 		name of the interval. (e.g 1m, 15m etc)
		 * @return logger name or {@code null}
		 */
		public String getMoskitoLoggerName(final String intervalName) {
			if (StringUtils.isEmpty(intervalName))
				return null;
			final LoggerConfig config = loggersStorage.get(intervalName);
			return config == null ? DEFAULT_MOSKITO_LOG_PREFIX + intervalName : config.getLoggerName();

		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("AspectLoggerConfig{");
			sb.append("defaultMoskitoLoggerName='").append(defaultMoskitoLoggerName).append('\'');
			sb.append(", attachDefaultStatLoggers=").append(attachDefaultStatLoggers);
			sb.append(", intervalLogger=").append(Arrays.toString(loggers));
			sb.append(", loggersStorage=").append(loggersStorage);
			sb.append('}');
			return sb.toString();
		}
	}

	/**
	 * Logger configuration.
	 */
	private static class LoggerConfig implements Serializable {
		/**
		 * SerialVersionUID.
		 */
		private static final long serialVersionUID = 5231263161891368289L;
		/**
		 * Moskito interval name. {@link Interval#getName()}.
		 */
		@Configure
		private String intervalName;
		/**
		 * Logger name for interval.
		 */
		@Configure
		private String loggerName;


		public String getIntervalName() {
			return intervalName;
		}

		public void setIntervalName(String intervalName) {
			this.intervalName = intervalName;
		}

		public String getLoggerName() {
			return loggerName;
		}

		public void setLoggerName(String loggerName) {
			this.loggerName = loggerName;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("LoggerConfig{");
			sb.append("intervalName='").append(intervalName).append('\'');
			sb.append(", loggerName='").append(loggerName).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}
}
