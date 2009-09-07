package net.java.dev.moskito.core.usecase.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

public class UseCaseRecorderImpl implements IUseCaseRecorder{
	
	private Map<String, ExistingRunningUseCase> recordedUseCases;
	
	UseCaseRecorderImpl() {
		recordedUseCases = new ConcurrentHashMap<String, ExistingRunningUseCase>();
	}

	public void addRecordedUseCase(ExistingRunningUseCase useCase) {
		recordedUseCases.put(useCase.getName(), useCase);
	}

	public ExistingRunningUseCase getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException{
		ExistingRunningUseCase useCase = recordedUseCases.get(name);
		if (useCase==null)
			throw new NoSuchRecordedUseCaseException(name);
		return useCase;
	}

	public List<String> getRecordedUseCaseNames() {
		return new ArrayList<String>(recordedUseCases.keySet());
	}

	public List<ExistingRunningUseCase> getRecordedUseCases() {
		return new ArrayList<ExistingRunningUseCase>(recordedUseCases.values());
	}

	public void removeRecordedUseCaseByName(String name){
		recordedUseCases.remove(name);
	}
}
