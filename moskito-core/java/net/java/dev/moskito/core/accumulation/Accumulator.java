package net.java.dev.moskito.core.accumulation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.helper.AbstractTieable;
import net.java.dev.moskito.core.helper.Tieable;
import net.java.dev.moskito.core.producers.IStats;

/**
 * An accumulator accumulates value from a defined producer over some period of time or series of values.
 * @author another
 *
 */
public class Accumulator extends AbstractTieable<AccumulatorDefinition> implements Tieable{
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(Accumulator.class);
	/**
	 * Stored values.
	 */
	private List<AccumulatedValue> values;
	/**
	 * Attached stats.
	 */
	private IStats stats;

	public Accumulator(AccumulatorDefinition aDefinition){
		super(aDefinition);
		values = new ArrayList<AccumulatedValue>();
	}
	
	public void addValue(AccumulatedValue value){
		values.add(value);
		if (values.size()>getDefinition().getMaxAmountOfAccumulatedItems()){
			values = values.subList(getDefinition().getMaxAmountOfAccumulatedItems()-getDefinition().getAccumulationAmount()+1, values.size());
		}
	}
	
	public void addValue(String aValue){
		addValue(new AccumulatedValue(aValue));
	}
	
	public List<AccumulatedValue> getValues(){
		return values;
	}

	@Override
	public void update() {
		if (!isActivated()){
			return;
		}
		
		String currentValue = stats.getValueByNameAsString(getDefinition().getValueName(), getDefinition().getIntervalName(), getDefinition().getTimeUnit());
		if (log!=null && log.isDebugEnabled())
			log.debug("UPDATED "+this+" with "+currentValue);
		addValue(currentValue);
		
	}

	@Override
	public boolean isActivated(){
		return stats != null;
	}

	@Override
	public void tieToStats(IStats aStatsObject){
		stats = aStatsObject;
	}

	@Override public String toString(){
		return getId()+" "+getName()+" "+" Def: "+getDefinition()+" active: "+isActivated()+", Values: "+(values==null?"none":""+values.size());
	}
}
