package net.java.dev.moskito.core.producers;

/**
 * Adapter for CallExecution interface with some default behaviour.
 * @author lrosenberg
 */
public abstract class AbstractCallExecution implements CallExecution{

	@Override
	public void startExecution() {
		startExecution(true, null);
	}

	@Override
	public void startExecution(boolean traceCall) {
		startExecution(traceCall, null);
		
	}

	@Override
	public void startExecution(String callDescription) {
		startExecution(true, callDescription);
	}


	@Override
	public void finishExecution() {
		finishExecution(null);
	}

	@Override
	public void abortExecution() {
		notifyExecutionError();
		finishExecution();
	}

	@Override
	public void abortExecution(String result) {
		notifyExecutionError();
		finishExecution(result);
	}
	
}