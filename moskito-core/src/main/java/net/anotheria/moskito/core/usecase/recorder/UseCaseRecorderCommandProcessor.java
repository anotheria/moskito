package net.anotheria.moskito.core.usecase.recorder;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.command.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Command processor for use-case recording.
 * @author lrosenberg
 *
 */
public class UseCaseRecorderCommandProcessor implements CommandProcessor{

	private static final Logger LOGGER = LoggerFactory.getLogger(UseCaseRecorderCommandProcessor.class);

	/**
	 * Parameter name that triggers the recorder.
	 */
	public static final String PARAM_USE_CASE_NAME = "mskUseCaseName";
	/**
	 * Use-case counter.
	 */
	private static AtomicInteger unnamedCounter = new AtomicInteger(0);
	
	@Override
	public void startCommand(String command, Map<String, String[]> parameters) {
		if (RunningTraceContainer.isTraceRunning())
			throw new RuntimeException("UseCase already running");
		String useCaseName = parameters.get(PARAM_USE_CASE_NAME)[0];
		if (useCaseName==null || useCaseName.length()==0)
			useCaseName = "unnamed"+unnamedCounter.incrementAndGet();
		RunningTraceContainer.startTracedCall(useCaseName);
		
		LOGGER.info("Starting command: "+useCaseName);
	}

	@Override
	public void stopCommand(String command, Map<String, String[]> parameters) {
		LOGGER.info("Stoping command: "+command);
		TracedCall last = RunningTraceContainer.endTrace();
		LOGGER.info("LAST_ "+last);
		UseCaseRecorderFactory.getUseCaseRecorder().addRecordedUseCase((CurrentlyTracedCall)last);
	}
	
}
