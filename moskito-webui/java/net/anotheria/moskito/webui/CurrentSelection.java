package net.anotheria.moskito.webui;

import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAOSortType;

/**
 * This bean, once initialized, holds current selection data in the thread, to prevent a lot of parameter passing between functions.
 * @author lrosenberg
 *
 */
public class CurrentSelection{
	/**
	 * Currently selected time unit for time manipulation.
	 */
	private TimeUnit currentTimeUnit;
	/**
	 * Name of currently selected interval.
	 */
	private String currentIntervalName;
	/**
	 * Currently selected sort type for analyze bean.
	 */
	private AnalyzedProducerCallsAOSortType analyzeProducerCallsSortType;
	
	public static CurrentSelection get(){
		return selection.get();
	}
	
	private void reset(){
		currentTimeUnit = null;
		currentIntervalName = null;
		analyzeProducerCallsSortType = new AnalyzedProducerCallsAOSortType();
	}
	
	public static CurrentSelection resetAndGet(){
		CurrentSelection current = selection.get();
		current.reset();
		return current;
	}
	
	/**
	 * The thread local state. 
	 */
	private final static ThreadLocal<CurrentSelection> selection = new ThreadLocal<CurrentSelection>(){
		@Override
		protected synchronized CurrentSelection initialValue() {
			return new CurrentSelection();
		}

	};

	public TimeUnit getCurrentTimeUnit() {
		return currentTimeUnit;
	}

	public void setCurrentTimeUnit(TimeUnit currentTimeUnit) {
		this.currentTimeUnit = currentTimeUnit;
	}

	public String getCurrentIntervalName() {
		return currentIntervalName;
	}

	public void setCurrentIntervalName(String currentIntervalName) {
		this.currentIntervalName = currentIntervalName;
	}

	public AnalyzedProducerCallsAOSortType getAnalyzeProducerCallsSortType() {
		return analyzeProducerCallsSortType;
	}

	public void setAnalyzeProducerCallsSortType(
			AnalyzedProducerCallsAOSortType analyzeProducerCallsSortType) {
		this.analyzeProducerCallsSortType = analyzeProducerCallsSortType;
	}
}
