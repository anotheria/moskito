package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

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
	 * CPU Time by this process.
	 */
	private StatValue processCpuTime;
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
		processCpuTime = StatValueFactory.createStatValue(0L, "processCpuTime", selectedIntervals);
		freePhysicalMemory = StatValueFactory.createStatValue(0L, "freePhysicalMemory", selectedIntervals);
		totalPhysicalMemory = StatValueFactory.createStatValue(0L, "totalPhysicalMemory", selectedIntervals);
		processors = StatValueFactory.createStatValue(0, "processors", selectedIntervals);
		
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
		ret.append(" freemem: ").append(freePhysicalMemory.getValueAsLong(intervalName));
		ret.append(" totalmem: ").append(totalPhysicalMemory.getValueAsLong(intervalName));
		ret.append(" processors: ").append(processors.getValueAsInt(intervalName));
		
		return ret.toString();
	}
	
	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		
		if (valueName==null)
			throw new AssertionError("Value name can't be null");
		valueName = valueName.toLowerCase();

		
		if (valueName.equals("free memory") || valueName.equals("free"))
			return ""+getFreePhysicalMemory(intervalName);
		if (valueName.equals("total memory") || valueName.equals("total"))
			return ""+getTotalPhysicalMemory(intervalName);
		if (valueName.equals("free memory mb") || valueName.equals("free mb"))
			return ""+getFreePhysicalMemory(intervalName)/MB;
		if (valueName.equals("total memory mb") || valueName.equals("total mb"))
			return ""+getTotalPhysicalMemory(intervalName)/MB;
		
		if (valueName.equals("openfiles") || valueName.equals("open files"))
			return ""+getOpenFiles(intervalName);
		if (valueName.equals("minopenfiles") || valueName.equals("min open files"))
			return ""+getMinOpenFiles(intervalName);
		if (valueName.equals("maxopenfiles") || valueName.equals("max open files"))
			return ""+getMaxOpenFiles(intervalName);
		if (valueName.equals("maxsupportedopenfiles") || valueName.equals("max supported open files"))
			return ""+getMaxSupportedOpenFiles(intervalName);

		if (valueName.equals("cputime") || valueName.equals("cpu time"))
			return ""+getProcessCPUTime(intervalName);
		if (valueName.equals("processors"))
			return ""+getProcessors(intervalName);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	
	public void update(int anOpenFiles, int aMaxOpenFiles, long aFreePhysicalMemorySize, long aTotalPhysicalMemorySize, long aProcessTime, int aProcessors){
		openFiles.setValueAsInt(anOpenFiles);
		maxOpenFiles.setValueIfGreaterThanCurrentAsInt(anOpenFiles);
		minOpenFiles.setValueIfLesserThanCurrentAsInt(anOpenFiles);
		
		maxSupportedOpenFiles.setValueAsInt(aMaxOpenFiles);
		processCpuTime.setValueAsLong(aProcessTime);
		processors.setValueAsLong(aProcessors);
		
		freePhysicalMemory.setValueAsLong(aFreePhysicalMemorySize);
		totalPhysicalMemory.setValueAsLong(aTotalPhysicalMemorySize);
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
	
	public int getProcessors(String intervalName){
		return processors.getValueAsInt(intervalName);
	}
	
	public long getFreePhysicalMemory(String intervalName){
		return freePhysicalMemory.getValueAsLong(intervalName);
	}
	
	public long getTotalPhysicalMemory(String intervalName){
		return totalPhysicalMemory.getValueAsLong(intervalName);
	}

}
