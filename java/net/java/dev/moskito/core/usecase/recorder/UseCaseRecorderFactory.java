package net.java.dev.moskito.core.usecase.recorder;

/**
 * Factory that creates an instance of the use-case-recorder.
 * @author lrosenberg
 *
 */
public class UseCaseRecorderFactory {
	/**
	 * Singleton instance.
	 */
	private static final UseCaseRecorderImpl instance = new UseCaseRecorderImpl();
	/**
	 * Returns the instance of the use-case recorder.
	 * @return
	 */
	public static final IUseCaseRecorder getUseCaseRecorder(){
		return instance;
	}
}
