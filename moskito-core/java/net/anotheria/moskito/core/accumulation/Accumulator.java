package net.anotheria.moskito.core.accumulation;

import net.anotheria.moskito.core.helper.AbstractTieable;
import net.anotheria.moskito.core.helper.Tieable;
import net.anotheria.moskito.core.producers.IStats;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * An accumulator accumulates value from a defined producer over some period of time or series of values.
 * @author another
 *
 */
public class Accumulator extends AbstractTieable<AccumulatorDefinition> implements Tieable {
	
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

	/**
	 * Creates a new Accumulator.
	 * @param aDefinition - accumulator definition.
	 */
	public Accumulator(AccumulatorDefinition aDefinition){
		super(aDefinition);
		values = new ArrayList<AccumulatedValue>(aDefinition.getMaxAmountOfAccumulatedItems());
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
