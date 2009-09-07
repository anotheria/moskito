package net.java.dev.moskito.core.usecase.recorder;

public class UseCaseRecorderFactory {
	private static final UseCaseRecorderImpl instance = new UseCaseRecorderImpl();
	
	public static final IUseCaseRecorder getUseCaseRecorder(){
		return instance;
	}
}
