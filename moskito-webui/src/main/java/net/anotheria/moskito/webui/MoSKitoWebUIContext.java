package net.anotheria.moskito.webui;

import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAOSortType;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a thread local variable for
 *
 * @author lrosenberg
 * @since 11.05.14 18:08
 */
public class MoSKitoWebUIContext {

	/**
	 * Http Session.
	 */
	private HttpSession currentSession;

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



	/**
	 * Simple container map to pass attributes between modules.
	 */
	private Map<String,Serializable> attributes = new HashMap<String, Serializable>();

	public HttpSession getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(HttpSession currentSession) {
		this.currentSession = currentSession;
	}

	private void copyFromAnotherContext(MoSKitoWebUIContext anotherContext){
		currentSession      = anotherContext.currentSession;
		currentTimeUnit     = anotherContext.currentTimeUnit;
		currentIntervalName = anotherContext.currentIntervalName;
		analyzeProducerCallsSortType = anotherContext.analyzeProducerCallsSortType;
		attributes = (HashMap<String,Serializable>)((HashMap<String,Serializable>)anotherContext.attributes).clone();
	}


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

	/**
	 * The thread local variable associated with the current thread.
	 */
	private static InheritableThreadLocal<MoSKitoWebUIContext> currentCallContext = new InheritableThreadLocal<MoSKitoWebUIContext>() {
		@Override
		protected synchronized MoSKitoWebUIContext initialValue() {
			return new MoSKitoWebUIContext();
		}

		@Override
		protected MoSKitoWebUIContext childValue(MoSKitoWebUIContext parentValue) {
			MoSKitoWebUIContext ret = new MoSKitoWebUIContext();
			ret.copyFromAnotherContext(parentValue);
			return ret;
		}
	};

	/**
	 * Returns the APICallContext assigned to this thread.
	 * @return previously assigned or new ApiCallContext object.
	 */
	public static MoSKitoWebUIContext getCallContext(){
		return currentCallContext.get();
	}

	private void reset(){
		currentTimeUnit = null;
		currentIntervalName = null;
		analyzeProducerCallsSortType = new AnalyzedProducerCallsAOSortType();
	}

	public static MoSKitoWebUIContext getCallContextAndReset(){
		MoSKitoWebUIContext current = getCallContext();
		current.reset();
		return current;
	}


	/**
	 * Removes the thread local instance of the api call context, in order to prevent webapp redeployment leaks.
	 */
	public static void remove(){
		currentCallContext.remove();
	}

	public void addAttribute(String attributeName, Serializable attributeValue){
		attributes.put(attributeName, attributeValue);
	}

	public Serializable getAttribute(String attributeName){
		return attributes.get(attributeName);
	}

}
