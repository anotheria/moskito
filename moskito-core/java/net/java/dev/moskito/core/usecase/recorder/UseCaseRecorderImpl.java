package net.java.dev.moskito.core.usecase.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
/**
 * Implementation of the use-case recorder.
 * @author lrosenberg
 */
public class UseCaseRecorderImpl implements IUseCaseRecorder{

	/**
	 * Map with recorded use-cases.
	 */
	private Map<String, ExistingRunningUseCase> recordedUseCases;
	/**
	 * Constructor.
	 */
	UseCaseRecorderImpl() {
		recordedUseCases = new ConcurrentHashMap<String, ExistingRunningUseCase>();
	}

	@Override public void addRecordedUseCase(ExistingRunningUseCase useCase) {
		recordedUseCases.put(useCase.getName(), useCase);
	}

	@Override public ExistingRunningUseCase getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException{
		ExistingRunningUseCase useCase = recordedUseCases.get(name);
		if (useCase==null)
			throw new NoSuchRecordedUseCaseException(name);
		return useCase;
	}

	@Override public List<String> getRecordedUseCaseNames() {
		return new ArrayList<String>(recordedUseCases.keySet());
	}

	@Override public List<ExistingRunningUseCase> getRecordedUseCases() {
		return new ArrayList<ExistingRunningUseCase>(recordedUseCases.values());
	}

	@Override public void removeRecordedUseCaseByName(String name){
		recordedUseCases.remove(name);
	}
}
