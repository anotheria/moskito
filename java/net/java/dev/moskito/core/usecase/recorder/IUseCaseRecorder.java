package net.java.dev.moskito.core.usecase.recorder;

import java.util.List;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

/**
 * Interface for a use case recorder.
 * @author lrosenberg
 *
 */
public interface IUseCaseRecorder {
	/**
	 * Adds a newly recorded use-case.
	 * @param useCase
	 */
	void addRecordedUseCase(ExistingRunningUseCase useCase);
	
	/**
	 * Returns the list of all recorded use-cases.
	 * @return
	 */
	List<ExistingRunningUseCase> getRecordedUseCases();
	
	/**
	 * Returns the list of all recorded use-case names.
	 * @return
	 */
	List<String> getRecordedUseCaseNames();
	
	/**
	 * Returns recorded use case by its name.
	 * @param name
	 * @return
	 * @throws NoSuchRecordedUseCaseException
	 */
	ExistingRunningUseCase getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException;
	
	/**
	 * Deletes a recorded user case.
	 * @param name name of the use case.
	 */
	void removeRecordedUseCaseByName(String name);
}
