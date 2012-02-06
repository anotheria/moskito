package net.java.dev.moskito.core.usecase.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
/**
 * Implementation of the use-case recorder.
 * @author lrosenberg
 */
public class UseCaseRecorderImpl implements IUseCaseRecorder{

	/**
	 * Map with recorded use-cases.
	 */
	private Map<String, CurrentlyTracedCall> recordedUseCases;
	/**
	 * Constructor.
	 */
	UseCaseRecorderImpl() {
		recordedUseCases = new ConcurrentHashMap<String, CurrentlyTracedCall>();
	}

	@Override public void addRecordedUseCase(CurrentlyTracedCall useCase) {
		recordedUseCases.put(useCase.getName(), useCase);
	}

	@Override public CurrentlyTracedCall getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException{
		CurrentlyTracedCall useCase = recordedUseCases.get(name);
		if (useCase==null)
			throw new NoSuchRecordedUseCaseException(name);
		return useCase;
	}

	@Override public List<String> getRecordedUseCaseNames() {
		return new ArrayList<String>(recordedUseCases.keySet());
	}

	@Override public List<CurrentlyTracedCall> getRecordedUseCases() {
		return new ArrayList<CurrentlyTracedCall>(recordedUseCases.values());
	}

	@Override public void removeRecordedUseCaseByName(String name){
		recordedUseCases.remove(name);
	}
}
