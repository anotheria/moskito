package net.anotheria.moskito.core.treshold;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.helper.RuntimeConstants;
import net.anotheria.moskito.core.helper.TieableDefinition;
import net.anotheria.moskito.core.helper.TieableRepository;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import org.apache.log4j.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * Repository that contains currently configured thresholds.
 */
public class ThresholdRepository extends TieableRepository<Threshold> {

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(ThresholdRepository.class);

	/**
	 * Singleton instance of this class.
	 */
	private static ThresholdRepository INSTANCE = new ThresholdRepository();


	/**
	 * Private constructor.
 	 */
	private ThresholdRepository(){
	}

	/**
	 * Returns the singleton instance of the registry.
	 * @return
	 */
	public static ThresholdRepository getInstance(){ return INSTANCE; }

	protected boolean tie(Threshold threshold, IStatsProducer<?> producer){
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
	
	public void cleanup(){
	    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		List<Threshold> th = getThresholds();
		for (Threshold t : th){
			try{
				ObjectName name = createName(t.getDefinition().getName());
		    	mbs.unregisterMBean(name);
			}catch(Exception e){
				log.warn("can't unregister "+t.getDefinition().getName()+", ignored.", e);
			}
			
		}
	}
	
	private ObjectName createName(String name) throws MalformedObjectNameException{
		String appName = RuntimeConstants.getApplicationName();
		String objectName = "moskito."+(appName.length()>0 ? appName+".":"")+"thresholds:type="+name;
		ObjectName objName = new ObjectName(objectName);
		return objName;
	}
		

	
	public Threshold createThreshold(ThresholdDefinition definition){
		Threshold ret = createTieable(definition); 
	    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	    try{
	    	// Construct the ObjectName for the MBean we will register
			ObjectName name = createName(ret.getDefinition().getName());
	    	
	    	// Register the Hello World MBean
	    	mbs.registerMBean(ret, name);
	    }catch(MalformedObjectNameException e){
	    	log.warn("can't subscribe threshold to jmx", e);
	    } catch (InstanceAlreadyExistsException e) {
	    	log.warn("can't subscribe threshold to jmx", e);
		} catch (MBeanRegistrationException e) {
	    	log.warn("can't subscribe threshold to jmx", e);
		} catch (NotCompliantMBeanException e) {
	    	log.warn("can't subscribe threshold to jmx", e);
		}

    	return ret;
	}

	/**
	 * Returns the worst threshold status in the system.
	 * @return the worst detected threshold status.
	 */
	public ThresholdStatus getWorstStatus(){
		ThresholdStatus ret = ThresholdStatus.GREEN;
		for (Threshold t : getThresholds()){
			if (t.getStatus().overrules(ret))
				ret = t.getStatus();
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
}
