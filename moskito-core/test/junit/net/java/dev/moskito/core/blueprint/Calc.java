package net.java.dev.moskito.core.blueprint;

import java.util.HashMap;
import java.util.Map;

public class Calc {
	private Map<String, Class<? extends Action>> actionClazzes;
	
	private static final Calc instance = new Calc();
	
	public static final Calc getInstance(){
		return instance;
	}
	
	private Calc(){
		actionClazzes = new HashMap<String, Class<? extends Action>>();
		actionClazzes.put("+", AddAction.class);
		actionClazzes.put("+", SubstractAction.class);
	}
	
	public int calc(String operation, int a, int b) throws Exception{
		Class<? extends Action> clazz = actionClazzes.get(operation);
		Action action = clazz.newInstance();
		return action.perform(a, b);
	}

	public static int calculate(String operation, int a, int b) throws Exception{
		return instance.calc(operation, a, b);
	}

}
