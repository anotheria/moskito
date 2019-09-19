package net.anotheria.moskito.core.threshold;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.GuardConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.helper.TieableDefinition;
import net.anotheria.moskito.core.helper.TieableRepository;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;
import net.anotheria.moskito.core.util.MBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.List;

/**
 * Repository that contains currently configured thresholds.
 */
public class ThresholdRepository<S extends IStats> extends TieableRepository<Threshold, S> {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(ThresholdRepository.class);

	/**
	 * Singleton instance of this class.
	 */
	private static ThresholdRepository<? extends IStats> INSTANCE = new ThresholdRepository<>();

	/**
	 * Configuration.
	 */
	private MoskitoConfiguration configuration;


	/**
	 * Private constructor.
 	 */
	private ThresholdRepository(){
		readConfig();
	}

	/**
	 * Returns the singleton instance of the registry.
	 * @return
	 */
	public static ThresholdRepository<? extends IStats> getInstance(){
		return INSTANCE;
	}

	protected boolean tie(Threshold threshold, IStatsProducer<? extends IStats> producer){

		ThresholdDefinition definition = threshold.getDefinition();
		IStats target = null;
		for (IStats s : producer.getStats()){
			if (s.getName().equals(definition.getStatName())){
				target = s;
				break;
			}
		}

		if (target==null){
			if (producer instanceof OnDemandStatsProducer){
				addToAutoTie(threshold, producer);
			}else{
				throw new IllegalArgumentException("StatObject not found "+definition.getStatName()+" in "+definition);
			}
		}

		threshold.tieToStats(target);
		
		//if (definition.getIntervalName()!=null){
		//	Interval interval = IntervalRegistry.getInstance().getInterval(definition.getIntervalName());
		//}
		
		return true;
		
	}
	
    /**
     * removes all {@link Threshold} MBeans from platform MBeanServer.
     */
    public void cleanup() {
        List<Threshold> th = getThresholds();
        for (Threshold t : th) {
            final String tName = t.getDefinition().getName();
            try {
            	MBeanUtil.getInstance().unregisterMBean(createName(tName));
            } catch (MBeanRegistrationException e) {
				log.warn("can't unregister " + tName + ", ignored.", e);
			}
		}
        super.cleanup();
    }
	
	private String createName(String name) {
        String appName = MoskitoConfigurationHolder.getConfiguration().getApplicationName();
		return "moskito."+(appName.length()>0 ? appName+ '.' :"")+"thresholds:type="+name;
	}
	
    /**
     * Creates a {@link Threshold} related to given {@link ThresholdDefinition} and registers it as
     * MBean. If there is already such a Bean defined, this method will remove the old MBean and
     * registers the new {@link Threshold} instead.
     * 
     * @param definition
     *            the {@link ThresholdDefinition}
     * @return {@link Threshold}
     */
	public Threshold createThreshold(ThresholdDefinition definition){
        Threshold ret = createTieable(definition);

        try {
	    	// Construct the ObjectName for the MBean we will register
            String name = createName(ret.getDefinition().getName());
            MBeanUtil.getInstance().registerMBean(ret, name, true);
        } catch (MalformedObjectNameException | NotCompliantMBeanException | MBeanRegistrationException e) {
	    	log.warn("can't subscribe threshold to jmx", e);
	    } catch(AccessControlException e){
			log.warn("can't create jmx bean due to access control problems", e);
		}

    	return ret;

	}

	public Threshold createCustomThreshold(String name, CustomThresholdProvider provider, OnDemandStatsProducer<S> producer){
		if (name==null)
			throw new IllegalArgumentException("CustomThreshold name can not be null");
		ThresholdDefinition definition = new ThresholdDefinition();
		definition.setName(name);
		definition.setIntervalName("1m");
		definition.setCustom(true);
		definition.setProducerName(producer.getProducerId());
		
		Threshold threshold = new Threshold(definition);
		threshold.tieToProvider(provider);

		addTieable(threshold);
		attachToListener(threshold);


		try {
			// Construct the ObjectName for the MBean we will register
			String mBeanName = createName(name);
			MBeanUtil.getInstance().registerMBean(threshold, mBeanName, true);
		} catch (MalformedObjectNameException | NotCompliantMBeanException | MBeanRegistrationException e) {
			log.warn("can't subscribe threshold to jmx", e);
		} catch(AccessControlException e){
			log.warn("can't create jmx bean due to access control problems", e);
		}

		return threshold;

	}

	public ExtendedThresholdStatus getExtendedWorstStatus(List<String> includedNames){

		ThresholdStatus status = (includedNames == null || includedNames.size()==0) ?
				getWorstStatus() : getWorstStatus(includedNames);
		ExtendedThresholdStatus ret = new ExtendedThresholdStatus(status);
		for (Threshold t : getThresholds()){
			if (t.getStatus()!=status)
				continue;
			if (includedNames!=null && includedNames.indexOf(t.getName())==-1)
				continue;
			ThresholdInStatus tis = new ThresholdInStatus();
			tis.setThresholdName(t.getName());
			tis.setValue(t.getLastValue());
			ret.add(tis);
		}
		return ret;
	}

