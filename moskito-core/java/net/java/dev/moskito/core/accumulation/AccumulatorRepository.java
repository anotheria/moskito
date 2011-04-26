package net.java.dev.moskito.core.accumulation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.helper.TieableDefinition;
import net.java.dev.moskito.core.helper.TieableRepository;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

public class AccumulatorRepository extends TieableRepository<Accumulator> {
	
	private static final AccumulatorRepository INSTANCE = new AccumulatorRepository();
	
	private ConcurrentMap<String, String> id2nameMapping = new ConcurrentHashMap<String, String>();
	
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
	
	
	protected Accumulator create(TieableDefinition def){
		return new Accumulator((AccumulatorDefinition)def);
	}
	
	public List<Accumulator> getAccumulators(){
		return getTieables();
	}

	public Accumulator createAccumulator(AccumulatorDefinition definition) {
		Accumulator ret = createTieable(definition);
		id2nameMapping.put(ret.getId(), ret.getName());
		return ret;
	}
	
	public Accumulator getAccumulatorById(String id){
		String name = id2nameMapping.get(id);
		if (name==null)
			throw new IllegalArgumentException("Id: "+id+" not bound");
		return getByName(name);
	}

}