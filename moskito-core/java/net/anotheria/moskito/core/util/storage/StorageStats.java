package net.anotheria.moskito.core.util.storage;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

/**
 * This class gathers statistics for storages. Storages are map like containers.
 */
public class StorageStats extends AbstractStats {
	
	/**
	 * Number of calls of the get method.
	 */
	private StatValue gets;
	
	/**
	 * Nnumber of the calls of the get method which returned null.
	 */
	private StatValue missedGets;
	
	/**
	 * Number of total calls to put.
	 */
	private StatValue puts;
	
	/**
	 * Number of calls to put which overwrote existing data.
	 */
	private StatValue overwritePuts;
	
	/**
	 * Number of remove calls.
	 */
	private StatValue removes;
	
	/**
	 * Number of remove calls which did no effect (no value for this key in the storage).
	 */
	private StatValue noopRemoves;
	
	/**
	 * Size of the storage. Tricky calculated on each operation, may differ from the real size.
	 */
	private StatValue size;
	
	/**
	 * Number of calls of the containsKey method.
	 */
	private StatValue containsKeyCalls;
	
	/**
	 * Number of calls of the containsKey method which returned true.
	 */
	private StatValue containsKeyHits;
	
	/**
	 * Number of calls of the containsValue method.
	 */
	private StatValue containsValueCalls;
	
	/**
	 * Number of calls of the containsValue method which returned true.
	 */
	private StatValue containsValueHits;

	/**
	 * Name of this storage.
	 */
	private String name;