	public ExtendedThresholdStatus getExtendedWorstStatusWithout(List<String> excludedNames){
		ThresholdStatus status = getWorstStatusWithout(excludedNames);
		ExtendedThresholdStatus ret = new ExtendedThresholdStatus(status);
		for (Threshold t : getThresholds()){
			if (t.getStatus()!=status)
				continue;
			if (excludedNames!=null && excludedNames.indexOf(t.getName())!=-1)
				continue;
			ThresholdInStatus tis = new ThresholdInStatus();
			tis.setThresholdName(t.getName());
			tis.setValue(t.getLastValue());
			ret.add(tis);
		}
		return ret;
	}


	/**
	 * Returns the worst threshold status in the system.
	 * @return the worst detected threshold status.
	 */
	public ThresholdStatus getWorstStatus(){

		if (configuration.getKillSwitch().disableMetricCollection())
			return ThresholdStatus.OFF;

		ThresholdStatus ret = ThresholdStatus.GREEN;
		for (Threshold t : getThresholds()){
			if (t.getStatus()==null){
				log.error("Threshold "+t+" has status NULL!");
			}else{
				if (t.getStatus().overrules(ret)) {
					ret = t.getStatus();
				}
			}
		}
		return ret;
	}
	
	/**
	 * Returns the worst threshold status in the system for given threshold names.
	 * @return the orst detected threshold status for selected thresholds.
	 */
	public ThresholdStatus getWorstStatus(String[] names){
		return getWorstStatus(Arrays.asList(names));
	}
	
	/**
	 * Returns the worst threshold status in the system for given threshold names.
	 * @return
	 */
	public ThresholdStatus getWorstStatus(List<String> names){
		if (configuration.getKillSwitch().disableMetricCollection())
			return ThresholdStatus.OFF;

		ThresholdStatus ret = ThresholdStatus.GREEN;
		for (Threshold t : getThresholds()){
			if (names.indexOf(t.getName())==-1)
				continue;
			if (t.getStatus().overrules(ret))
				ret = t.getStatus();
		}
		return ret;
		
	}

	/**
	 * Returns the worst threshold status in the system for all thresholds except given threshold names.
	 * @return
	 */
	public ThresholdStatus getWorstStatusWithout(List<String> names){

		if (configuration.getKillSwitch().disableMetricCollection())
			return ThresholdStatus.OFF;

		ThresholdStatus ret = ThresholdStatus.GREEN;
		for (Threshold t : getThresholds()){
			if (names.indexOf(t.getName())!=-1)
				continue;
			if (t.getStatus().overrules(ret))
				ret = t.getStatus();
		}
		return ret;

	}

	/**
	 * Returns active thresholds.
	 * @return
	 */
	public List<Threshold> getThresholds(){
		return getTieables();
	}
	
	/**
	 * Creates a new threshold.
	 */
	protected Threshold create(TieableDefinition def){
		return new Threshold((ThresholdDefinition)def);
	}

	/**
	 * Reads the config and creates configured thresholds. For now this method is only executed on startup.
	 */
	private void readConfig(){
		configuration = MoskitoConfigurationHolder.getConfiguration();
		ThresholdsConfig config = configuration.getThresholdsConfig();
		ThresholdConfig[] tcs = config.getThresholds();
		if (tcs!=null && tcs.length>0){
			for (ThresholdConfig tc  : tcs){
				ThresholdDefinition td = new ThresholdDefinition();
				td.setName(tc.getName());
				td.setIntervalName(tc.getIntervalName());
				td.setProducerName(tc.getProducerName());
				td.setStatName(tc.getStatName());
				td.setTimeUnit(TimeUnit.valueOf(tc.getTimeUnit()));
				td.setValueName(tc.getValueName());
				Threshold newThreshold = createThreshold(td);

				GuardConfig[] guards =  tc.getGuards();
				if (guards!=null && guards.length>0){
					boolean hasDots = false;
					for (GuardConfig guard : guards ) {
						hasDots |= hasDots(guard.getValue());
					}

					for (GuardConfig guard : guards ){
						newThreshold.addGuard(
								hasDots ?
										new DoubleBarrierPassGuard(ThresholdStatus.valueOf(guard.getStatus()),
												Double.parseDouble(guard.getValue()),
												GuardedDirection.valueOf(guard.getDirection()))
										:
										new LongBarrierPassGuard(ThresholdStatus.valueOf(guard.getStatus()),
												Long.parseLong(guard.getValue()),
												GuardedDirection.valueOf(guard.getDirection()))
						);
					}
				}
			}
		}
	}

	/**
	 * This method is for unit testing ONLY.
	 */
	@SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", justification = "This method is only for unit tests.")
	void reset() {
		cleanup();
		INSTANCE = new ThresholdRepository<>();
	}

	/**
	 * This method is for unit testing ONLY.
	 * The Findbugs warning is suppressed, because this method is for unit testing only.
	 */
	@SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", justification = "This method is for unit testing only.")
	public static void resetForUnitTests() {
		getInstance().reset();
	}

	private boolean hasDots(String ... params){
		if (params==null)
			return false;
		for (String p : params){
			if (p!=null && p.indexOf('.')>0)
				return true;
		}
		return false;
	}

}
