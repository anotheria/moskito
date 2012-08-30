package net.java.dev.moskito.core.accumulation;

import java.util.List;

import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.helper.TieableDefinition;
import net.java.dev.moskito.core.helper.TieableRepository;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * Repository that holds and manages accumulators.
 * @author lrosenberg
 *
 */
public class AccumulatorRepository extends TieableRepository<Accumulator> {
	/**
	 * The singleton instance.
	 */
	private static final AccumulatorRepository INSTANCE = new AccumulatorRepository();
	/**
	 * Returns the singleton instance of the AccumulatorRepository.
	 * @return the one and only instance.
	 */
	public static final AccumulatorRepository getInstance(){
		return INSTANCE;
	}

	@Override
	protected boolean tie(Accumulator acc, IStatsProducer producer) {
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
}