package net.anotheria.moskito.core.usecase.recorder;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;

import java.util.List;

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
	void addRecordedUseCase(CurrentlyTracedCall useCase);
	
	/**
	 * Returns the list of all recorded use-cases.
	 * @return
	 */
	List<CurrentlyTracedCall> getRecordedUseCases();
	
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
	CurrentlyTracedCall getRecordedUseCaseByName(String name) throws NoSuchRecordedUseCaseException;
	
	/**
	 * Deletes a recorded user case.
	 * @param name name of the use case.
	 */
	void removeRecordedUseCaseByName(String name);
}
