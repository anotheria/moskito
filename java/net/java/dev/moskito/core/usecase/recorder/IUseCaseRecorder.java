package net.java.dev.moskito.core.usecase.recorder;

import java.util.List;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

public interface IUseCaseRecorder {
	
	public void addRecordedUseCase(ExistingRunningUseCase useCase);
	
	public List<ExistingRunningUseCase> getRecordedUseCases();
	
	public List<String> getRecordedUseCaseNames();
	
	public ExistingRunningUseCase getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException;
	
	public void removeRecordedUseCaseByName(String name);
}
