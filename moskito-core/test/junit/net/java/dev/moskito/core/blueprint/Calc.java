package net.java.dev.moskito.core.blueprint;

import java.util.HashMap;
import java.util.Map;

import net.java.dev.moskito.core.producers.IStatsProducer;

public class Calc {
	private static Map<String, Class<? extends Action>> actionClazzes;
	static{
		actionClazzes = new HashMap<String, Class<? extends Action>>();
		actionClazzes.put("+", AddAction.class);
		actionClazzes.put("-", SubstractAction.class);
		actionClazzes.put("E", ErrorAction.class);
	}
	
	private static MyBlueprintCallExecutor callExecutor = new MyBlueprintCallExecutor();
	
	private static int calc(String operation, int a, int b) throws Exception{
		Class<? extends Action> clazz = actionClazzes.get(operation);
		Action action = clazz.newInstance();
		return action.perform(a, b);
	}

	public static int calculate(String operation, int a, int b) throws Exception{
		
		BlueprintProducer producer = BlueprintProducersFactory.getBlueprintProducer(operation, "d", "d");
		return (Integer)producer.execute(callExecutor, operation, a, b);
	}
	
	static class MyBlueprintCallExecutor implements BlueprintCallExecutor{
		@Override
		public Object execute(Object... parameters) throws Exception {
			return calc((String)parameters[0], ((Integer)parameters[1]).intValue(), ((Integer)parameters[2]).intValue());
		}
		
	}
	
	static IStatsProducer getProducer(String operation){
		return BlueprintProducersFactory.getBlueprintProducer(operation, "d", "d");
	}

}
