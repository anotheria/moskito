package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats object for operation system related values.
 * @author lrosenberg
 *
 */
public class OSStats extends AbstractStats {
	/**
	 * Current open files amount.
	 */
	private StatValue openFiles;
	/**
	 * Max open files amount.
	 */
	private StatValue maxOpenFiles; 
	/**
	 * Min open files amount.
	 */
	private StatValue minOpenFiles; 
	/**
	 * Max open files supported by the system. This value is never changed.
	 */
	private StatValue maxSupportedOpenFiles;
	/**
	 * Relative CPU Time by this process (since last interval).
	 */
	private StatValue processCpuTime;
	/**
	 * Total CPU Time by this process.
	 */
	private StatValue processTotalCpuTime;

	/**
	 * Selfexplaining.
	 */
	private StatValue freePhysicalMemory;
	/**
	 * Selfexplaining.
	 */
	private StatValue totalPhysicalMemory;
	/**
	 * Number of processors, this is usually a constant.
	 */
	private StatValue processors;

	/**
	 * Process CPU Load in Percent.
	 */
	private StatValue processCpuLoad;

	/**
	 * System CPU Load in Percent.
	 */
	private StatValue systemCpuLoad;
	

	public OSStats(){
		this("OS", Constants.getDefaultIntervals());
	} 
	
