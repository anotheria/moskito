package net.anotheria.moskito.core.context;

import java.util.HashMap;
import java.util.Map;

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

	public static void addTag(String tagName, String tagValue){
		get().tags.put(tagName, tagValue);
	}

	public static Map<String, String> getTags(){
		return (Map<String, String>) get().tags.clone();
	}

	
}
