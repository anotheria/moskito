package net.java.dev.moskito.core.predefined;

public interface TestService {
	void increase();
	
	void throwException();

	String operationWithParameter(int aParam, String bParam, boolean cParam);
}
