package net.anotheria.moskito.core.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MoSKito Context is a thread local class, that is used to contain some information about current execution, for example tags.
 *
 * @author lrosenberg
 * @since 22.05.17 01:36
 */
public class MoSKitoContext {
	private static InheritableThreadLocal<MoSKitoContext> currentContext = new InheritableThreadLocal<MoSKitoContext>(){
		@Override
		protected MoSKitoContext initialValue() {
			return new MoSKitoContext();
		}
	};

	public static MoSKitoContext get(){
		return currentContext.get();
	}

	private HashMap<String, String> tags = new HashMap<>();

	/**
	 * If true an error has occured in this thread already. This is useful to separate from initial errors in the processing and followup errors.
	 */
	private AtomicBoolean errorOccured = new AtomicBoolean(false);

	public static void addTag(String tagName, String tagValue){
		get().tags.put(tagName, tagValue);
	}

	public static Map<String, String> getTags(){
		return (Map<String, String>) get().tags.clone();
	}

	public boolean markErrorAndReturnIfErrorAlreadyHappenedBefore(){
		return errorOccured.getAndSet(true);
	}

	public void reset(){
		tags = new HashMap<>();
		errorOccured = new AtomicBoolean(false);
	}

	
}
