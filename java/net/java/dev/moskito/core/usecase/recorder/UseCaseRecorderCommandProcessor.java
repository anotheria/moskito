package net.java.dev.moskito.core.usecase.recorder;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.command.ICommandProcessor;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;

public class UseCaseRecorderCommandProcessor implements ICommandProcessor{
	
	
	
	public static final String PARAM_USE_CASE_NAME = "mskUseCaseName";
	private static AtomicInteger unnamedCounter = new AtomicInteger(0);

	public void startCommand(String command, Map<String, String[]> parameters) {
		if (RunningUseCaseContainer.isUseCaseRunning())
			throw new RuntimeException("UseCase already running");
		String useCaseName = parameters.get(PARAM_USE_CASE_NAME)[0];
		if (useCaseName==null || useCaseName.length()==0)
			useCaseName = "unnamed"+unnamedCounter.incrementAndGet();
		RunningUseCaseContainer.startUseCase(useCaseName);
		
		System.out.println("Starting command: "+useCaseName);
	}

	public void stopCommand(String command, Map<String, String[]> parameters) {
		System.out.println("SToping command: "+command);
		RunningUseCase last = RunningUseCaseContainer.endUseCase();
		System.out.println("LAST_ "+last);
		UseCaseRecorderFactory.getUseCaseRecorder().addRecordedUseCase((ExistingRunningUseCase)last);
	}
	
}