	/**
	 * Creates a new storage stats object without a name and with support for default intervals.
	 */
	public StorageStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new storage stats object with the given name and with support for default intervals.
	 * @param name
	 */
	public StorageStats(String name){
		this(name, Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new storage stats object with the given name and intervals.
	 * @param aName name of the storage.
	 * @param selectedIntervals supported intervals.
	 */
	public StorageStats(String aName,  Interval[] selectedIntervals){
		Long longPattern = Long.valueOf(0);
		name = aName;
		
		gets = StatValueFactory.createStatValue(longPattern, "gets", selectedIntervals);
		missedGets = StatValueFactory.createStatValue(longPattern, "missedGets", selectedIntervals);
		puts = StatValueFactory.createStatValue(longPattern, "puts", selectedIntervals);
		overwritePuts = StatValueFactory.createStatValue(longPattern, "overwritePuts", selectedIntervals);
		removes = StatValueFactory.createStatValue(longPattern, "removes", selectedIntervals);
		noopRemoves = StatValueFactory.createStatValue(longPattern, "noopRemoves", selectedIntervals);
		
		containsKeyCalls = StatValueFactory.createStatValue(longPattern, "containsKeyCalls", selectedIntervals);
		containsKeyHits = StatValueFactory.createStatValue(longPattern, "containsKeyHits", selectedIntervals);
		containsValueCalls = StatValueFactory.createStatValue(longPattern, "containsValueCalls", selectedIntervals);
		containsValueHits = StatValueFactory.createStatValue(longPattern, "containsValueHits", selectedIntervals);
		
		size = StatValueFactory.createStatValue(longPattern, "size", selectedIntervals);
		

	}
	
	public String getName(){
		return name;
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" G: ").append(gets.getValueAsLong(intervalName));
		b.append(" mG: ").append(missedGets.getValueAsLong(intervalName));
		b.append(" mG R: ").append(getMissedGetRatio(intervalName));
		b.append(" hG R: ").append(getHitGetRatio(intervalName));
		
		b.append(" P: ").append(puts.getValueAsLong(intervalName));
		b.append(" oP: ").append(overwritePuts.getValueAsLong(intervalName));
		b.append(" oP R: ").append(getOverwritePutRatio(intervalName));
		b.append(" nP R: ").append(getNewPutRatio(intervalName));
		
		
		b.append(" RM: ").append(removes.getValueAsLong(intervalName));
		b.append(" noRM: ").append(noopRemoves.getValueAsLong(intervalName));
		b.append(" noRM R: ").append(getNoopRemoveRatio(intervalName));
		

		b.append(" PG R: ").append(getPutGetRatio(intervalName));
		b.append(" PRM R: ").append(getPutRemoveRatio(intervalName));

		b.append(" SZ: ").append(size.getValueAsLong(intervalName));
		
		b.append(" CKC: ").append(containsKeyCalls.getValueAsLong(intervalName));
		b.append(" CKH: ").append(containsKeyHits.getValueAsLong(intervalName));
		b.append(" CK HR: ").append(containsKeyHits.getValueAsLong(intervalName));
		b.append(" CVC: ").append(containsValueCalls.getValueAsLong(intervalName));
		b.append(" CVH: ").append(getContainsKeyHitRatio(intervalName));
		b.append(" CV HR: ").append(getContainsValueHitRatio(intervalName));

		return b.toString();
	}

	/**
	 * Returns the ratio of remove operation that had no effect.
	 * @param intervalName the name of the interval.
	 * @return
	 */
	public double getNoopRemoveRatio(String intervalName){
		return noopRemoves.getValueAsDouble(intervalName) / removes.getValueAsDouble(intervalName);
	}

	/**
	 * Returns the ratio of overwriting puts compared to all puts.
	 * @param intervalName the name of the interval.
	 * @return
	 */
	public double getOverwritePutRatio(String intervalName){
		return overwritePuts.getValueAsDouble(intervalName) / puts.getValueAsDouble(intervalName);
	}

	public double getNewPutRatio(String intervalName){
		long putsAsLong = puts.getValueAsLong(intervalName);
		return ((double)(putsAsLong - overwritePuts.getValueAsLong(intervalName))) / putsAsLong;
	}

	public double getMissedGetRatio(String intervalName){
		return missedGets.getValueAsDouble(intervalName) / gets.getValueAsDouble(intervalName);
	}
	
	public double getHitGetRatio(String intervalName){
		long getAsLong = gets.getValueAsLong(intervalName);
		return ((double)(getAsLong - missedGets.getValueAsLong(intervalName)))/getAsLong;
	}

	public double getContainsKeyHitRatio(String intervalName){
		return containsKeyHits.getValueAsDouble(intervalName) / containsKeyCalls.getValueAsLong(intervalName);
	}

	public double getContainsValueHitRatio(String intervalName){
		return containsValueHits.getValueAsDouble(intervalName) / containsValueCalls.getValueAsLong(intervalName);
	}
	
	public double getPutGetRatio(String intervalName){
		return puts.getValueAsDouble(intervalName) / gets.getValueAsLong(intervalName);
	}

	public double getPutRemoveRatio(String intervalName){
		return puts.getValueAsDouble(intervalName) / removes.getValueAsLong(intervalName);
	}

	public void addGet(){
		gets.increase();
	}
	
	public void addMissedGet(){
		missedGets.increase();
	}
	
	public void addPut(){
		puts.increase();
	}
	
	public void addOverwritePut(){
		overwritePuts.increase();
	}
	
	public void increaseSize(){
		size.increase();
	}
	
	public void decreaseSize(){
		size.decrease();
	}

	public void setSize(int aSize){
		size.setValueAsInt(aSize);
	}
	
	public void addContainsKeyHit(){
		containsKeyCalls.increase();
		containsKeyHits.increase();
		
	}

	public void addContainsKeyMiss(){
		containsKeyCalls.increase();
	}

	public void addContainsValueHit(){
		containsValueCalls.increase();
		containsValueHits.increase();
		
	}

	public void addContainsValueMiss(){
		containsValueCalls.increase();
	}
	
	public void addRemove(){
		removes.increase();
	}
	
	public void addNoopRemove(){
		noopRemoves.increase();
	}

	public long getContainsKeyCalls(String intervalName) {
		return containsKeyCalls.getValueAsLong(intervalName);
	}

	public long getContainsKeyHits(String intervalName) {
		return containsKeyHits.getValueAsLong(intervalName);
	}

	public long getContainsValueCalls(String intervalName) {
		return containsValueCalls.getValueAsLong(intervalName);
	}

	public long getContainsValueHits(String intervalName) {
		return containsValueHits.getValueAsLong(intervalName);
	}

	public long getGets(String intervalName) {
		return gets.getValueAsLong(intervalName);
	}

	public long getMissedGets(String intervalName) {
		return missedGets.getValueAsLong(intervalName);
	}

	public long getNoopRemoves(String intervalName) {
		return noopRemoves.getValueAsLong(intervalName);
	}

	public long getOverwritePuts(String intervalName) {
		return overwritePuts.getValueAsLong(intervalName);
	}

	public long getPuts(String intervalName) {
		return puts.getValueAsLong(intervalName);
	}

	public long getRemoves(String intervalName) {
		return removes.getValueAsLong(intervalName);
	}

	public long getSize(String intervalName) {
		return size.getValueAsLong(intervalName);
	}
}
