package net.anotheria.moskito.extensions.sampling;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.extensions.sampling.mappers.ServiceRequestStatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.04.15 18:00
 */
public class SamplingEngine {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(SamplingEngine.class);

	/**
	 * Instance. For now.
	 */
	private static SamplingEngine instance = new SamplingEngine();

	private IProducerRegistry producerRegistry;

	public static final Boolean FLAG_REGISTER_PRODUCER_ON_THE_FLY = Boolean.TRUE;

	private SamplingEngine(){
		producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
	}



	public static final SamplingEngine getInstance(){
		return instance;
	}

	public void addSample(Sample sample){
		//do queue later

		processSample(sample);

	}

	private void processSample(Sample sample){
		String producerId = sample.getProducerId();
		String mapperId   = sample.getStatMapperId();
		StatsMapper mapper = getMapper(mapperId);

		if (mapper == null){
			log.error("Mapper with id "+mapperId+" not found, thrown away sample "+sample);
			return;
		}

		OnDemandStatsProducer producer = (OnDemandStatsProducer)producerRegistry.getProducer(producerId);
		if (producer == null){
			//we have to register producer.
			if (!FLAG_REGISTER_PRODUCER_ON_THE_FLY){
				log.warn("Submitted new sample for "+producerId+", which is not registered and producer auto-register is off");
				return ;
			}
			log.info("Registering producer "+producerId+" on the fly");
			String category = sample.getValues().get("category");
			if (category == null)
				category = "sampling"; // TODO make configurable
			String subsystem = sample.getValues().get("subsystem");
			if (subsystem == null)
				subsystem = "sampling"; // TODO make configurable
			producer = new OnDemandStatsProducer(producerId, category, subsystem, mapper.getFactory());
			producerRegistry.registerProducer(producer);
		}

		log.debug("Have to add sampling value to producer "+producer);
		String statName = sample.getValues().get("stat");
		if (statName == null){
			IStats stats = producer.getDefaultStats();
			mapper.updateStats(stats, sample);
		}else{
			try {
				IStats stats = producer.getStats(statName);
				mapper.updateStats(stats, sample);
			}catch(OnDemandStatsProducerException e){
				log.warn("Can't create new stats object", e);
			}
			String cumulate = sample.getValues().get("cumulate");
			if ("true".equals(cumulate)){
				mapper.updateStats(producer.getDefaultStats(), sample);
			}

		}

	}

	private StatsMapper getMapper(String mapperId){
		//TODO configuration.
		if (mapperId.equals("servicerequest"))
			return new ServiceRequestStatsMapper();
		return null;
	}
}
