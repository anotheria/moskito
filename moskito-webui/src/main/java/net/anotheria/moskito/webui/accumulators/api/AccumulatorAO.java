package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.moskito.core.accumulation.AccumulatedValue;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.util.NumberUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * API layer view of the accumulator.
 *
 * @author lrosenberg
 * @since 21.03.14 23:05
 */
public class AccumulatorAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5404648764114214875L;

	/**
	 * Name of the accumulator.
	 */
	private String name;

	/**
	 * Id of the accumulator.
	 */
	private String id;

	/**
	 * Accumulated values over the time.
	 */
	private List<AccumulatedValueAO> values;

	public AccumulatorAO(Accumulator acc){
		name = acc.getName();
		id = acc.getId();
		values = new LinkedList<AccumulatedValueAO>();
		for (AccumulatedValue v : acc.getValues()){
			long timestamp = v.getTimestamp()/1000*1000;
			//for single graph data
			AccumulatedValueAO ao = new AccumulatedValueAO(NumberUtils.makeTimeString(timestamp));
			ao.addValue(v.getValue());
			ao.setIsoTimestamp(NumberUtils.makeISO8601TimestampString(v.getTimestamp()));
			ao.setNumericTimestamp(v.getTimestamp());
			values.add(ao);
		}
	}

	public String getName(){
		return name;
	}

	public String toString(){
		return "Accumulator "+getName();
	}

	public List<AccumulatedValueAO> getValues() {
		return values;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
