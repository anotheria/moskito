package net.java.dev.moskito.webui;

import java.util.HashMap;
import java.util.Map;

import net.java.dev.moskito.webui.action.MoskitoUIAction;

public final class ActionFactory {
	
	private static final Map<String, MoskitoUIAction> instances = new HashMap<String, MoskitoUIAction>();
	
	static MoskitoUIAction getInstanceOf(String actionType) throws ActionFactoryException{
		MoskitoUIAction action = instances.get(actionType);
		if (action!=null)
			return action;
		synchronized(instances){
			action = instances.get(actionType);
			if (action!=null)
				return action;
			try{
				action = (MoskitoUIAction) Class.forName(actionType).newInstance();
			}catch(Exception e){
				throw new ActionFactoryException(e);
			}
			instances.put(actionType, action);
		}
		return action;
	}
}
