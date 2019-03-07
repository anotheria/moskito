package net.anotheria.moskito.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

/**
 * Builtin producer for values supplied by jmx for the operation system.
 * 
 * @author lrosenberg
 */
public class BuiltInOSProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer {
	/**
	 * Associated stats.
	 */
	private OSStats stats;
	/**
	 * Stats container
	 */
	private List<IStats> statsList;

	/**
	 * The monitored pool.
	 */
	private OperatingSystemMXBean mxBean;

	/**
	 * Name of the mxbean class.
	 */
	private static final String clazzname = "com.sun.management.UnixOperatingSystemMXBean";

	/**
	 * Resolved class of the mx bean.
	 */
	private Class<?> clazz;

	/** The Constant BITS_TO_BYTES_FACTOR. */
	private static final int BITS_TO_BYTES_FACTOR = 1024;

	/**
	 * If true indicates.
	 */
	private final boolean isUnixOS = SystemUtils.IS_OS_UNIX;
	private final boolean isWindowsOS = SystemUtils.IS_OS_WINDOWS;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(BuiltInOSProducer.class);


	public BuiltInOSProducer() {
		mxBean = ManagementFactory.getOperatingSystemMXBean();
		statsList = new ArrayList<IStats>(1);
		stats = new OSStats();
		statsList.add(stats);

		try {
			clazz = Class.forName(clazzname);
		} catch (ClassNotFoundException e) {
			log.warn("Couldn't find unix version of os class: " + clazzname + ", osstats won't operate properly - " + e.getMessage());
		}

		if (!isUnixOS) {
			log.warn("Couldn't find unix version of os class: " + clazzname
					+ ", osstats won't operate properly for all stats. Current type is: "
					+ mxBean.getClass().getName());
		}

		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readMbean();
			}
		});

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);

		Accumulators.setupCPUAccumulators();
	}


	@Override
	public String getCategory() {
		return "os";
	}


	@Override
	public String getProducerId() {
		return "OS";
	}


	@Override
	public List<IStats> getStats() {
		return statsList;
	}


	/**
	 * Reads the management bean and extracts monitored data on regular base.
	 */
	private void readMbean() {
		if (clazz == null) {
			return;
		}
		try {
			if (isUnixOS) {
				long openFiles = getValue("OpenFileDescriptorCount");
				long maxOpenFiles = getValue("MaxFileDescriptorCount");

				long freePhysicalMemorySize = getValue("FreePhysicalMemorySize");
				long totalPhysicalMemorySize = getValue("TotalPhysicalMemorySize");

				long processTime = getValue("ProcessCpuTime");

				double processCPULoad = getDoubleValue("ProcessCpuLoad");
				double systemCPULoad = getDoubleValue("SystemCpuLoad");

				long processors = getValue("AvailableProcessors");

				stats.update((int) openFiles, (int) maxOpenFiles, freePhysicalMemorySize, totalPhysicalMemorySize, processTime, (int) processors, processCPULoad, systemCPULoad);

			} else if (isWindowsOS) {
				long processors = getValue("AvailableProcessors");
				double systemCPULoad = readWindowsCPULoad();

				long totalPhysicalMemorySize = readWindowsTotalMemory();
				long freePhysicalMemorySize = readWindowsFreeMemory();

				stats.update(-1, -1, freePhysicalMemorySize, totalPhysicalMemorySize, -1, (int) processors, -1, systemCPULoad);

			} else {
				long processors = getValue("AvailableProcessors");

				stats.update(-1, -1, -1, -1, -1, (int) processors, -1, -1);
			}

		} catch (Exception e) {
			log.warn("Couldn't read value due to", e);
		}
	}


	private long getValue(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method m = clazz.getMethod("get" + name);
		if (name.equals("AvailableProcessors"))
			return (Integer) m.invoke(mxBean);
		Long result = (Long) m.invoke(mxBean);
		return result;
	}


	private double getDoubleValue(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method m = clazz.getMethod("get" + name);
		Double result = (Double) m.invoke(mxBean);
		return result;
	}


	public static void main(String[] a) {
		new BuiltInOSProducer();
	}


	private static long readWindowsTotalMemory() {
		Long result = 0L;

		try {
			String[] command = "wmic OS get TotalVisibleMemorySize /Value".split(" ");
			String line = executeMemoryInfoProcess(command); // Output should be something like 'TotalVisibleMemorySize=8225260'
			result = Long.parseLong(line.substring(line.indexOf("=") + 1)) * BITS_TO_BYTES_FACTOR; // convert it to bytes
		} catch (Exception e) {
			log.error("unable to get TotalVisibleMemorySize from wmic", e);
		}

		return result.longValue();
	}


	private static long readWindowsFreeMemory() {
		Long result = 0L;

		try {
			String[] command = "wmic OS get FreePhysicalMemory /Value".split(" ");
			String line = executeMemoryInfoProcess(command); // Output should be something like 'FreePhysicalMemory=8225260'
			result = Long.parseLong(line.substring(line.indexOf("=") + 1)) * BITS_TO_BYTES_FACTOR; // convert it to bytes
		} catch (Exception e) {
			log.error("unable to get FreePhysicalMemory from wmic", e);
		}

		return result.longValue();
	}


	private static double readWindowsCPULoad() {
		Double result = 0d;

		try {
			String[] command = "wmic cpu get LoadPercentage /Value".split(" ");
			String line = executeMemoryInfoProcess(command); // Output should be something like 'LoadPercentage=27'
			result = Double.parseDouble(line.substring(line.indexOf("=") + 1)) / 100; // convert 27% to 0.27 to be represented such as unix's SystemCpuLoad
		} catch (Exception e) {
			log.error("unable to get LoadPercentage from wmic", e);
		}

		return result.doubleValue();
	}


	private static String executeMemoryInfoProcess(String... command) throws IOException {
		ProcessBuilder procBuilder = new ProcessBuilder(command);
		Process process = procBuilder.start();

		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}
				return line;
			}
		} catch (IOException e1) {
			throw e1;
		} finally {
			br.close();
		}
		throw new IOException("Could not read memory process output for command " + command);
	}

}
