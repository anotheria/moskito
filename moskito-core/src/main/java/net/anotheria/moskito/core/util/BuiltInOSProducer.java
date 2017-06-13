package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Builtin producer for values supplied by jmx for the operation system.
 * @author lrosenberg
 */
public class BuiltInOSProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer{
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

	/**
	 * If true indicates.
	 */
	private final boolean isUnixOS;
	
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(BuiltInOSProducer.class);
	
	public BuiltInOSProducer(){
		mxBean = ManagementFactory.getOperatingSystemMXBean();
		statsList = new ArrayList<IStats>(1);
		stats = new OSStats();
		statsList.add(stats);
		
		try{
			clazz = Class.forName(clazzname);
		}catch(ClassNotFoundException e){
			log.warn("Couldn't find unix version of os class: "+clazzname+", osstats won't operate properly - "+e.getMessage());
		}

		char version=System.getProperty("java.version").charAt(2);

		if (version > '7'){
			isUnixOS = mxBean.getClass().getName().equals("sun.management.OperatingSystemImpl");
		}else{
			isUnixOS = mxBean.getClass().getName().equals("com.sun.management.UnixOperatingSystem");
		}
		
        if (!isUnixOS) {
            log.warn("Couldn't find unix version of os class: " + clazzname
                    + ", osstats won't operate properly. Current type is: "
                    + mxBean.getClass().getName());
        }

		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readMbean();
			}});

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
		if (clazz==null){
			return;
		}
		try{
			if (isUnixOS) {
				long openFiles = getValue("OpenFileDescriptorCount");
				long maxOpenFiles = getValue("MaxFileDescriptorCount");
				
				long freePhysicalMemorySize = getValue("FreePhysicalMemorySize");
				long totalPhysicalMemorySize = getValue("TotalPhysicalMemorySize");

				long processTime = getValue("ProcessCpuTime");

				double processCPULoad = getDoubleValue("ProcessCpuLoad");
				double systemCPULoad  = getDoubleValue( "SystemCpuLoad");

				long processors = getValue("AvailableProcessors");
				
				stats.update((int)openFiles, (int)maxOpenFiles, freePhysicalMemorySize, totalPhysicalMemorySize, processTime, (int)processors, processCPULoad, systemCPULoad);

			} else {
				long processors = getValue("AvailableProcessors");

				stats.update(-1, -1, -1, -1, -1, (int)processors, -1, -1);
			}
			
		}catch(Exception e){
			log.warn("Couldn't read value due to",e);
		}
	}
	
	private long getValue(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		Method m = clazz.getMethod("get"+name);
		if (name.equals("AvailableProcessors"))
			return (Integer)m.invoke(mxBean);
		Long result = (Long)m.invoke(mxBean);
		return result;
	}

	private double getDoubleValue(String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		Method m = clazz.getMethod("get"+name);
		Double result = (Double)m.invoke(mxBean);
		return result;
	}

	public static void main(String[] a){
		new BuiltInOSProducer();
	}

}
