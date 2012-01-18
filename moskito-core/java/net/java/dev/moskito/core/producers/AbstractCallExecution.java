package net.java.dev.moskito.core.producers;

public abstract class AbstractCallExecution implements CallExecution{

	@Override
	public void startExecution() {
		startExecution(true, null);
	}

	@Override
	public void startExecution(boolean recordUseCase) {
		startExecution(recordUseCase, null);
		
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