	public OSStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	} 

	public OSStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		openFiles = StatValueFactory.createStatValue(0, "openFiles", selectedIntervals);
		maxOpenFiles = StatValueFactory.createStatValue(0, "maxOpenFiles", selectedIntervals); 
		maxOpenFiles.setDefaultValueAsLong(Integer.MIN_VALUE);
		maxOpenFiles.reset();
		minOpenFiles = StatValueFactory.createStatValue(0, "minOpenFiles", selectedIntervals); 
		minOpenFiles.setDefaultValueAsLong(Integer.MAX_VALUE);
		minOpenFiles.reset();

		maxSupportedOpenFiles = StatValueFactory.createStatValue(0, "minOpenFiles", selectedIntervals);
		processCpuTime = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "processCpuTime", selectedIntervals);
		processTotalCpuTime = StatValueFactory.createStatValue(0L, "processTotalCpuTime", selectedIntervals);
		freePhysicalMemory = StatValueFactory.createStatValue(0L, "freePhysicalMemory", selectedIntervals);
		totalPhysicalMemory = StatValueFactory.createStatValue(0L, "totalPhysicalMemory", selectedIntervals);
		processors = StatValueFactory.createStatValue(0, "processors", selectedIntervals);

		processCpuLoad = StatValueFactory.createStatValue(0d, "processCpuLoad", selectedIntervals);
		systemCpuLoad = StatValueFactory.createStatValue(0d, "systemCpuLoad", selectedIntervals);

		addStatValues(openFiles, maxOpenFiles, minOpenFiles, maxSupportedOpenFiles, processCpuTime, processTotalCpuTime, freePhysicalMemory, totalPhysicalMemory, processors, processCpuLoad, systemCpuLoad);
		
	}

	@Override
	public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder();
		
		ret.append(getName()).append(' ');
		ret.append(" openfiles: ").append(openFiles.getValueAsInt(intervalName));
		ret.append(" maxopenfiles: ").append(openFiles.getValueAsInt(intervalName));
		ret.append(" minopenfiles: ").append(openFiles.getValueAsInt(intervalName));
		ret.append(" maxallowedopenfiles: ").append(maxSupportedOpenFiles.getValueAsInt(intervalName));
		ret.append(" cputime: ").append(processCpuTime.getValueAsLong(intervalName));
		ret.append(" totalcputime: ").append(processTotalCpuTime.getValueAsLong(intervalName));
		ret.append(" freemem: ").append(freePhysicalMemory.getValueAsLong(intervalName));
		ret.append(" totalmem: ").append(totalPhysicalMemory.getValueAsLong(intervalName));
		ret.append(" processors: ").append(processors.getValueAsInt(intervalName));
		ret.append(" processcpuload: "+processCpuLoad.getValueAsDouble(intervalName));
		ret.append(" systemcpuload: "+systemCpuLoad.getValueAsDouble(intervalName));
		
		return ret.toString();
	}
	
	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		
		if (valueName==null)
			throw new AssertionError("Value name can't be null");
		valueName = valueName.toLowerCase();

		if (valueName.equals("free memory") || valueName.equals("free"))
			return String.valueOf(getFreePhysicalMemory(intervalName));
		if (valueName.equals("total memory") || valueName.equals("total"))
			return String.valueOf(getTotalPhysicalMemory(intervalName));
		if (valueName.equals("free memory mb") || valueName.equals("free mb"))
			return String.valueOf(getFreePhysicalMemory(intervalName) / MB);
		if (valueName.equals("total memory mb") || valueName.equals("total mb"))
			return String.valueOf(getTotalPhysicalMemory(intervalName) / MB);
		
		if (valueName.equals("openfiles") || valueName.equals("open files"))
			return String.valueOf(getOpenFiles(intervalName));
		if (valueName.equals("minopenfiles") || valueName.equals("min open files"))
			return String.valueOf(getMinOpenFiles(intervalName));
		if (valueName.equals("maxopenfiles") || valueName.equals("max open files"))
			return String.valueOf(getMaxOpenFiles(intervalName));
		if (valueName.equals("maxsupportedopenfiles") || valueName.equals("max supported open files"))
			return String.valueOf(getMaxSupportedOpenFiles(intervalName));

		if (valueName.equals("cputime") || valueName.equals("cpu time"))
			return String.valueOf(getProcessCPUTime(intervalName));
		if (valueName.equals("total cputime") || valueName.equals("total cpu time") || valueName.equals("totalcputime") )
			return String.valueOf(getProcessTotalCPUTime(intervalName));
		if (valueName.equals("processors"))
			return String.valueOf(getProcessors(intervalName));

		if (valueName.equals("processload") || valueName.equals("processcpuload"))
			return String.valueOf(getProcessCpuLoad(intervalName));
		if (valueName.equals("systemload") || valueName.equals("systemcpuload"))
			return String.valueOf(getSystemCpuLoad(intervalName));


		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"FREE",
			"TOTAL",
			"FREE MB",
			"TOTAL MB",
			"Open Files",
			"Min Open Files",
			"Max Open Files",
			"Map supported Open Files",
			"CPU TIME",
			"Total CPU TIME",
			"Processors",
			"ProcessCPULoad",
			"SystemCPULoad"
	));

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}



	public void update(int anOpenFiles, int aMaxOpenFiles, long aFreePhysicalMemorySize, long aTotalPhysicalMemorySize, long aProcessTime, int aProcessors, double aProcessCpuLoad, double aSystemCpuLoad){
		openFiles.setValueAsInt(anOpenFiles);
		maxOpenFiles.setValueIfGreaterThanCurrentAsInt(anOpenFiles);
		minOpenFiles.setValueIfLesserThanCurrentAsInt(anOpenFiles);
		
		maxSupportedOpenFiles.setValueAsInt(aMaxOpenFiles);
		processTotalCpuTime.setValueAsLong(aProcessTime);
		processCpuTime.setValueAsLong(aProcessTime);
		processors.setValueAsLong(aProcessors);
		
		freePhysicalMemory.setValueAsLong(aFreePhysicalMemorySize);
		totalPhysicalMemory.setValueAsLong(aTotalPhysicalMemorySize);

		//convert to percent and set.
		processCpuLoad.setValueAsDouble(toPercent(aProcessCpuLoad));
		systemCpuLoad.setValueAsDouble(toPercent(aSystemCpuLoad));
	}

	private double toPercent(double val){
		return (double)((int)(val * 10000))/100;
	}
	
	public int getOpenFiles(String intervalName){
		return openFiles.getValueAsInt(intervalName);
	}
	
	public int getMaxOpenFiles(String intervalName){
		return maxOpenFiles.getValueAsInt(intervalName);
	}
	
	public int getMinOpenFiles(String intervalName){
		return minOpenFiles.getValueAsInt(intervalName);
	}
	
	public int getMaxSupportedOpenFiles(String intervalName){
		return maxSupportedOpenFiles.getValueAsInt(intervalName);
	}
	
	public long getProcessCPUTime(String intervalName){
		return processCpuTime.getValueAsLong(intervalName);
	}

	public long getProcessTotalCPUTime(String intervalName){
		return processTotalCpuTime.getValueAsLong(intervalName);
	}

	public int getProcessors(String intervalName){
		return processors.getValueAsInt(intervalName);
	}
	
	public long getFreePhysicalMemory(String intervalName){
		return freePhysicalMemory.getValueAsLong(intervalName);
	}
	
	public long getTotalPhysicalMemory(String intervalName){
		return totalPhysicalMemory.getValueAsLong(intervalName);
	}

	public double getProcessCpuLoad(String intervalName){
		return processCpuLoad.getValueAsDouble(intervalName);
	}
	public double getSystemCpuLoad(String intervalName){
		return systemCpuLoad.getValueAsDouble(intervalName);
	}

}
