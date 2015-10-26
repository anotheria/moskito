package net.anotheria.moskito.core.accumulation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorConfig;
import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.helper.TieableDefinition;
import net.anotheria.moskito.core.helper.TieableRepository;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Repository that holds and manages accumulators.
 * @author lrosenberg
 *
 */
public final class AccumulatorRepository extends TieableRepository<Accumulator> {

	/**
	 * Logger
	 */
	private static Logger log = LoggerFactory.getLogger(AccumulatorRepository.class);

	/**
	 * The singleton instance.
	 */
	private static AccumulatorRepository INSTANCE = new AccumulatorRepository();
	/**
	 * Returns the singleton instance of the AccumulatorRepository.
	 * @return the one and only instance.
	 */
	public static AccumulatorRepository getInstance(){
		return INSTANCE;
	}

	private AccumulatorRepository(){
		readConfig();
	}

	@Override
	protected boolean tie(Accumulator acc, IStatsProducer<? extends IStats> producer) {
		AccumulatorDefinition definition = acc.getDefinition();
		IStats target = null;
		for (IStats s : producer.getStats()){
			if (s.getName().equals(definition.getStatName())){
				target = s;
				break;
			}
		}
		
		if (target==null){
			if (producer instanceof OnDemandStatsProducer){
				addToAutoTie(acc, producer);
			}else{
				throw new IllegalArgumentException("StatObject not found "+definition.getStatName()+" in "+definition);
			}
		}

		acc.tieToStats(target);
		return true;
		
	}
	
	@Override
	protected Accumulator create(TieableDefinition def){
		return new Accumulator((AccumulatorDefinition)def);
	}
	/**
	 * Returns configured accumulators.
	 * @return
	 */
	public List<Accumulator> getAccumulators(){
		return getTieables();
	}

	public Accumulator createAccumulator(TieableDefinition def){
		return createTieable(def);
	}

	/**
	 * Reads the config and creates configured accumulators. For now this method is only executed on startup.
	 */
	private void readConfig(){
		AccumulatorsConfig config = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig();
		AccumulatorConfig[] acs = config.getAccumulators();
		if (acs!=null && acs.length>0){
			for (AccumulatorConfig ac  : acs){
				AccumulatorDefinition ad = new AccumulatorDefinition();
				ad.setName(ac.getName());
				ad.setIntervalName(ac.getIntervalName());
				ad.setProducerName(ac.getProducerName());
				ad.setStatName(ac.getStatName());
				ad.setTimeUnit(TimeUnit.valueOf(ac.getTimeUnit()));
				ad.setValueName(ac.getValueName());
				Accumulator acc = createAccumulator(ad);
				if (log.isDebugEnabled()){
					log.debug("Created accumulator "+acc);
				}
			}
		}
	}

    /**
     * This method is for unit testing ONLY.
	 * The Findbugs warning is suppressed, because this method is for unit testing only.
     */
	@SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", justification = "This method is for unit testing only.")
    void reset() {
        cleanup();
		INSTANCE = new AccumulatorRepository();
	}

}
